package com.thingsflow.internapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.thingsflow.internapplication.databinding.InputDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputDialog : DialogFragment() {
    companion object {
        fun newInstance() = InputDialog()
    }

    private var _binding: InputDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InputDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setLayout(1000, 600)
        with(binding) {
            completeBtn.setOnClickListener(View.OnClickListener {
                val org_name = orgEt.text.toString()
                val repo_name = repoEt.text.toString()
                viewModel.changeTitle(org_name, repo_name)
                dismiss()
            })
        }
    }
}