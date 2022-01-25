package com.rxone.nimai;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    static int id=0;
    Context context;
    List<Reminder> reminderList;
    private Reminder[] localDataSet;



    RecyclerView rvPrograms;
    final View.OnClickListener onClickListener = new MyOnClickListner();


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private  TextView textView;
        private  TextView textView2;
        private  TextView textView3;
        private ImageView imageView;



        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.textView15);
            textView2 = (TextView) view.findViewById(R.id.textView16);
            textView3 = (TextView) view.findViewById(R.id.textView17);
            imageView = (ImageView) view.findViewById(R.id.imageView2);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
//     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(Context context,List<Reminder> reminderlist,RecyclerView rvPrograms) {
        this.context=context;
        this.reminderList=reminderlist;
        this.rvPrograms=rvPrograms;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context)
                .inflate(R.layout.reminder_item, viewGroup, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder viewHolder,int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Reminder reminder = reminderList.get(position);
        if(reminder.getImgpath()!=null)
        {
            Bitmap bmp = BitmapFactory.decodeByteArray(reminder.getImgpath(), 0, reminder.getImgpath().length);
            viewHolder.imageView.setImageBitmap(bmp);

        }

        viewHolder.textView.setText(reminder.getMed());
        viewHolder.textView2.setText(reminder.getDoc());
        viewHolder.textView3.setText("Scheduled on: "+reminder.getStart_date());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    private class MyOnClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = rvPrograms.getChildLayoutPosition(v);
            String item = reminderList.get(itemPosition).getDoc();

            int remid = reminderList.get(itemPosition).getRem_id();


            Intent intention = new Intent(context, EditDetails.class);
            intention.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intention.putExtra("remid",remid);
            id=remid;
            Toast.makeText(context, String.valueOf(remid), Toast.LENGTH_SHORT).show();
            context.startActivity(intention);






        }
    }
}

