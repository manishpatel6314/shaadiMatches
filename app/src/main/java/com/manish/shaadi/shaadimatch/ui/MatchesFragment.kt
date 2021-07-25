package com.manish.shaadi.shaadimatch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.manish.shaadi.binding.FragmentDataBindingComponent
import com.manish.shaadi.di.Injectable
import com.manish.shaadi.di.factory.AppModelFactory
import com.manish.shaadi.helper.AppExecutors
import com.manish.shaadi.helper.Logger
import com.manish.shaadi.shaadimatch.viewmodels.MatchesViewModel
import com.google.gson.Gson
import com.manish.shaadi.R
import com.manish.shaadi.databinding.MatchesFragmentBinding
import javax.inject.Inject

class MatchesFragment : Fragment(), Injectable {

    @Inject
    lateinit var vieModelFactory: AppModelFactory


    private val viewModel: MatchesViewModel by viewModels { vieModelFactory }

    private val dataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var binding: MatchesFragmentBinding
    lateinit var adapter: MatchAdapter

    @Inject
    lateinit var executors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.matches_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MatchAdapter(
            requireContext(),
            executors = executors,
            dataBindingComponent = dataBindingComponent,
            callback = { action, item, pos ->
                viewModel.updateMatchAction(action, userName = item.username)
                adapter.notifyItemChanged(pos)
            }).also {
            binding.recycler.adapter = it
        }

        viewModel.result.observe(viewLifecycleOwner, Observer {
            Logger.e(Thread.currentThread(), "data ${Gson().toJson(it)}")
            it.data?.let {
                adapter.submitList(it)
            }
        })
    }

}