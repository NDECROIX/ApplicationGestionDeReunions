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
import com.decroix.nicolas.mareu.events.DeleteMeetingEvent;
import com.decroix.nicolas.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;

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

        holder.avatar.setColorFilter(randomColor());

        holder.title.setText(meeting.meetingDateToString());
        holder.mail.setText(meeting.getEmail());

        holder.delete.setOnClickListener(v ->
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));
    }

    private static int randomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
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
        @BindView(R.id.list_meeting_item_iv_avatar)
        ImageView avatar;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
