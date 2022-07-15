package com.example.lesson13

import androidx.fragment.app.Fragment

interface OnFragmentNavigationListener {
    fun onNavigate(fragment: Fragment, tag: String)
}