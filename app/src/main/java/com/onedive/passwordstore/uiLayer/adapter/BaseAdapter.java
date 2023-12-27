package com.onedive.passwordstore.uiLayer.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * This class is the base class for the recyclerview adapter in this project, created to reduce boilerplate code when initiating the adapter
 * @param <B> viewBinding to use
 */
public abstract class BaseAdapter<B extends ViewBinding > extends RecyclerView.Adapter<BaseAdapter.BaseHolder<B>> {

    private B binding;
    private final Context context;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    public abstract B createBinding(@NonNull ViewGroup parent, int viewType);

    public abstract void bindVh(@NonNull BaseHolder<B> holder, int position);

    @NonNull
    @Override
    public final BaseHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = createBinding(parent, viewType);
        return new BaseHolder<>(binding);
    }

    @Override
    public final void onBindViewHolder(@NonNull BaseHolder<B> holder, int position) {
        bindVh(holder, position);
    }

    public Context getContext() {
        return context;
    }

    public B getBinding() {
        return binding;
    }

    public static class BaseHolder<B extends ViewBinding> extends RecyclerView.ViewHolder {
        public BaseHolder(@NonNull B itemView) {
            super(itemView.getRoot());
        }
    }

}
