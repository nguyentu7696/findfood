package com.anhtu.hongngoc.findfood.controller;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.anhtu.hongngoc.findfood.Adapters.AdapterRecyclerOdau;
import com.anhtu.hongngoc.findfood.R;
import com.anhtu.hongngoc.findfood.controller.interfaces.OdauInterface;
import com.anhtu.hongngoc.findfood.model.QuanAnModel;

import java.util.ArrayList;
import java.util.List;

public class OdauController {

    private Context context;
    private QuanAnModel quanAnModel;
    private AdapterRecyclerOdau adapterRecyclerOdau;

    public OdauController(Context context){
        this.context = context;
        this.quanAnModel = new QuanAnModel();
    }

    public void getDanhSachQuanAnController(RecyclerView recyclerOdau, Context context, final ProgressBar progressBar, Location vitrihientai){

        final List<QuanAnModel> quanAnModelList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        adapterRecyclerOdau = new AdapterRecyclerOdau(quanAnModelList, R.layout.custom_layout_recyclerview_odau);
        recyclerOdau.setAdapter(adapterRecyclerOdau);
        progressBar.setVisibility(View.VISIBLE);
        OdauInterface odauInterface = new OdauInterface() {
            @Override
            public void getDanhSachQuanAnModel(QuanAnModel quanAnModel) {
                quanAnModelList.add(quanAnModel);
                adapterRecyclerOdau.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        };
        quanAnModel.getDanhSachQuanAn(odauInterface, vitrihientai);
    }
}
