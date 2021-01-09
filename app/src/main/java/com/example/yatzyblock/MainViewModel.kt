package com.example.yatzyblock

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    fun sumUp(list: MutableList<TextView>, bonus: Int = 0): Int {
        Log.i("INSUM", bonus.toString())
        var sum = 0
        list.forEach {
            if (it.text.toString().isNotEmpty()) {
                sum += it.text.toString().toInt()
            }
        }
        return sum + bonus
    }

    fun checkIfBonus(sum: Int): Boolean {
        if (sum >= 63) {
            return true
        }
        return false
    }

}