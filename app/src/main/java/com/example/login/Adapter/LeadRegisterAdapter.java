package com.example.login.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.model.Leads;

import java.util.ArrayList;
import java.util.List;

public class LeadRegisterAdapter extends RecyclerView.Adapter<LeadRegisterAdapter.ViewHolder> implements Filterable {

    List<Leads> Leadlist;
    List<Leads> filteredList;
    ILeadAdapter mILeadAdapter;

    public LeadRegisterAdapter(Context context, List<Leads> lList) {
        this.Leadlist = lList;
        this.filteredList = lList;
        mILeadAdapter = (ILeadAdapter) context;
    }

    public interface ILeadAdapter {
        void doClick(int pos);
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
        holder.etClosing_date.setText(lead.getClosing_date());
        holder.tvStatus.setText(lead.getResult());
        holder.tvamount.setText(lead.getAmount());
        holder.tvinfo.setText(lead.getInfo());

    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    filteredList = Leadlist;
                } else {
                    List<Leads> filteredList2 = new ArrayList<>();
                    for (Leads lead : Leadlist) {
                        if (lead.getLead_id().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList2.add(lead);
                        }
                        filteredList = filteredList2;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                filteredList = (List<Leads>) results.values;
                notifyDataSetChanged();
            }


        };

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLead, tvOpty, tvSales, tvContact, etClosing_date, tvStatus, tvamount, tvinfo;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mILeadAdapter.doClick(getAdapterPosition());
                }
            });

            tvLead = itemView.findViewById(R.id.mlead);
            tvOpty = itemView.findViewById(R.id.mopty);
            tvSales = itemView.findViewById(R.id.mSales);
            tvContact = itemView.findViewById(R.id.mContact);
            etClosing_date = itemView.findViewById(R.id.mClosing_date);
            tvStatus = itemView.findViewById(R.id.mStatus);
            tvamount = itemView.findViewById(R.id.maamount);
            tvinfo = itemView.findViewById(R.id.minfo);

        }
    }
}
