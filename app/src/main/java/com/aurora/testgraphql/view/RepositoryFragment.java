package com.aurora.testgraphql.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aurora.testgraphql.R;
import com.aurora.testgraphql.Utility;
import com.aurora.testgraphql.adapter.Repository_Adapter;
import com.aurora.testgraphql.databinding.RepositoryFragmentBinding;
import com.aurora.testgraphql.viewModel.RepositoryViewModel;
import com.example.GetUserRepositoryQuery;

public class RepositoryFragment extends Fragment {

    private RepositoryViewModel mViewModel;
    RecyclerView recycler;
    RepositoryFragmentBinding binding;
    Repository_Adapter adapter;

    public static RepositoryFragment newInstance() {
        return new RepositoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.repository_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RepositoryViewModel.class);
        mViewModel.setContext(getContext());
        init();
        initRecycler();
        getRepositories();
        // TODO: Use the ViewModel
    }
    private void init() {
        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRepositories();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.refresh.setRefreshing(false);
                    }
                },3000);
            }
        });
    }
    private void initRecycler() {
        recycler = binding.recycler;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        recycler.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


        adapter = new Repository_Adapter(new Repository_Adapter.Clik_Item() {
            @Override
            public void Onrecived(GetUserRepositoryQuery.Node infoResult) {
               Utility.openWebSite(getContext(),infoResult.url().toString());

            }
        });
        recycler.setAdapter(adapter);


    }

    public void getRepositories(){
        mViewModel.getRepository().observe(getViewLifecycleOwner(), new Observer<GetUserRepositoryQuery.Data>() {
            @Override
            public void onChanged(GetUserRepositoryQuery.Data data) {
                adapter.setItems(data.viewer().repositories().nodes());
                binding.refresh.setRefreshing(false);
            }
        });
    }
}