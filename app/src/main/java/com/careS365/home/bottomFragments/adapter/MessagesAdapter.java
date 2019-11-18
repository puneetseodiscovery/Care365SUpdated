package com.careS365.home.bottomFragments.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.careS365.R;
import com.careS365.home.bottomFragments.model.BeanMessages;
import com.careS365.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>{

    Context context;
    ArrayList<BeanMessages> list;

    public MessagesAdapter(Context context, ArrayList<BeanMessages> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_messages_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get().load(list.get(position).getPersonImg()).into(holder.ivProfilePic);
        // holder.ivCat.setImageResource(list.get(position).getServiceImage());
        holder.tvName.setText(list.get(position).getPersonName());
        holder.tvTime.setText(list.get(position).getTime());
        holder.tvMessage.setText(list.get(position).getMessage());
        /*holder.llCircleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadFragment(list.get(position).getServiceName(),list.get(position).getId());
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_profile_pic)
        ImageView ivProfilePic;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.tv_time)
        TextView tvTime;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            init();
        }

        public void init() {
            tvName.setTypeface(Utility.typeFaceForText(context));
            tvMessage.setTypeface(Utility.typeFaceForText(context));
            tvTime.setTypeface(Utility.typeFaceForText(context));
        }
    }

}
