package com.example.yatzyblock.game

import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.example.yatzyblock.EntryType
import com.example.yatzyblock.R
import com.example.yatzyblock.databinding.FragmentGameBinding
import com.example.yatzyblock.models.Player
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var binding: FragmentGameBinding

    private val listRows: MutableList<TableRow> = mutableListOf()

    private lateinit var viewModel: GameViewModel
    private lateinit var argument: GameFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        argument = GameFragmentArgs.fromBundle(requireArguments())

        val viewModel: GameViewModel by viewModel {
            parametersOf(argument.players)
        }
        this.viewModel = viewModel

        binding = FragmentGameBinding.inflate(inflater)

        init()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.storeHighScore_menuEntry -> {
                viewModel.storeHighScorePlayer(viewModel.playerList.value!!)
                return true
            }
            R.id.showHighScore_menuEntry -> {
                val highScorePlayer: Player = viewModel.getHighScorePlayer()
                val dialogBuilder = AlertDialog.Builder(requireContext())
                val message = TextView(requireContext())
                message.setPadding(90)
                message.textSize = 20.0F
                message.setTextColor(Color.BLACK)
                message.text = getString(
                    R.string.highScoreMessage,
                    highScorePlayer.name,
                    highScorePlayer.endSumme
                )
                dialogBuilder.setTitle("Aktueller Highscore")
                dialogBuilder.setView(message)
                val alert = dialogBuilder.create()
                alert.show()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
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
                (listRows[EntryType.Bonus.ordinal].getChildAt(index + 1) as TextView).text =
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
            firstColumnTextView.setPadding(30, 0, 0, 0)
            tableRow.addView(firstColumnTextView)
            binding.yatzyTableLayout.addView(tableRow)
        }

        listRows.forEachIndexed { rowIndex, row ->
            players.forEach { player ->
                when (EntryType.values()[rowIndex]) {
                    EntryType.PlayerName -> {
                        val nameTextView = TextView(context)
                        setupTextView(nameTextView, row, player.name)
                    }
                    EntryType.SummeOben -> {
                        val sumTopTextView = TextView(context)
                        setupTextView(sumTopTextView, row)
                    }
                    EntryType.Bonus -> {
                        val bonusTextView = TextView(context)
                        setupTextView(bonusTextView, row)
                    }
                    EntryType.EndSumme -> {
                        val sumBottomTextView = TextView(context)
                        setupTextView(sumBottomTextView, row)
                    }
                    else -> {
                        val editTextView = EditText(context)
                        editTextView.gravity = Gravity.CENTER
                        editTextView.inputType = InputType.TYPE_CLASS_NUMBER
                        editTextView.filters = arrayOf<InputFilter>(LengthFilter(3))
                        editTextView.doOnTextChanged { text, _, _, _ ->
                            var value = 0
                            if (text != null) {
                                if (text.isNotEmpty()) value = text.toString().toInt()
                            }
                            viewModel.addValueToPlayer(
                                value = value,
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

    private fun setupTextView(textView: TextView, row: TableRow, playerName: String = "") {
        textView.gravity = Gravity.CENTER
        textView.text = playerName
        row.setBackgroundColor(Color.LTGRAY)
        row.setPadding(0, 20, 0, 20)
        row.addView(textView)
    }

}