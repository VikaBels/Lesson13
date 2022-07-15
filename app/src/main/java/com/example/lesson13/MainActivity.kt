package com.example.lesson13

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson13.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity(), OnFragmentSendDataListener, OnFragmentNavigationListener {
    private var listMenuItem = ArrayList<ItemMenu>()

    private lateinit var adapter: MenuItemAdapter
    private lateinit var bindingMain: ActivityMainBinding

    private var fragmentSendDataListener: OnFragmentNavigationListener? = null

    private lateinit var drawerToggle: ActionBarDrawerToggle

    private fun showFragment(
        fragment: Fragment,
        tag: String,
        clearToTag: String?,
        clearInclusive: Boolean
    ) {
        val fragmentManager: FragmentManager = supportFragmentManager

        if (clearToTag != null || clearInclusive) {
            fragmentManager.popBackStack(
                clearToTag,
                if (clearInclusive) {
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                } else 0
            )
        }

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, tag)
            .addToBackStack(tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        if (savedInstanceState == null) {
            showFragment(MainScreenFragment(), TAG_FOR_MAIN_SCREEN, null, true)
        }

        addToolBar()

        initializeFragmentListener()

        fillingMenu()

        setUpAdapter()
    }

    private fun addToolBar() {
        val drawerLayout = bindingMain.drawerLayout
        val toolBar = bindingMain.toolBar

        setSupportActionBar(toolBar)

        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolBar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun initializeFragmentListener() {
        fragmentSendDataListener = this as? OnFragmentNavigationListener
            ?: error("$this${resources.getString(R.string.exceptionInterface)}")
    }

    private fun fillingMenu() {
        listMenuItem.add(
            ItemMenu(
                resources.getString(R.string.home_page),
                MainScreenFragment(),
                TAG_FOR_MAIN_SCREEN
            )
        )
        listMenuItem.add(
            ItemMenu(
                resources.getString(R.string.text_edit),
                ListFragment(),
                TAG_FOR_LIST_FILES
            )
        )
        listMenuItem.add(
            ItemMenu(
                resources.getString(R.string.calculator),
                CalculatorScreenFragment(),
                TAG_FOR_CALCULATOR
            )
        )
    }

    private fun setUpAdapter() {
        adapter = MenuItemAdapter(this, listMenuItem, fragmentSendDataListener)

        bindingMain.menuList.adapter = adapter
        bindingMain.menuList.layoutManager = LinearLayoutManager(this)
    }

    override fun onNavigate(fragment: Fragment, tag: String) {
        showFragment(fragment, tag, TAG_FOR_MAIN_SCREEN, false)
    }

    override fun onSendData(data: String?) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = DetailFragment()
        fragment.arguments = bundleOf(KEY_TRANSFER_NAME to data)
        transaction
            .apply { replace(R.id.container, fragment, TAG_FOR_DETAIL) }
            .addToBackStack(null)
            .commit()
    }

    override fun onFinishDetailFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = ListFragment()
        transaction
            .apply { add(R.id.container, fragment, TAG_FOR_LIST) }
            .commit()
        //supportFragmentManager.popBackStack()
    }

    override fun renameFragmentTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }
}
