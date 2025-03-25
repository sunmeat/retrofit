package com.sunmeat.retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {

    private final List<JokeModel> jokes;

    JokeAdapter(List<JokeModel> jokes) {
        this.jokes = jokes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JokeModel model = jokes.get(position);
        var content = model.getContent();
        content = content.replaceAll("! -", "\n -");
        holder.joke.setText(content);
    }

    @Override
    public int getItemCount() {
        if (jokes == null)
            return 0;
        return jokes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView joke;

        ViewHolder(View itemView) {
            super(itemView);
            joke = itemView.findViewById(R.id.post);
        }
    }
}