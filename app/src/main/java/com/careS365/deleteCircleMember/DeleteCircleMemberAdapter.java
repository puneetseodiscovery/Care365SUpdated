package com.careS365.deleteCircleMember;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.careS365.R;
import com.careS365.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeleteCircleMemberAdapter extends RecyclerView.Adapter<DeleteCircleMemberAdapter.ViewHolder> {

    Context context;
    ArrayList<BeanDeleteCircleMember> list;
    ArrayList<String> selectedUsers = new ArrayList<>();
    String selectedUsersForDel;

    public DeleteCircleMemberAdapter(Context context, ArrayList<BeanDeleteCircleMember> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_delete_circle_member_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(!list.get(position).getPersonImg().equals(""))
            Picasso.get().load(list.get(position).getPersonImg()).into(holder.ivProfilePic);
        // holder.ivCat.setImageResource(list.get(position).getServiceImage());
        holder.tvName.setText(list.get(position).getPersonName());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selectedUsers.add(list.get(position).getPersonId());
                } else {
                    selectedUsers.remove(list.get(position).getPersonId());
                }
                selectedUsersForDel = selectedUsers.toString().replace("[", "").replace("]", "")
                        .replace(", ", ",");;

                ((DeleteCircleMemberActivity)context).getSelectedMembers(selectedUsersForDel);
            }
        });
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
        @BindView(R.id.checkbox)
        CheckBox checkBox;



        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            init();
        }

        public void init() {
            tvName.setTypeface(Utility.typeFaceForText(context));
        }
    }

}
