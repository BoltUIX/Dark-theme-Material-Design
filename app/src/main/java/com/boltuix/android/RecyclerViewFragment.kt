package com.boltuix.android

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.boltuix.android.databinding.RecyclerViewFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RecyclerViewFragment : Fragment() {
    private var _binding: RecyclerViewFragmentBinding? = null

    private lateinit var adapterOrder: RecyclerViewAdapter

    private val viewModel: RecyclerViewViewModel by viewModels()
    private val viewModelActivity: NotesViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = RecyclerViewFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //'setHasOptionsMenu(Boolean): Unit' is deprecated. Deprecated in Java
        //https://stackoverflow.com/questions/71917856/sethasoptionsmenuboolean-unit-is-deprecated-deprecated-in-java
        // The usage of an interface lets you inject your own implementation

        val menuHost: MenuHost = requireActivity()
        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_main, menu)

                // Set the item state
                lifecycleScope.launch {
                    val isChecked = viewModelActivity.getUIMode.first()
                    val item = menu.findItem(R.id.action_night_mode)
                    item.isChecked = isChecked
                    setUIMode(item, isChecked)
                }

            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_night_mode -> {
                        menuItem.isChecked = !menuItem.isChecked
                        setUIMode(menuItem, menuItem.isChecked)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)



        binding.recyclerView.apply {


            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

            hasFixedSize()
            adapterOrder = RecyclerViewAdapter(event = { _, item ->
                   Snackbar.make(binding.recyclerView, item.label, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            })
            adapter= adapterOrder
        }

        viewModel.liveNewsData.observe(viewLifecycleOwner) { response ->
            //adapterOrder.itemDiffer.submitList(response)
            adapterOrder.submitList(response)

            binding.emptyStateLayout.run { if (response.isNullOrEmpty()) show() else hide() }

        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            viewModelActivity.saveToDataStore(true)
            item.setIcon(R.drawable.ic_night)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            viewModelActivity.saveToDataStore(false)
            item.setIcon(R.drawable.ic_day)

        }
    }



}

