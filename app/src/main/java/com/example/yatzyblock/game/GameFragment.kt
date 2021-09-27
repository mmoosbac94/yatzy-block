package com.example.yatzyblock.game

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.yatzyblock.EntryType
import com.example.yatzyblock.R
import com.example.yatzyblock.databinding.FragmentGameBinding
import com.example.yatzyblock.models.Player
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import android.widget.Toast


class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var binding: FragmentGameBinding

    private val listRows: MutableList<TableRow> = mutableListOf()

    private lateinit var viewModel: GameViewModel
    private lateinit var argument: GameFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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
                viewModel.calculateWinner()
                viewModel.storePlayerData()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {

        buildTableLayout(argument.players)

        addPlayerListObserver()
        addToastObserver()
    }

    private fun addToastObserver() {
        viewModel.toastMessage.observe(viewLifecycleOwner) { toastMessage ->
            Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT).show()
        }
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
            if (entryType == EntryType.PlayerName || entryType == EntryType.SummeOben
                || entryType == EntryType.EndSumme || entryType == EntryType.Bonus
            ) {
                firstColumnTextView.setTypeface(null, Typeface.BOLD)
                firstColumnTextView.textSize = 17.0F
            }
            firstColumnTextView.setPadding(30, 0, 0, 0)
            tableRow.addView(firstColumnTextView)
            tableRow.gravity = Gravity.CENTER_VERTICAL
            binding.yatzyTableLayout.addView(tableRow)
        }

        listRows.forEachIndexed { rowIndex, row ->
            players.forEach { player ->
                when (EntryType.values()[rowIndex]) {
                    EntryType.PlayerName -> {
                        val nameTextView = TextView(context)
                        customizeValueTextView(nameTextView)
                        setupTextView(nameTextView, row, player.name)
                    }
                    EntryType.SummeOben -> {
                        val sumTopTextView = TextView(context)
                        customizeValueTextView(sumTopTextView)
                        setupTextView(sumTopTextView, row)
                    }
                    EntryType.Bonus -> {
                        val bonusTextView = TextView(context)
                        customizeValueTextView(bonusTextView)
                        setupTextView(bonusTextView, row)
                    }
                    EntryType.EndSumme -> {
                        val sumBottomTextView = TextView(context)
                        customizeValueTextView(sumBottomTextView)
                        setupTextView(sumBottomTextView, row)
                    }
                    else -> {
                        val container = LinearLayout(requireContext())
                        val lp = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        lp.setMargins(0, 20, 20, 20)
                        val editTextView = EditText(context)
                        editTextView.layoutParams = lp
                        editTextView.setTextColor(Color.WHITE)
                        editTextView.setBackgroundResource(R.drawable.border_text)
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
                        container.addView(editTextView, lp)
                        row.addView(container)
                    }
                }
            }
        }
    }

    private fun customizeValueTextView(textView: TextView) {
        textView.textSize = 20.0F
        textView.setTypeface(null, Typeface.BOLD)
    }

    private fun setupTextView(textView: TextView, row: TableRow, playerName: String = "") {
        textView.gravity = Gravity.CENTER
        textView.text = playerName
        row.setBackgroundColor(Color.LTGRAY)
        row.setPadding(0, 20, 0, 20)
        row.addView(textView)
    }

}