package com.travel.travelskuy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.travelskuy.R;
import com.travel.travelskuy.database.entity.OrderTicketEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrderTicketAdapter extends RecyclerView.Adapter<OrderTicketAdapter.ViewAdapter> {
    private List<OrderTicketEntity> list;
    private Context context;
    private Dialog dialog;
    private Dialog btnhapus;
    private ArrayList<OrderTicketEntity> TransaksiList;

    public interface Dialog{
        void onHapus(int position);
    }


    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public OrderTicketAdapter(Context context, List<OrderTicketEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticketlist, parent, false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewAdapter holder, int position) {
        Integer pasanger = list.get(position).passenger;
        String tujuan = list.get(position).to;
        String from = list.get(position).from;
        String waktu = list.get(position).getWaktu();
        String namatiket = list.get(position).getType_ticket();

        holder.namatiket.setText(namatiket);
        holder.tujuan.setText(from + " - " + tujuan);
        holder.kalendar.setText(waktu);
        holder.penumpang.setText(pasanger.toString());



    }

    public void filterList(ArrayList<OrderTicketEntity> filteredList) {
        TransaksiList = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewAdapter extends RecyclerView.ViewHolder {

        TextView namatiket, tujuan, kalendar, penumpang;

        public ViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);
            namatiket = itemView.findViewById(R.id.txtnamatiket);
            tujuan = itemView.findViewById(R.id.txtujuan);
            kalendar = itemView.findViewById(R.id.txtkalender);
            penumpang = itemView.findViewById(R.id.txtpasenger);

        }
    }
}
