package com.example.yatzyblock

import android.widget.TextView
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {


    fun sumUpAll(list: MutableList<TextView>) : Int {
        var sum = 0
        list.forEach {
            if(it.text.toString().isNotEmpty()) {
                sum += it.text.toString().toInt()
            }
        }
        return sum
    }

}