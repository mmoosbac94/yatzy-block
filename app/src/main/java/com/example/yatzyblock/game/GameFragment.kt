package com.example.yatzyblock.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        for(player in players) {
            val row = TableRow(context)
            val textViewName = TextView(context)
            textViewName.text = player.name
            row.addView(textViewName)
            binding.yatzyTableLayout.addView(row)
        }
    }

}