package com.example.lesson13


interface OnFragmentNavigationListener {
    fun openFragmentByTag(tag: String)

    fun finishDetailFragment()

    fun finishAllFragmentsAndExit()
}