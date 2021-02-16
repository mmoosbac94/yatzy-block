package com.example.yatzyblock.start

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.observe
import com.example.yatzyblock.R
import com.example.yatzyblock.databinding.FragmentStartBinding
import org.koin.android.viewmodel.ext.android.viewModel


class StartFragment : Fragment(R.layout.fragment_start) {

    private lateinit var binding: FragmentStartBinding

    private val viewModel: StartViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStartBinding.inflate(inflater)

        init()

        return binding.root
    }

    private fun init() {
        binding.addPlayerButton.setOnClickListener {
            viewModel.addPlayer(binding.editTextName.text.toString())
        }

        viewModel.listPlayers.observe(viewLifecycleOwner) { player ->
            player.map {
                val textView = TextView(context)
                textView.text = it.name
                binding.nameLayout.addView(textView)
            }
        }
    }
}