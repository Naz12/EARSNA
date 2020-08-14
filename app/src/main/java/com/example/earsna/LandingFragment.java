package com.example.earsna;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LandingFragment extends Fragment {

    private LandingViewModel mViewModel;
    private RecyclerView mRecyclerItems;
    private HomeAdapter mHomeAdapter;
    private LinearLayoutManager mHomeLayoutManager;

    public static LandingFragment newInstance() {
        return new LandingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.landing_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LandingViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerItems = (RecyclerView) getView().findViewById(R.id.list_items);
        mHomeLayoutManager = new LinearLayoutManager(getContext());
        mHomeAdapter = new HomeAdapter(getContext());
        mRecyclerItems.setLayoutManager(mHomeLayoutManager);
        mRecyclerItems.setAdapter(mHomeAdapter);
    }
}