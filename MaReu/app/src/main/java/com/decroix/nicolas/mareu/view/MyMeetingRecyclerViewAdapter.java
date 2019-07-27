package com.decroix.nicolas.mareu.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    OnClickMeetingListener callback;

    public MyMeetingRecyclerViewAdapter(OnClickMeetingListener callback) {
        this.callback = callback;
    }

    private List<Meeting> meetings;

    private static int randomColor(Meeting meeting) {
        int color = (int) (meeting.getDateTime().getTime() % 255);
        return Color.argb(255, color, 200, color);
    }

    public void updateList(List<Meeting> meetings) {
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

        holder.avatar.setColorFilter(randomColor(meeting));
        holder.title.setText(meeting.meetingTitleToString());
        holder.mail.setText(meeting.getEmails());

        holder.delete.setOnClickListener(v -> callback.onClickDeleteMeeting(meeting));
        holder.itemView.setOnClickListener(v -> callback.onClickMeeting(meeting));
    }

    public interface OnClickMeetingListener {
        void onClickMeeting(Meeting meeting);

        void onClickDeleteMeeting(Meeting meeting);
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_meeting_item_iv_delete)
        ImageView delete;
        @BindView(R.id.list_meeting_item_tv_mail)
        TextView mail;
        @BindView(R.id.list_meeting_item_tv_title)
        TextView title;
        @BindView(R.id.list_meeting_item_iv_avatar)
        ImageView avatar;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
