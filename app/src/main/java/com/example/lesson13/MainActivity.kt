package com.example.lesson13

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson13.adapters.MenuItemAdapter
import com.example.lesson13.databinding.ActivityMainBinding
import com.example.lesson13.fragments.*
import com.example.lesson13.listeners.*
import com.example.lesson13.models.ItemMenu

class MainActivity : AppCompatActivity(),
    OnFragmentOpenFileListener,
    OnFragmentNavigationListener,
    OnFragmentRenameTitleListener,
    CustomDialogConfigurationListener,
    OnFragmentFinishListener {

    companion object {
        const val KEY_TAG_DIALOG = "keyDialog"
    }

    private var bindingMain: ActivityMainBinding? = null

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain?.root)

        if (savedInstanceState == null) {
            showFragment(TAG_FOR_MAIN_SCREEN, null, null)
        }

        addToolBar()

        fillingMenu()

        setUpAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingMain = null
    }

    override fun openFileByName(data: String?) {
        showFragment(TAG_FOR_DETAIL, TAG_FOR_MAIN_SCREEN, data)
    }

    override fun openFragmentByTag(tag: String) {
        showFragment(tag, TAG_FOR_MAIN_SCREEN, null)
    }

    override fun finishDetailFragment() {
        showFragment(null, TAG_FOR_MAIN_SCREEN, null)
    }

    override fun finishProgram() {
        finish()
    }

    override fun showDialog(title: String, message: String, cancel: Boolean) {
        val fragment = CustomDialogFragment()

        fragment.arguments =
            bundleOf(
                KEY_INFO_DIALOG to arrayListOf(
                    title,
                    message,
                    cancel.toString()
                )
            )

        fragment.isCancelable = false
        fragment.show(supportFragmentManager, KEY_TAG_DIALOG)
    }

    private fun addToolBar() {
        val drawerLayout = bindingMain?.drawerLayout
        val toolBar = bindingMain?.toolBar

        setSupportActionBar(toolBar)

        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolBar,
            R.string.open,
            R.string.close
        )

        drawerLayout?.addDrawerListener(drawerToggle)
    }

    private fun showFragment(
        tag: String?,
        clearToTag: String?,
        nameFile: String?
    ) {
        val fragmentManager: FragmentManager = supportFragmentManager
        var fragment: Fragment? = null
        val transaction = fragmentManager.beginTransaction()

        if (clearToTag != null) {
            fragmentManager.popBackStack(
                clearToTag,
                0
            )
        }

        when (tag) {
            TAG_FOR_CALCULATOR -> {
                fragment = CalculatorScreenFragment()
            }
            TAG_FOR_LIST_FILES -> {
                fragment = ListFilesFragment()
            }
            TAG_FOR_MAIN_SCREEN -> {
                fragment = MainScreenFragment()
            }
            TAG_FOR_DETAIL -> {
                fragment = DetailFragment()
            }
        }

        if (!nameFile.isNullOrEmpty()) {
            fragment?.arguments = bundleOf(KEY_TRANSFER_NAME to nameFile)
        }

        if (fragment != null) {
            transaction.replace(R.id.container, fragment, tag)
                .addToBackStack(tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }

        bindingMain?.drawerLayout?.closeDrawer(GravityCompat.START)
    }

    override fun renameFragmentTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun fillingMenu(): List<ItemMenu> {
        val listMenuItem = mutableListOf<ItemMenu>()
        listMenuItem.add(
            ItemMenu(
                resources.getString(R.string.home_page),
                TAG_FOR_MAIN_SCREEN
            )
        )
        listMenuItem.add(
            ItemMenu(
                resources.getString(R.string.text_edit),
                TAG_FOR_LIST_FILES
            )
        )
        listMenuItem.add(
            ItemMenu(
                resources.getString(R.string.calculator),
                TAG_FOR_CALCULATOR
            )
        )
        return listMenuItem
    }

    private fun setUpAdapter() {
        val adapter = MenuItemAdapter(this, fillingMenu(), this)

        bindingMain?.menuList?.adapter = adapter
        bindingMain?.menuList?.layoutManager = LinearLayoutManager(this)
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
