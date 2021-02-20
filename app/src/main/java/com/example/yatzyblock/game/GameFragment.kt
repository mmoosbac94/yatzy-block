package com.example.yatzyblock.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import com.example.yatzyblock.R
import com.example.yatzyblock.databinding.FragmentGameBinding
import com.example.yatzyblock.models.Player


class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGameBinding.inflate(inflater)

        init()

        return binding.root
    }


    private fun init() {
        val arguments = GameFragmentArgs.fromBundle(requireArguments())
        buildTableLayout(arguments.players)
    }


    private fun buildTableLayout(players: Array<Player>) {

        val listRows: MutableList<TableRow> = mutableListOf()

        var i = 0
        while (i < 18) {
            val tableRow = TableRow(context)
            listRows.add(tableRow)
            val firstColumnTextView = TextView(context)
            firstColumnTextView.text = "name$i"
            tableRow.addView(firstColumnTextView)
            binding.yatzyTableLayout.addView(tableRow)
            i++
        }

        listRows.forEachIndexed { indexRows, row ->
            players.forEachIndexed { indexPlayers, player ->
                if (indexRows == 0) {
                    val nameTextView = TextView(context)
                    nameTextView.text = player.name
                    row.addView(nameTextView)
                } else {
                    val editTextView = EditText(context)
                    row.addView(editTextView)
                }
            }
        }

    }

}