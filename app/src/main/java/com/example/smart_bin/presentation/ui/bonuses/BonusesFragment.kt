package com.example.smart_bin.presentation.ui.bonuses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smart_bin.App
import com.example.smart_bin.R
import com.example.smart_bin.databinding.BonusesFragmentBinding
import com.example.smart_bin.presentation.adapters.BonusesAdapter
import com.example.smart_bin.presentation.base.BaseFragment
import javax.inject.Inject

class BonusesFragment : BaseFragment<BonusesFragmentBinding, BonusesViewModel>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BonusesFragmentBinding =
        BonusesFragmentBinding::inflate

    override lateinit var viewModel: BonusesViewModel
    private lateinit var adapter: BonusesAdapter

    @Inject
    lateinit var vmFactory: BonusesViewModel.BonusViewModelFactory

    override fun setup() {
        adapter = BonusesAdapter()
        (requireActivity().application as App).appComponent.injectBonuses(this)
        viewModel = ViewModelProvider(this, vmFactory)[BonusesViewModel::class.java]

        viewModel.bonus.observe(viewLifecycleOwner) { bonuses ->
            if (bonuses.isEmpty()) {
                binding.placeholder.visibility = View.VISIBLE
            } else {
                binding.placeholder.visibility = View.GONE
                bonuses.let { adapter.submitList(it) }
            }
        }

        binding.bonusesRecyclerView.adapter = adapter
        binding.appBar.setNavigationOnClickListener {
            customBackPressed()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    customBackPressed()
                }
            })
    }

    private fun customBackPressed() {
        findNavController().navigate(R.id.action_global_homeFragment)
    }
}