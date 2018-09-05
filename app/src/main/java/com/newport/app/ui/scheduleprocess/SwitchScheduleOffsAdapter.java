package com.newport.app.ui.scheduleprocess;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newport.app.R;
import com.newport.app.data.models.response.UserScheduleResponse;

import java.util.ArrayList;
import java.util.List;

public class SwitchScheduleOffsAdapter extends RecyclerView.Adapter<SwitchScheduleOffsAdapter.DaysOffItemViewHolder>  {

    private final List<UserScheduleResponse> daysOffList;

    //Listener for onClick in news
    public interface OnClickUserListener { void onUserItemClick(UserScheduleResponse lastUser); }
    private OnClickUserListener listener;
    public void setOnUserClickListener(OnClickUserListener listener) { this.listener = listener; }

    public SwitchScheduleOffsAdapter() {
        this.daysOffList = new ArrayList<>();
    }

    public void addData(List<UserScheduleResponse> responseList) {
        this.daysOffList.clear();
        this.daysOffList.addAll(responseList);
        notifyDataSetChanged();
    }

    @Override
    public DaysOffItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_offs, parent, false);
        return new DaysOffItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaysOffItemViewHolder holder, int position) {
        holder.lblDaysOff.setText(daysOffList.get(position).getUser_name());
    }

    @Override
    public int getItemCount() {
        return daysOffList.size();
    }

    class DaysOffItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private final TextView lblDaysOff;

        DaysOffItemViewHolder(View itemView) {
            super(itemView);
            lblDaysOff = itemView.findViewById(R.id.lblDaysOff);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onUserItemClick(daysOffList.get(getAdapterPosition()));
            }
        }
    }
}
