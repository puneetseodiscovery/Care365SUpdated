package com.careS365.home.bottomFragments.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.careS365.R;

import java.util.ArrayList;

/**
 * Created by fedor on 21.03.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public interface OnClickInterface {
        void onClick(String item);
    }

    private ArrayList<String> mDataset = new ArrayList<>();
    private static OnClickInterface mOnClickInterface;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public RelativeLayout mContainer;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.list_text);
            mContainer = (RelativeLayout) v.findViewById(R.id.item_container);

        }

        public void update(final String model) {
            mTextView.setText(model);
            mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickInterface != null) {
                        mOnClickInterface.onClick(model);
                    }
                }
            });
        }
    }

    public void setOnClickInterface(OnClickInterface onClickInterface) {
        mOnClickInterface = onClickInterface;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.update(mDataset.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void update(ArrayList<String> list) {
        this.mDataset = list;
        notifyDataSetChanged();

    }
}
