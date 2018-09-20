package com.newport.app.ui.scheduleprocess;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newport.app.R;
import com.newport.app.data.models.response.SwitchScheduleEmailResponse;
import com.newport.app.data.models.response.SwitchSchedulesPendingRequestResponse;

import java.util.ArrayList;
import java.util.List;

public class SwitchSchedulePendingRequestAdapter  extends RecyclerView.Adapter<SwitchSchedulePendingRequestAdapter.PendingRequestsItemViewHolder> {

    private final List<SwitchSchedulesPendingRequestResponse> switchScheduleEmailResponseList;

    //Listener for onClick in news
    public interface OnClickPendingRequestListener { void onPendingRequestItemClick(SwitchSchedulesPendingRequestResponse lastUser); }
    private OnClickPendingRequestListener listener;
    public void setOnPendingRequestClickListener(OnClickPendingRequestListener listener) { this.listener = listener; }

    public SwitchSchedulePendingRequestAdapter() {
        this.switchScheduleEmailResponseList = new ArrayList<>();
    }

    public void addData(List<SwitchSchedulesPendingRequestResponse> responseList) {
        this.switchScheduleEmailResponseList.clear();
        this.switchScheduleEmailResponseList.addAll(responseList);
        notifyDataSetChanged();
    }

    @Override
    public PendingRequestsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_request, parent, false);
        return new PendingRequestsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PendingRequestsItemViewHolder holder, int position) {
        holder.lblPendingRequest.setText(switchScheduleEmailResponseList.get(position).getMailer_name());
    }

    @Override
    public int getItemCount() {
        return switchScheduleEmailResponseList.size();
    }

    class PendingRequestsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView lblPendingRequest;

        PendingRequestsItemViewHolder(View itemView) {
            super(itemView);
            lblPendingRequest = itemView.findViewById(R.id.lblPendingRequest);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onPendingRequestItemClick(switchScheduleEmailResponseList.get(getAdapterPosition()));
            }
        }
    }
}
