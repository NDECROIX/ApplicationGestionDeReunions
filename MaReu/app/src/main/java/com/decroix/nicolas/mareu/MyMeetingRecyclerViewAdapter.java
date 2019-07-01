package com.decroix.nicolas.mareu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.decroix.nicolas.mareu.model.Meeting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    private List<Meeting> meetings;

    public MyMeetingRecyclerViewAdapter(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_meeting_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Meeting meeting = meetings.get(position);
        String title = meeting.getLocation() + " - " +
                meeting.getTimeStart() + " - " +
                meeting.getName();

        holder.title.setText(title);
        holder.mail.setText(meeting.getEmail());
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_meeting_item_iv_delete)
        ImageView delete;
        @BindView(R.id.list_meeting_item_tv_mail)
        TextView mail;
        @BindView(R.id.list_meeting_item_tv_title)
        TextView title;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
