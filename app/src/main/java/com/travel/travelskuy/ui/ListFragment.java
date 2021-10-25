package com.travel.travelskuy.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travel.travelskuy.R;
import com.travel.travelskuy.adapter.OrderTicketAdapter;
import com.travel.travelskuy.database.AppDatabase;
import com.travel.travelskuy.database.entity.OrderTicketEntity;
import com.travel.travelskuy.databinding.FragmentListBinding;
import com.travel.travelskuy.session.SessionManager;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    FragmentListBinding binding;
    private AppDatabase database;
    private OrderTicketAdapter orderAdapter;
    private List<OrderTicketEntity> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list,container,false);
        binding.getLifecycleOwner();


        database = AppDatabase.getInstance(requireContext().getApplicationContext());
        list.clear();
        list.addAll(database.orderTicketDao().getorder(SessionManager.getIsUsername(requireContext())));

        orderAdapter = new OrderTicketAdapter(requireContext().getApplicationContext(), list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext().getApplicationContext(), RecyclerView.VERTICAL, false);
        binding.rvTicketlist.setLayoutManager(layoutManager);
        binding.rvTicketlist.setAdapter(orderAdapter);


        return binding.getRoot();

    }


}