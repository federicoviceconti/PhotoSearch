package com.example.photosearch.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<S> extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {
    private final List<S> model;

    public BaseAdapter(List<S> model) {
        this.model = model;
    }

    public void refreshList(List<S> model) {
        this.model.clear();
        this.model.addAll(model);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getHolderLayout(), parent, false);
        return onCreateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.BaseHolder holder, int position) {
        S item = model.get(position);
        holder.initializeModel(item);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public abstract class BaseHolder extends RecyclerView.ViewHolder {
        private S model;

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
            initViewReference(itemView);
        }

        final void initializeModel(S model) {
            this.model = model;
            onModelCreated();
        }

        protected S getModel() { return model; }

        public abstract void initViewReference(View itemView);
        public abstract void onModelCreated();
    }

    public abstract int getHolderLayout();
    public abstract BaseHolder onCreateHolder(View itemView);
}