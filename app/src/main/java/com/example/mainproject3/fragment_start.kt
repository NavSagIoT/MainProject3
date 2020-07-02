package com.example.mainproject3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mainproject3.databinding.LayoutStartBinding


class fragment_start : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: LayoutStartBinding = DataBindingUtil.inflate<LayoutStartBinding>(
            inflater,
            R.layout.layout_start,
            container,
            false
        )
        binding.btnStart1.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(fragment_startDirections.actionFragmentStartToFragmentLogin())
            setHasOptionsMenu(true)
        }
        binding.btnStart2.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(fragment_startDirections.actionFragmentStartToFragmentSignup())
            setHasOptionsMenu(true)
        }
        return binding.root


    }
}