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

import com.bumptech.glide.Glide;
import com.careS365.R;
import com.careS365.responseModel.GetCircleMembersResponseModel;
import com.careS365.util.Constants;
import com.careS365.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DeleteCircleMemberAdapter extends RecyclerView.Adapter<DeleteCircleMemberAdapter.ViewHolder> {

    Context context;
    ArrayList<BeanDeleteCircleMember> list;
    ArrayList<String> selectedUsers = new ArrayList<>();
    String selectedUsersForDel;
    List<GetCircleMembersResponseModel.Datum> data;

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

        String imageURL=list.get(position).getPersonImg();

        if (list.get(position).getPersonImg()==null && list.get(position).getPersonImg().isEmpty() ){
            Glide.with(context).load(imageURL).error(R.drawable.img_placeholder).into(holder.ivProfilePic);
        }else {
            Glide.with(context).load(imageURL).error(R.drawable.img_placeholder).into(holder.ivProfilePic);
        }

        holder.tvName.setText(list.get(position).getPersonName());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedUsers.add(list.get(position).getPersonId());
                } else {
                    selectedUsers.remove(list.get(position).getPersonId());
                }
                selectedUsersForDel = selectedUsers.toString().replace("[", "").replace("]", "")
                        .replace(", ", ",");
                ((DeleteCircleMemberActivity) context).getSelectedMembers(selectedUsersForDel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_profile_pic)
        CircleImageView ivProfilePic;

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
