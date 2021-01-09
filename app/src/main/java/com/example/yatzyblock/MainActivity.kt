package com.example.yatzyblock

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.yatzyblock.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var endSumTableRow: TableRow
    private lateinit var firstSumTableRow: TableRow
    private lateinit var bonusTableRow: TableRow


    private var listOfColumns: MutableList<MutableList<TextView>> = mutableListOf()

    private val listTextViews1column: MutableList<TextView> = mutableListOf()
    private val listTextViews2column: MutableList<TextView> = mutableListOf()
    private val listTextViews3column: MutableList<TextView> = mutableListOf()
    private val listTextViews4column: MutableList<TextView> = mutableListOf()
    private val listTextViews5column: MutableList<TextView> = mutableListOf()
    private val listTextViews6column: MutableList<TextView> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        endSumTableRow = layoutInflater.inflate(R.layout.endsum_table_row, null) as TableRow
        firstSumTableRow = layoutInflater.inflate(R.layout.endsum_table_row, null) as TableRow
        bonusTableRow = layoutInflater.inflate(R.layout.endsum_table_row, null) as TableRow

        createRowsAndColumns()

        addListener()
    }

    // Temporarily deactivated system back button
    override fun onBackPressed() {}


    private fun createRowsAndColumns() {

        for (i in 0..18) {
            val tableRow: TableRow = layoutInflater.inflate(R.layout.table_row, null) as TableRow
            when (i) {
                0 -> defineTableRow(tableRow, i, "Name:")
                1 -> defineTableRow(tableRow, i, "Einer")
                2 -> defineTableRow(tableRow, i, "Zweier")
                3 -> defineTableRow(tableRow, i, "Dreier")
                4 -> defineTableRow(tableRow, i, "Vierer")
                5 -> defineTableRow(tableRow, i, "Fünfer")
                6 -> defineTableRow(tableRow, i, "Sechser")

                7 -> defineTableRow(firstSumTableRow, i, "Summe:")
                8 -> defineTableRow(bonusTableRow, i, "Bonus")
                9 -> defineTableRow(tableRow, i, "1 Paar")
                10 -> defineTableRow(tableRow, i, "2 Paar")
                11 -> defineTableRow(tableRow, i, "Drei Gleiche")
                12 -> defineTableRow(tableRow, i, "Vier Gleiche")
                13 -> defineTableRow(tableRow, i, "Kleine Straße")
                14 -> defineTableRow(tableRow, i, "Große Straße")
                15 -> defineTableRow(tableRow, i, "Volles Haus")
                16 -> defineTableRow(tableRow, i, "Chance")
                17 -> defineTableRow(tableRow, i, "Yatzy")
                18 -> defineTableRow(endSumTableRow, i, "Endsumme")
            }
        }

        listOfColumns.add(listTextViews1column)
        listOfColumns.add(listTextViews2column)
        listOfColumns.add(listTextViews3column)
        listOfColumns.add(listTextViews4column)
        listOfColumns.add(listTextViews5column)
        listOfColumns.add(listTextViews6column)

    }


    private fun defineTableRow(tableRow: TableRow, numberRow: Int, name: String) {

        if (numberRow != 0 && numberRow != 18 && numberRow != 7 && numberRow != 8) {
            tableRow.children.forEach {
                (it as TextView).inputType = InputType.TYPE_CLASS_NUMBER

            }
            defineColumn(tableRow)
        } else if (numberRow != 0) {
            tableRow.setBackgroundColor(Color.GRAY)
        }

        (tableRow.getChildAt(0) as TextView).text = name

        binding.yatzyTable.addView(tableRow)
    }

    private fun defineColumn(tableRow: TableRow) {
        for (i in 1..tableRow.childCount) {
            when (i) {
                1 -> listTextViews1column.add(tableRow.getChildAt(1) as TextView)
                2 -> listTextViews2column.add(tableRow.getChildAt(2) as TextView)
                3 -> listTextViews3column.add(tableRow.getChildAt(3) as TextView)
                4 -> listTextViews4column.add(tableRow.getChildAt(4) as TextView)
                5 -> listTextViews5column.add(tableRow.getChildAt(5) as TextView)
                6 -> listTextViews6column.add(tableRow.getChildAt(6) as TextView)
            }
        }
    }


    private fun addListener() {

        listOfColumns.forEachIndexed { index, mutableList ->

            mutableList.forEach { textField ->
                textField.doAfterTextChanged {
                    val sum: Int = viewModel.sumUp(mutableList.subList(0, 6))
                    (firstSumTableRow.getChildAt(index + 1) as TextView).text =
                        sum.toString()
                    if (viewModel.checkIfBonus(sum)) {
                        (bonusTableRow.getChildAt(index + 1) as TextView).text = "30"
                    } else {
                        (bonusTableRow.getChildAt(index + 1) as TextView).text = "0"
                    }
                }
            }

            (bonusTableRow.getChildAt(index + 1) as TextView).doAfterTextChanged {
                (endSumTableRow.getChildAt(index + 1) as TextView).text =
                    viewModel.sumUp(mutableList, it.toString().toInt()).toString()
            }
        }
    }


}