package com.careS365.home.bottomFragments.adapter;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.careS365.R;
import com.careS365.home.HomeActivity;
import com.careS365.home.bottomFragments.model.BeanCreateCircle;
import com.careS365.home.bottomFragments.presenter.IPCreateCircleAdapter;
import com.careS365.home.bottomFragments.presenter.PCreateCircleAdapter;
import com.careS365.responseModel.CreateCircleResponseModel;
import com.careS365.util.Constants;
import com.careS365.util.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateCircleAdapter extends RecyclerView.Adapter<CreateCircleAdapter.ViewHolder> implements ICreateCircleAdapter {

    Context context;
    ArrayList<BeanCreateCircle> list;
    ProgressDialog progressDialog;
    IPCreateCircleAdapter ipCreateCircleAdapter;

    public CreateCircleAdapter(Context context, ArrayList<BeanCreateCircle> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_create_circle_view, parent, false);
        ipCreateCircleAdapter = new PCreateCircleAdapter(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get().load(list.get(position).getCicleImg()).into(holder.ivCreateCircle);
        // holder.ivCat.setImageResource(list.get(position).getServiceImage());
        holder.tvCreateCircle.setText(list.get(position).getCicleName());
        holder.llCircleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isNetworkConnected(context)) {
                    progressDialog = Utility.showLoader(context);
                    ipCreateCircleAdapter.createCircle(list.get(position).getCicleName(), Utility.getUserId());
                } else {
                    Toast.makeText(context, "Check your internet connection !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void createCircleSuccessFromPresenter(CreateCircleResponseModel createCircleResponseModel) {
        progressDialog.dismiss();
        Constants.SELECTED_CIRCLE = createCircleResponseModel.getData().getId();
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void createCircleFailureFromPresenter(String msgg) {
        progressDialog.dismiss();
        Toast.makeText(context, "" + msgg, Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_create_circle)
        ImageView ivCreateCircle;
        @BindView(R.id.tv_create_circle)
        TextView tvCreateCircle;
        @BindView(R.id.ll_circle_view_item)
        LinearLayout llCircleItem;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            init();
        }

        public void init() {
            tvCreateCircle.setTypeface(Utility.typeFaceForText(context));
        }
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<BeanCreateCircle> filterdNames) {
        list = filterdNames;
        notifyDataSetChanged();
    }
}
