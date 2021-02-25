package com.example.yatzyblock.game

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import com.example.yatzyblock.R
import com.example.yatzyblock.databinding.FragmentGameBinding
import com.example.yatzyblock.models.Player
import org.koin.android.viewmodel.ext.android.viewModel


class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var binding: FragmentGameBinding

    private val viewModel: GameViewModel by viewModel()

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

        setUpPlayers(arguments.players)
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

        listRows.forEachIndexed { rowIndex, row ->
            players.forEach { player ->
                if (rowIndex == 0) {
                    val nameTextView = TextView(context)
                    nameTextView.text = player.name
                    row.addView(nameTextView)
                } else {
                    val editTextView = EditText(context)
                    editTextView.inputType = InputType.TYPE_CLASS_NUMBER
                    editTextView.doOnTextChanged { text, _, _, _ ->
                        viewModel.addValueToPlayer(text.toString().toInt(), player, rowIndex)
                    }
                    row.addView(editTextView)
                }
            }
        }

    }

    private fun setUpPlayers(players: Array<Player>) {
        viewModel.addPlayers(players)
    }

}