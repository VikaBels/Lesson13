package com.example.lesson13

import androidx.fragment.app.Fragment

class ItemMenu(
    private var name: String?,
    private var fragment: Fragment,
    private var tag: String
) {
    fun getName(): String? {
        return name
    }

    fun getFragment(): Fragment {
        return fragment
    }

    fun getTag(): String {
        return tag
    }
}