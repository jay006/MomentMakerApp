package com.example.admin.moment_maker;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ListViewHolder> {
    private Context mcontext;
    private List<UserDetails> mUsers;

    public UserAdapter(Context context, List<UserDetails> userDetailsList) {
        mcontext = context;
        mUsers = userDetailsList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v=LayoutInflater.from(mcontext).inflate(R.layout.hug_listview,viewGroup,false);
       return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {

        UserDetails users=mUsers.get(i);
        listViewHolder.userTitle.setText("Title: "+users.getTitle());
        listViewHolder.userDescription.setText(users.getDescription());
        listViewHolder.userDate.setText("Due date: "+users.getDate());
        Picasso.with(mcontext).load(users.getImageUrl()).fit().centerCrop().into(listViewHolder.userPhoto);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView userTitle, userDate, userDescription;
        public Button invite;
        public ImageView userPhoto;

        public ListViewHolder(View itemview) {
            super(itemview);
            userTitle = itemview.findViewById(R.id.fetchTitle);
            userDate = itemview.findViewById(R.id.fetchDate);
            userDescription = itemview.findViewById(R.id.fetchDescription);
            userPhoto = itemview.findViewById(R.id.fetchImage);
        }

    }

}