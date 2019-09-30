package com.example.login.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.model.Leads;

import java.util.List;

public class LeadRegisterAdapter extends RecyclerView.Adapter<LeadRegisterAdapter.ViewHolder> {

    List<Leads> Leadlist;
    private Context context;


    public LeadRegisterAdapter(List<Leads> lList) {
        this.Leadlist = lList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {

        if (Leadlist != null)
            return Leadlist.size();
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Leads lead = Leadlist.get(position);
        Log.i("lead", lead.getOpp_name());
        //healah salah iki lo
        //oalah iku sg nde settext uduk teko model?
        //yoteko model tapi kan sng di celuk variabel e
        //yowes pokok ngunu lah
        //var sg di instance yoo sg diceluk?
        //yo, pokok ngono
        // lek sg jaremu mau iku piye?
        //iki wes iso? mosok e
        //wes, coba iki
        //mau carane getter setter piye
        //cntrl + alt + inser
        //metu ws sesuai okespi, terus setter pisan diagnti
        holder.tvLead.setText(lead.getLead_id());
        holder.tvOpty.setText(lead.getOpp_name());
        holder.tvSales.setText(lead.getNik());
        holder.tvContact.setText(lead.getId_customer());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLead, tvOpty, tvSales, tvContact;

        public ViewHolder(View itemView) {
            super(itemView);

            tvLead = itemView.findViewById(R.id.mlead);
            tvOpty = itemView.findViewById(R.id.mopty);
            tvSales = itemView.findViewById(R.id.mSales);
            tvContact = itemView.findViewById(R.id.mContact);

        }
    }
}
