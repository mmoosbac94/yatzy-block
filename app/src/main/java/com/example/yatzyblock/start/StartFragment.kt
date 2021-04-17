package com.example.yatzyblock.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.yatzyblock.R
import com.example.yatzyblock.databinding.FragmentStartBinding
import com.example.yatzyblock.models.Player
import org.koin.android.viewmodel.ext.android.viewModel


class StartFragment : Fragment(R.layout.fragment_start) {

    private lateinit var binding: FragmentStartBinding

    private val viewModel: StartViewModel by viewModel()

    private lateinit var playerList: List<Player>

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
            binding.editTextName.setText("")
        }

        viewModel.listPlayers.observe(viewLifecycleOwner) { playerList ->
            this.playerList = playerList
            val nameList = playerList.map { it.name }
            val adapter = context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_item,
                    nameList
                )
            }
            binding.playerListView.adapter = adapter
            adapter?.notifyDataSetChanged()
        }

        binding.startButton.setOnClickListener {
            val nameListArray: Array<Player> = playerList.toTypedArray()
            findNavController().navigate(
                StartFragmentDirections.actionStartFragmentToGameFragment(
                    nameListArray
                )
            )
        }

    }
}