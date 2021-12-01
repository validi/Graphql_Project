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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aurora.testgraphql.R;
import com.aurora.testgraphql.Utility;
import com.aurora.testgraphql.databinding.ProfileFragmentBinding;
import com.aurora.testgraphql.viewModel.ProfileViewModel;
import com.example.GetProfileQuery;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
ProfileFragmentBinding binding;
GetProfileQuery.Data profile;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.profile_fragment,container,false);

        return binding.getRoot();

       // return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        mViewModel.setContext(getContext());
        init();
        click();
        getProfile();
        // TODO: Use the ViewModel
    }

    private void init() {
        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProfile();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.refresh.setRefreshing(false);
                    }
                },3000);
            }
        });
    }

    private void click() {
        binding.btnOpenPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Utility.openWebSite(getContext(),profile.viewer().url().toString());

            }
        });
    }

    public void getProfile(){
        mViewModel.getRepository().observe(getViewLifecycleOwner(), new Observer<GetProfileQuery.Data>() {
            @Override
            public void onChanged(GetProfileQuery.Data data) {
                binding.refresh.setRefreshing(false);
                binding.setData(data);
                profile=data;
                Picasso.with(getContext()).load(data.viewer().avatarUrl().toString()).into(binding.imgProfile);

            }
        });
    }

}