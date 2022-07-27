package com.example.lesson13.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.lesson13.R
import com.example.lesson13.TXT_EMPTY
import com.example.lesson13.databinding.FragmentCalculatorScreenBinding
import com.example.lesson13.listeners.OnFragmentRenameTitleListener

class CalculatorScreenFragment : Fragment() {
    enum class Operation(val text: CharSequence) {
        EQUAL("="),
        PLUS("+"),
        MINUS("-"),
        MULTIPLY("*"),
        DIVIDE("/")
    }

    private val number = StringBuilder()

    private var num1 = 0
    private var num2 = 0
    private var operand: String? = null
    private var error = false

    private var helpOperand: String? = null
    private var helpInt = 0
    private var helperSum = 0

    private var bindingCalculator: FragmentCalculatorScreenBinding? = null

    private var allButton: View.OnClickListener? = null

    private var fragmentRenameTitleListener: OnFragmentRenameTitleListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentRenameTitleListener = context as? OnFragmentRenameTitleListener
            ?: error("$context${resources.getString(R.string.exceptionInterface)}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingCalculator = FragmentCalculatorScreenBinding.inflate(layoutInflater)

        return bindingCalculator?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickProcessing(view)

        setOnClickListener(allButton)
    }

    override fun onStart() {
        super.onStart()
        fragmentRenameTitleListener?.renameFragmentTitle(resources.getString(R.string.calculator))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingCalculator = null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentRenameTitleListener = null
    }

    private fun onClickProcessing(view: View) {
        allButton = View.OnClickListener { v ->
            when (v.id) {
                R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree,
                R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven,
                R.id.btnEight, R.id.btnNine -> clickOnNumberBtn(view.findViewById(v.id))
                R.id.btnPlus, R.id.btnMinus,
                R.id.btnDivide, R.id.btnMultiply -> workWithOperand(view.findViewById(v.id))
                R.id.btnClear -> {
                    bindingCalculator?.txtResult?.text = resources.getString(R.string.zero)
                    number.setLength(0)
                    clearVariables()
                }

                R.id.btnEqual -> workWithOperand(view.findViewById(v.id))
            }
        }
    }

    private fun clickOnNumberBtn(selectedButton: Button) {
        number.append(selectedButton.text.toString().trim { it <= ' ' })
        bindingCalculator?.txtResult?.text = number
    }

    private fun parseStringBuilderToInt(numberString: StringBuilder): Int? {
        var number: Int? = null
        try {
            number = numberString.toString().toInt()
        } catch (e: Exception) {
            println(e)
        }
        return number
    }

    private fun workWithOperand(selectedButton: Button) {
        if (selectedButton.text.equals(Operation.EQUAL.text) && operand == Operation.EQUAL.text) {
            num1 = helperSum
            operand = helpOperand
        }

        val selectedNumber: Int? = parseStringBuilderToInt(number)

        if (num1 == 0 && selectedNumber != null) {
            num1 = selectedNumber
            operand = selectedButton.text.toString()
        } else if (num2 == 0) {
            if (selectedNumber != null) {
                num2 = selectedNumber
                helpInt = num2
                helpOperand = operand
            } else {
                if (helpInt == 0) {
                    helpInt = num1
                    helpOperand = operand
                }
                num2 = helpInt
            }
            when (operand) {
                Operation.PLUS.text -> num1 += num2
                Operation.MINUS.text -> num1 -= num2
                Operation.DIVIDE.text ->
                    if (num2 == 0) {
                        error = true
                    } else {
                        num1 /= num2
                    }
                Operation.MULTIPLY.text -> num1 *= num2
            }
            operand = selectedButton.text.toString()
            num2 = 0
        }

        if (selectedButton.text.toString() == Operation.EQUAL.text) {
            bindingCalculator?.txtResult?.text =
                if (error) resources.getString(R.string.error) else num1.toString()
            number.setLength(0)
            helperSum = num1
        } else {
            number.setLength(0)
        }
    }

    private fun clearVariables() {
        operand = TXT_EMPTY
        num1 = 0
        helpInt = 0
        helperSum = 0
        helpOperand = TXT_EMPTY
    }

    private fun setOnClickListener(allButton: View.OnClickListener?) {
        bindingCalculator?.btnZero?.setOnClickListener(allButton)
        bindingCalculator?.btnOne?.setOnClickListener(allButton)
        bindingCalculator?.btnTwo?.setOnClickListener(allButton)
        bindingCalculator?.btnThree?.setOnClickListener(allButton)
        bindingCalculator?.btnFour?.setOnClickListener(allButton)
        bindingCalculator?.btnFive?.setOnClickListener(allButton)
        bindingCalculator?.btnSix?.setOnClickListener(allButton)
        bindingCalculator?.btnSeven?.setOnClickListener(allButton)
        bindingCalculator?.btnEight?.setOnClickListener(allButton)
        bindingCalculator?.btnNine?.setOnClickListener(allButton)
        bindingCalculator?.btnPlus?.setOnClickListener(allButton)
        bindingCalculator?.btnMinus?.setOnClickListener(allButton)
        bindingCalculator?.btnDivide?.setOnClickListener(allButton)
        bindingCalculator?.btnMultiply?.setOnClickListener(allButton)
        bindingCalculator?.btnEqual?.setOnClickListener(allButton)
        bindingCalculator?.btnClear?.setOnClickListener(allButton)
    }
}