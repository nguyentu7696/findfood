package com.anhtu.hongngoc.findfood.controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    public void getDanhSachQuanAnController(RecyclerView recyclerOdau, Context context){

        final List<QuanAnModel> quanAnModelList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        adapterRecyclerOdau = new AdapterRecyclerOdau(quanAnModelList, R.layout.custom_layout_recyclerview_odau);
        recyclerOdau.setAdapter(adapterRecyclerOdau);
        OdauInterface odauInterface = new OdauInterface() {
            @Override
            public void getDanhSachQuanAnModel(QuanAnModel quanAnModel) {
                quanAnModelList.add(quanAnModel);
                adapterRecyclerOdau.notifyDataSetChanged();
            }
        };
        quanAnModel.getDanhSachQuanAn(odauInterface);
    }
}
