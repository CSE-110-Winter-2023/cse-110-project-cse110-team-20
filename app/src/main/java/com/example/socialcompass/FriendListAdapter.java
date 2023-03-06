package com.example.socialcompass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private List<Person> friends = Collections.emptyList();
    private Consumer<Person> onPersonDeleteClicked;

    public void setOnPersonDeleteClickListener(Consumer<Person> onPersonDeleteClicked) {
        this.onPersonDeleteClicked = onPersonDeleteClicked;
    }

    /**
     * This time around, the ViewHolder is much simpler, just data.
     * This is closer to "modern" Kotlin Android conventions.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View itemView;
        public final TextView nameView;
        public final View deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            // Populate the text views...
            this.nameView = itemView.findViewById(R.id.note_item_title);
            this.deleteButton = itemView.findViewById(R.id.note_item_delete);
        }

        public void bind(Person person) {
            nameView.setText(person.name);
            deleteButton.setOnClickListener(v -> onPersonDeleteClicked.accept(person));
        }
    }

    public void setFriends(List<Person> people) {
        this.friends = people;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        var view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        var note = friends.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    @Override
    public long getItemId(int position) {
        return friends.get(position).uid.hashCode();
    }
}
