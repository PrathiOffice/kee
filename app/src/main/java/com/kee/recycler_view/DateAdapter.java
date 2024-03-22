package com.kee.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    private Context context;
    private List<DateModel> dateList;

    public DateAdapter(Context context, List<DateModel> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DateModel date = dateList.get(position);
        holder.textViewDate.setText(date.getDate());
        holder.textViewDate.setBackgroundResource(date.getBackgroundColor());
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
