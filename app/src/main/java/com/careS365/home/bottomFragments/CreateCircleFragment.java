package com.careS365.home.bottomFragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.careS365.R;
import com.careS365.home.bottomFragments.adapter.CreateCircleAdapter;
import com.careS365.home.bottomFragments.model.BeanCreateCircle;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCircleFragment extends Fragment {


    @BindView(R.id.rv_circle)
    RecyclerView rvCircle;
    ArrayList<BeanCreateCircle> circleArrayList ;
    @BindView(R.id.et_search)
    EditText etSearch;
    ArrayList<BeanCreateCircle> filterdCircles;
    CreateCircleAdapter adapter;

    Context context;

    public CreateCircleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_circle, container, false);
        ButterKnife.bind(this, view);
        context = this.getActivity();
        init();


        return view;
}

    private void init() {
        circleArrayList=new ArrayList<>();

        circleArrayList.add(new BeanCreateCircle(R.mipmap.plus_icon,"Family"));
        circleArrayList.add(new BeanCreateCircle(R.mipmap.plus_icon,"Friends"));
        circleArrayList.add(new BeanCreateCircle(R.mipmap.plus_icon,"Extended family"));
        circleArrayList.add(new BeanCreateCircle(R.mipmap.plus_icon,"Vacation Group"));
        circleArrayList.add(new BeanCreateCircle(R.mipmap.plus_icon,"Special Someones"));
        circleArrayList.add(new BeanCreateCircle(R.mipmap.plus_icon,"Siblings"));

        //rvCircle.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        rvCircle.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CreateCircleAdapter(context, circleArrayList);
        //rvCircle.setNestedScrollingEnabled(false);
        rvCircle.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });


    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        //ArrayList<BeanCreateCircle> filterdNames = new ArrayList<>();
        filterdCircles = new ArrayList<>();

        //looping through existing elements
        for (int i = 0;i < circleArrayList.size();i++) {
            //if the existing elements contains the search input
            if (circleArrayList.get(i).getCicleName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdCircles.add(circleArrayList.get(i));
            }
            /*else {
                filterdNames.add(new BeanCreateCircle(R.mipmap.plus_icon,text));
            }*/
        }

        if(filterdCircles.size() == 0){
            filterdCircles.add(new BeanCreateCircle(R.mipmap.plus_icon,text));
        }

      //  Toast.makeText(context, "size:" +filterdCircles.size(), Toast.LENGTH_SHORT).show();
        /*for(int i = 0;i < filterdNames.size();i++ ){
            if(filterdNames.get(i).getCicleName().toLowerCase().contains(text.toLowerCase())){

            } else {
                filterdNames.add(new BeanCreateCircle(R.mipmap.plus_icon,text));
            }
        }*/

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdCircles);
    }


}
