package com.example.eventticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private final List<Event> eventList;
    private final Consumer<Event> onEditEvent;
    private final Consumer<Event> onDeleteEvent;

    public EventAdapter(List<Event> eventList, Consumer<Event> onEditEvent, Consumer<Event> onDeleteEvent) {
        this.eventList = eventList;
        this.onEditEvent = onEditEvent;
        this.onDeleteEvent = onDeleteEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.tvName.setText(event.getName());
        holder.tvPlace.setText(event.getPlace());
        holder.tvDate.setText(event.getDate());
        holder.tvPrice.setText(String.valueOf(event.getPrice()));

        if (onEditEvent != null && onDeleteEvent != null) {
            holder.btnEdit.setOnClickListener(v -> onEditEvent.accept(event));
            holder.btnDelete.setOnClickListener(v -> onDeleteEvent.accept(event));
        } else {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPlace, tvDate, tvPrice;
        Button btnEdit, btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

