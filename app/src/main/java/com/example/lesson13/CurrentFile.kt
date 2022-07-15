package com.example.lesson13

class CurrentFile(private var name: String?, private var data: String?) {
    fun getName(): String? {
        return name
    }

    fun getData(): String? {
        return data
    }
}