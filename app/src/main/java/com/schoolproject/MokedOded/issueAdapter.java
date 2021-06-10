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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class issueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Issue> issuesList = new ArrayList<>();
    private DatabaseReference mDatabaseRef;

    public issueAdapter(ArrayList<Issue> issuesList) { // you can pass other parameters in constructor

        this.issuesList = issuesList;
    }

    private class CustomAdapterItemView extends RecyclerView.ViewHolder {
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

        Glide.with(((CustomAdapterItemView) holder).issueImage).load(currentItem.imgURL).into((ImageView) holder.itemView.findViewById(R.id.issueImageView));
        String issue;
        switch (currentItem.issue){

            case 1:
                issue = "פח מלא";
                break;
            case 2:
                issue = "אין פח";
                break;
            case 3:
                issue = "אין שקית בפח";
                break;
            case 4:
                issue = "אין נייר בשירותים";
                break;
            case 5:
                issue = "אין סבון בשירותים";
                break;
            case 6:
                issue = "שירותים מוצפים";
                break;
            case 7:
                issue = "שירותים מלוכלכים";
                break;
            case 8:
                issue = "שירותים סתומים";
                break;
            default:
                issue = "אחר";
        }
        ((CustomAdapterItemView) holder).issueTypeTextView.setText(issue);
        String location;
        switch (currentItem.location){
            case 1:
                location = "מבואה 1 - צפון";
                break;
            case 2:
                location = "מבואה 2 - צפון";
                break;
            case 3:
                location = "מבואה 3 - צפון";
                break;
            case 11:
                location = "מבואה 1 - דרום";
                break;
            case 12:
                location = "מבואה 2 - דרום";
                break;
            case 13:
                location = "מבואה 3 - דרום";
                break;
            case 91:
                location = "חצר צפונית";
                break;
            case 92:
                location = "חצר דרומית";
                break;
            case 93:
                location = "חצר מרכזית";
                break;
            default:
                location = "error 6234";
                break;
        };
        ((CustomAdapterItemView) holder).issueLocationTextView.setText(location);
        ((CustomAdapterItemView) holder).statusButton.setText(currentItem.status);
        if (currentItem.status.equals("לא טופל")){
            ((CustomAdapterItemView) holder).statusButton.setTextColor(Color.parseColor("#F44336"));
        }
        else {
            ((CustomAdapterItemView) holder).statusButton.setTextColor(Color.parseColor("#FF0FDF17"));
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


