package com.example.smart_bin.presentation.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.smart_bin.App
import com.example.smart_bin.databinding.NewsFragmentBinding
import com.example.smart_bin.presentation.adapters.NewsAdapter
import com.example.smart_bin.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFragment : BaseFragment<NewsFragmentBinding, NewsViewModel>() {
    private lateinit var adapter: NewsAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> NewsFragmentBinding =
        NewsFragmentBinding::inflate

    override lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var vmFactory: NewsViewModel.NewsViewModelFactory

    override fun setup() {
        (requireActivity().application as App).appComponent.injectNews(this)
        viewModel = ViewModelProvider(this, vmFactory)[NewsViewModel::class.java]

        adapter = NewsAdapter(requireContext())
        binding.newsRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.news.collectLatest { pagedData ->
                adapter.submitData(pagedData)
            }
        }
    }
}