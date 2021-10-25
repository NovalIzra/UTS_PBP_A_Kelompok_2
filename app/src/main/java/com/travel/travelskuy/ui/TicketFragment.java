package com.travel.travelskuy.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travel.travelskuy.AreaGuideActivity;
import com.travel.travelskuy.BusActivity;
import com.travel.travelskuy.FligthTicketActivity;
import com.travel.travelskuy.R;
import com.travel.travelskuy.TrainTicketActivity;
import com.travel.travelskuy.databinding.FragmentTicketBinding;

public class TicketFragment extends Fragment {

    FragmentTicketBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket, container, false);
        binding.getLifecycleOwner();

        binding.btnTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext().getApplicationContext(), FligthTicketActivity.class);
                startActivity(intent);
            }
        });

        binding.btntrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext().getApplicationContext(), TrainTicketActivity.class);
                startActivity(intent);
            }
        });

        binding.btnbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext().getApplicationContext(), BusActivity.class);
                startActivity(intent);
            }
        });

        binding.btnareaguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext().getApplicationContext(), AreaGuideActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();

    }
}