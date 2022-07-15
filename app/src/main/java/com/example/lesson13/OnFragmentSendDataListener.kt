package com.example.lesson13

interface OnFragmentSendDataListener {
    fun onSendData(data: String?)

    fun onFinishDetailFragment()

    fun renameFragmentTitle(title: String)
}