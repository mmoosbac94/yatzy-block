package com.example.yatzyblock.game

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.observe
import com.example.yatzyblock.EntryType
import com.example.yatzyblock.R
import com.example.yatzyblock.databinding.FragmentGameBinding
import com.example.yatzyblock.models.Player
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.w3c.dom.Text


class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var binding: FragmentGameBinding

    private val listRows: MutableList<TableRow> = mutableListOf()

    private lateinit var viewModel: GameViewModel
    private lateinit var argument: GameFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        argument = GameFragmentArgs.fromBundle(requireArguments())

        val viewModel: GameViewModel by viewModel {
            parametersOf(argument.players)
        }
        this.viewModel = viewModel

        binding = FragmentGameBinding.inflate(inflater)

        init()

        return binding.root
    }


    private fun init() {

        buildTableLayout(argument.players)

        addPlayerListObserver()
    }

    private fun addPlayerListObserver() {
        viewModel.playerList.observe(viewLifecycleOwner) { playerList ->
            for ((index, player) in playerList.withIndex()) {
                (listRows[EntryType.SummeOben.ordinal].getChildAt(index + 1) as TextView).text =
                    player.summeOben.toString()
                (listRows[EntryType.HatBonus.ordinal].getChildAt(index + 1) as TextView).text =
                    player.bonus.toString()
                (listRows[EntryType.EndSumme.ordinal].getChildAt(index + 1) as TextView).text =
                    player.endSumme.toString()
            }
        }
    }


    private fun buildTableLayout(players: Array<Player>) {

        EntryType.values().map { entryType ->
            val tableRow = TableRow(context)
            listRows.add(tableRow)
            val firstColumnTextView = TextView(context)
            firstColumnTextView.text = entryType.entry
            tableRow.addView(firstColumnTextView)
            binding.yatzyTableLayout.addView(tableRow)
        }

        listRows.forEachIndexed { rowIndex, row ->
            players.forEach { player ->
                when (EntryType.values()[rowIndex]) {
                    EntryType.PlayerName -> {
                        val nameTextView = TextView(context)
                        nameTextView.text = player.name
                        row.addView(nameTextView)
                    }
                    EntryType.SummeOben -> {
                        val sumTopTextView = TextView(context)
                        row.addView(sumTopTextView)
                    }
                    EntryType.HatBonus -> {
                        val bonusTextView = TextView(context)
                        row.addView(bonusTextView)
                    }
                    EntryType.EndSumme -> {
                        val sumBottomTextView = TextView(context)
                        row.addView(sumBottomTextView)
                    }
                    else -> {
                        val editTextView = EditText(context)
                        editTextView.inputType = InputType.TYPE_CLASS_NUMBER
                        editTextView.doOnTextChanged { text, _, _, _ ->
                            viewModel.addValueToPlayer(
                                value = text.toString().toInt(),
                                player = player,
                                entryType = EntryType.values()[rowIndex]
                            )
                        }
                        row.addView(editTextView)
                    }
                }
            }
        }

    }

}