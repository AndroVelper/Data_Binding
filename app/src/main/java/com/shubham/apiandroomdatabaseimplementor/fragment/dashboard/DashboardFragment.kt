package com.shubham.apiandroomdatabaseimplementor.fragment.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shubham.apiandroomdatabaseimplementor.adapter.RecyclerViewAdapter
import com.shubham.apiandroomdatabaseimplementor.data.ApiResponse
import com.shubham.apiandroomdatabaseimplementor.databinding.FragmentDashboardBinding
import com.shubham.apiandroomdatabaseimplementor.utils.Status
import com.shubham.apiandroomdatabaseimplementor.utils.hideView
import com.shubham.apiandroomdatabaseimplementor.utils.showToast
import com.shubham.apiandroomdatabaseimplementor.utils.showView

class DashboardFragment : Fragment() {
    private val binding: FragmentDashboardBinding by lazy {
        FragmentDashboardBinding.inflate(layoutInflater)
    }

    private var viewModel: DashBoardViewModel? = null
    private var adapter: RecyclerViewAdapter? = null
    private var items: ArrayList<ApiResponse> = arrayListOf()

    companion object {
        private var isOldDataUpdated = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        initializeVariable()
        initObserver()
        initListener()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel?.isDatabaseEmpty()
    }

    private fun initListener() {
        binding.apply {
            srlRefreshView.setOnRefreshListener {
                viewModel?.getAllData()
            }
        }
    }

    private fun initObserver() {
        viewModel?.apiResponse?.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.Success -> {
                    val isRefreshing = binding.pbRecyclerDataLoader.isVisible
                    binding.pbRecyclerDataLoader.hideView()
                    binding.pbProgressLoader.hideView()
                    if (!isRefreshing) {
                        items.clear()
                    }
                    val oldItemCount = items.size
                    if (it.data is ArrayList<*>) {
                        val data = it.data as Collection<*>
                        val typeCase = data.filterIsInstance<ApiResponse>()
                        items.addAll(typeCase)
                    }
                    binding.srlRefreshView.isRefreshing = false
                    provideUpdateToAdapter(oldItemCount)
                }

                Status.DbError -> {
                    binding.pbRecyclerDataLoader.hideView()
                    binding.pbProgressLoader.hideView()
                    if(it.error?.message == "empty"){
                        val size = items.size
                        items.clear()
                        binding.rvList.adapter?.notifyItemRangeRemoved(0 , size)
                    }
                    it.error?.message?.showToast(requireContext())
                }

                Status.Error -> {
                    binding.pbRecyclerDataLoader.hideView()
                    binding.pbProgressLoader.hideView()
                }
                Status.IsLoading -> {}
            }
        }


        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount && isOldDataUpdated) {
                    binding.pbRecyclerDataLoader.showView()

                    isOldDataUpdated = false
                    Handler(Looper.getMainLooper()).postDelayed({
                        viewModel?.getDataFromDatabase(items.size )
                        isOldDataUpdated = true
                    }, 2000)

                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun provideUpdateToAdapter(oldItemCount: Int) {
        if (oldItemCount == 0){
          binding.rvList.adapter?.notifyDataSetChanged()
        }
        else{
            // case 2 item list is not empty
            binding.rvList.adapter?.notifyItemRangeInserted(oldItemCount , items.size)
        }

    }


    private fun initializeVariable() {
        viewModel = ViewModelProvider(this)[DashBoardViewModel::class.java]
    }

    private fun setUpRecyclerView() {
         adapter = RecyclerViewAdapter(items)
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.adapter = adapter
    }

}