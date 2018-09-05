package com.newport.app.ui.schedules;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.newport.app.R;
import com.newport.app.data.models.response.ScheduleResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tohure on 15/11/17.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleItemViewHolder> {

    private final List<ScheduleResponse> scheduleResponseList;
    private final int widthSystem;

    public interface OnClickSchedulerSwitchListener { void onScheduleSwtichItemClick(); }
    private OnClickSchedulerSwitchListener scheduleSwitchListener;
    void setOnScheduleSwitchClickListener(OnClickSchedulerSwitchListener scheduleSwitchListener) { this.scheduleSwitchListener = scheduleSwitchListener; }

    public interface OnClickSeeScheduleRequest { void onSeeScheduleRequestItemClick(); }
    private OnClickSeeScheduleRequest seeScheduleRequestListener;
    void setOnSeeScheduleRequestClickListener(OnClickSeeScheduleRequest seeScheduleRequestListener) { this.seeScheduleRequestListener = seeScheduleRequestListener; }

    public interface OnClickSchedulerListener { void onScheduleItemClick(ScheduleResponse scheduleResponse); }
    private OnClickSchedulerListener listener;
    void setOnScheduleClickListener(OnClickSchedulerListener listener) { this.listener = listener; }

    ScheduleAdapter(int widthSystem) {
        this.widthSystem = widthSystem;
        this.scheduleResponseList = new ArrayList<>();
    }

    public void addData(List<ScheduleResponse> scheduleResponseList) {
        this.scheduleResponseList.clear();
        this.scheduleResponseList.addAll(scheduleResponseList);
        notifyDataSetChanged();
    }

    @Override
    public ScheduleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedules, parent, false);
        return new ScheduleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleItemViewHolder holder, int position) {
        Picasso.with(holder.imgScheduleEnterprise.getContext())
                .load(scheduleResponseList.get(position).getLogo())
                .fit().centerInside()
                .placeholder(R.drawable.newport_gray)
                .error(android.R.drawable.ic_dialog_alert)
                .into(holder.imgScheduleEnterprise);
    }

    @Override
    public int getItemCount() {
        return scheduleResponseList.size();
    }

    public class ScheduleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgScheduleEnterprise;
        private Button btnSeeSchedule;
        private Button btnScheduleOperations;
        private Button btnSeeSwitchScheduleRequest;

        ScheduleItemViewHolder(View itemView) {
            super(itemView);
            imgScheduleEnterprise = itemView.findViewById(R.id.imgScheduleEnterprise);

            android.view.ViewGroup.LayoutParams layoutParams = imgScheduleEnterprise.getLayoutParams();

            layoutParams.height = (widthSystem / 2) - 20;
            imgScheduleEnterprise.setLayoutParams(layoutParams);

            btnSeeSchedule = itemView.findViewById(R.id.btnSeeSchedule);
            btnScheduleOperations = itemView.findViewById(R.id.btnScheduleOperations);
            btnSeeSwitchScheduleRequest = itemView.findViewById(R.id.btnSeeSwitchScheduleRequest);
            btnSeeSchedule.setOnClickListener(this);
            btnScheduleOperations.setOnClickListener(this);
            btnSeeSwitchScheduleRequest.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnScheduleOperations) {
                if (scheduleSwitchListener != null) {
                    scheduleSwitchListener.onScheduleSwtichItemClick();
                }
            } else if (view.getId() == R.id.btnSeeSwitchScheduleRequest) {
                if (seeScheduleRequestListener != null) {
                    seeScheduleRequestListener.onSeeScheduleRequestItemClick();
                }
            } else {
                if (listener != null) {
                    listener.onScheduleItemClick(scheduleResponseList.get(getAdapterPosition()));
                }
            }

        }
    }
}
