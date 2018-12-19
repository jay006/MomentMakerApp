package com.example.admin.moment_maker;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserDetails> {

    private Activity context;
    private List<UserDetails> userDetailsList;
    public UserAdapter(Activity context,List<UserDetails>userDetailsList) {
        super(context,R.layout.hug_listview,userDetailsList);
        this.context=context;
        this.userDetailsList=userDetailsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listview=inflater.inflate(R.layout.hug_listview,null,true);
        TextView usertitle=(TextView)listview.findViewById(R.id.usertitle);
        TextView userdescription=(TextView)listview.findViewById(R.id.userdescription);
        TextView duedate=(TextView)listview.findViewById(R.id.userduedate);

        UserDetails userDetails=userDetailsList.get(position);
        usertitle.setText("Title: "+userDetails.getTitle());
        userdescription.setText(userDetails.getDescription());
        duedate.setText("Due Date: "+userDetails.getDate());

        return listview;
    }
}
