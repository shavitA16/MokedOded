package com.schoolproject.MokedOded;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class issueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Issue> issuesList = new ArrayList<>();
    private Context context;
    private DatabaseReference mDatabaseRef;

    public issueAdapter(ArrayList<Issue> issuesList, Context context, DatabaseReference mDatabaseRef) { // you can pass other parameters in constructor
        this.context = context;
        this.issuesList = issuesList;
        this.mDatabaseRef = mDatabaseRef;
    }

    class CustomAdapterItemView extends RecyclerView.ViewHolder {
        ImageView issueImage;
        TextView issueTypeTextView;
        TextView issueLocationTextView;
        TextView issueDateTextView;
        TextView issueUserEmailTextView;
        Button statusButton;

        CustomAdapterItemView(final View itemView) {
            super(itemView);
            issueImage = itemView.findViewById(R.id.issueImageView);
            issueTypeTextView = itemView.findViewById(R.id.issueTypeTextView);
            issueLocationTextView = itemView.findViewById(R.id.issueLocationTextView);
            issueDateTextView = itemView.findViewById(R.id.issueDateTextView);
            issueUserEmailTextView = itemView.findViewById(R.id.issueUserEmailTextView);
            statusButton = itemView.findViewById(R.id.statusButton);
            statusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Issue currentItem = issuesList.get(getPosition());
                    if (statusButton.getText().equals("????????")){
                        statusButton.setTextColor(Color.RED);
                        statusButton.setText("???? ????????");
                        currentItem.status = "???? ????????";
                        mDatabaseRef.child(currentItem.path).child("status").setValue("???? ????????");
                    }
                    else if(statusButton.getText().equals("???? ????????")){
                        statusButton.setTextColor(Color.GREEN);
                        statusButton.setText("????????");
                        currentItem.status = "????????";
                        mDatabaseRef.child(currentItem.path).child("status").setValue("????????");
                    }
                }
            });
        }

        void bind(int position) {
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_template, parent, false);
        return new CustomAdapterItemView(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CustomAdapterItemView) holder).bind(position);

        Issue currentItem = issuesList.get(position);

        Glide.with(context)
                .asBitmap()
                .load(currentItem.imgURL)
                .into(((CustomAdapterItemView) holder).issueImage);
        String issue;
        switch (currentItem.issue){

            case 1:
                issue = "???? ??????";
                break;
            case 2:
                issue = "?????? ????";
                break;
            case 3:
                issue = "?????? ???????? ??????";
                break;
            case 4:
                issue = "?????? ???????? ????????????????";
                break;
            case 5:
                issue = "?????? ???????? ????????????????";
                break;
            case 6:
                issue = "?????????????? ????????????";
                break;
            case 7:
                issue = "?????????????? ????????????????";
                break;
            case 8:
                issue = "?????????????? ????????????";
                break;
            default:
                issue = "??????";
        }
        ((CustomAdapterItemView) holder).issueTypeTextView.setText(issue);
        String location;
        switch (currentItem.location){
            case 1:
                location = "?????????? 1 - ????????";
                break;
            case 2:
                location = "?????????? 2 - ????????";
                break;
            case 3:
                location = "?????????? 3 - ????????";
                break;
            case 11:
                location = "?????????? 1 - ????????";
                break;
            case 12:
                location = "?????????? 2 - ????????";
                break;
            case 13:
                location = "?????????? 3 - ????????";
                break;
            case 91:
                location = "?????? ????????????";
                break;
            case 92:
                location = "?????? ????????????";
                break;
            case 93:
                location = "?????? ????????????";
                break;
            default:
                location = "error 6234";
                break;
        };
        ((CustomAdapterItemView) holder).issueLocationTextView.setText(location);
        ((CustomAdapterItemView) holder).statusButton.setText(currentItem.status);
        if (currentItem.status.equals("???? ????????")){
            ((CustomAdapterItemView) holder).statusButton.setTextColor(Color.RED);
        }
        else {
            ((CustomAdapterItemView) holder).statusButton.setTextColor(Color.GREEN);
        }

        ((CustomAdapterItemView) holder).issueDateTextView.setText(currentItem.date);
        ((CustomAdapterItemView) holder).issueUserEmailTextView.setText(currentItem.userEmail);
    }

    @Override
    public int getItemCount() {
        return issuesList.size();
    }

    // Add your other methods here
}



