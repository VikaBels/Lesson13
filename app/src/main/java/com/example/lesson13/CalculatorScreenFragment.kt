package com.example.lesson13

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.lesson13.databinding.FragmentCalculatorScreenBinding

class CalculatorScreenFragment : Fragment() {
    companion object {
        private const val OPERAND_EQUAL: String = "="
        private const val OPERAND_PLUS: String = "+"
        private const val OPERAND_MINUS: String = "-"
        private const val OPERAND_MULTIPLY: String = "*"
        private const val OPERAND_DIVIDE: String = "/"
    }

    private var btnZero: Button? = null
    private var btnOne: Button? = null
    private var btnTwo: Button? = null
    private var btnThree: Button? = null
    private var btnFour: Button? = null
    private var btnFive: Button? = null
    private var btnSix: Button? = null
    private var btnSeven: Button? = null
    private var btnEight: Button? = null
    private var btnNine: Button? = null
    private var btnPlus: Button? = null
    private var btnMinus: Button? = null
    private var btnDivide: Button? = null
    private var btnMultiply: Button? = null
    private var btnEqual: Button? = null
    private var btnClear: Button? = null

    private var txtViewResult: TextView? = null

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

        findViews()

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

        btnZero = null
        btnOne = null
        btnTwo = null
        btnThree = null
        btnFour = null
        btnFive = null
        btnSix = null
        btnSeven = null
        btnEight = null
        btnNine = null
        btnPlus = null
        btnMinus = null
        btnDivide = null
        btnMultiply = null
        btnEqual = null
        btnClear = null
        txtViewResult = null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentRenameTitleListener = null
    }

    private fun findViews() {
        btnZero = bindingCalculator?.btnZero
        btnOne = bindingCalculator?.btnOne
        btnTwo = bindingCalculator?.btnTwo
        btnThree = bindingCalculator?.btnThree
        btnFour = bindingCalculator?.btnFour
        btnFive = bindingCalculator?.btnFive
        btnSix = bindingCalculator?.btnSix
        btnSeven = bindingCalculator?.btnSeven
        btnEight = bindingCalculator?.btnEight
        btnNine = bindingCalculator?.btnNine
        btnPlus = bindingCalculator?.btnPlus
        btnMinus = bindingCalculator?.btnMinus
        btnDivide = bindingCalculator?.btnDivide
        btnMultiply = bindingCalculator?.btnMultiply
        btnEqual = bindingCalculator?.btnEqual
        btnClear = bindingCalculator?.btnClear
        txtViewResult = bindingCalculator?.txtResult
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
                    txtViewResult?.text = resources.getString(R.string.zero)
                    number.setLength(0)
                    clearVariables()
                }

                R.id.btnEqual -> workWithOperand(view.findViewById(v.id))
            }
        }
    }

    private fun clickOnNumberBtn(selectedButton: Button) {
        number.append(selectedButton.text.toString().trim { it <= ' ' })
        txtViewResult?.text = number
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
        if (selectedButton.text.equals(OPERAND_EQUAL) && operand.equals(OPERAND_EQUAL)) {
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
                OPERAND_PLUS -> num1 += num2
                OPERAND_MINUS -> num1 -= num2
                OPERAND_DIVIDE ->
                    if (num2 == 0) {
                        error = true
                    } else {
                        num1 /= num2
                    }
                OPERAND_MULTIPLY -> num1 *= num2
            }
            operand = selectedButton.text.toString()
            num2 = 0
        }

        if (selectedButton.text.toString() == OPERAND_EQUAL) {
            txtViewResult?.text =
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
        btnZero?.setOnClickListener(allButton)
        btnOne?.setOnClickListener(allButton)
        btnTwo?.setOnClickListener(allButton)
        btnThree?.setOnClickListener(allButton)
        btnFour?.setOnClickListener(allButton)
        btnFive?.setOnClickListener(allButton)
        btnSix?.setOnClickListener(allButton)
        btnSeven?.setOnClickListener(allButton)
        btnEight?.setOnClickListener(allButton)
        btnNine?.setOnClickListener(allButton)
        btnPlus?.setOnClickListener(allButton)
        btnMinus?.setOnClickListener(allButton)
        btnDivide?.setOnClickListener(allButton)
        btnMultiply?.setOnClickListener(allButton)
        btnEqual?.setOnClickListener(allButton)
        btnClear?.setOnClickListener(allButton)
    }
}