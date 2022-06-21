package com.thingsflow.internapplication.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.thingsflow.internapplication.MainActivity
import com.thingsflow.internapplication.R
import com.thingsflow.internapplication.databinding.MainFragmentBinding


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onResume()
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        var org = "google"
        var repo = "dagger"
        var img_url = "https://s3.ap-northeast-2.amazonaws.com/hellobot-kr-test/image/main_logo.png"

        binding.titleTxtView.setOnClickListener(View.OnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireActivity())
            dialogBuilder.setTitle("입력")
            dialogBuilder.setMessage("Oranizaion/Repository")
            val popupView = layoutInflater.inflate(R.layout.input_popup, null)
            dialogBuilder.setView(popupView)
                .setPositiveButton("Search", DialogInterface.OnClickListener{ dialog, id ->
                    val textView1: TextView = popupView.findViewById(R.id.input_org)
                    val textView2: TextView = popupView.findViewById(R.id.input_repo)
                    viewModel.updateList(textView1.text.toString(), textView2.text.toString())
                    dialog.cancel()
                    org = textView1.text.toString()
                    repo = textView2.text.toString()
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener{ dialog, id ->
                    dialog.cancel()
                })
            dialogBuilder.create()
            dialogBuilder.show()
        })
        glide = Glide.with(this)

        var adapter = RecyclerAdapter(this, org, repo, img_url,glide)
        binding.recyclerView.adapter = adapter
        viewModel.getOrg()?.let{
            org = it
        }
        viewModel.getRepo()?.let{
            repo = it
        }
        Log.d("Log" ,"${org}, ${repo}")
        viewModel.updateList(org, repo)
        viewModel.issueList.observe(viewLifecycleOwner, Observer {
            viewModel.updateInput(org, repo)
            binding.titleTxtView.text = viewModel.getTitle()
            adapter.setNewItems(viewModel.getList())
        })

        viewModel.b.observe(viewLifecycleOwner, Observer{
            if(viewModel.b.value == false){
                val errDialogBuilder = AlertDialog.Builder(requireActivity())
                errDialogBuilder.setTitle("ERROR")
                errDialogBuilder.setMessage("A Non-existence Repository")
                errDialogBuilder.setPositiveButton("OK", DialogInterface.OnClickListener { popdialog, i -> popdialog.cancel() })
                errDialogBuilder.create()
                errDialogBuilder.show()
                viewModel.changeB(true)
            }
        })
    }
    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as MainActivity).setActionBarTitle("InterApplication")
        }
    }

}