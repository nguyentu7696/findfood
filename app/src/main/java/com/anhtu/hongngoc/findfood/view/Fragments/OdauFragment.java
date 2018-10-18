package com.anhtu.hongngoc.findfood.view.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.anhtu.hongngoc.findfood.R;
import com.anhtu.hongngoc.findfood.controller.OdauController;
import com.anhtu.hongngoc.findfood.model.QuanAnModel;

import java.security.interfaces.RSAPublicKey;

public class OdauFragment extends Fragment{
    private OdauController odauController;
    private RecyclerView recyclerOdau;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private NestedScrollView nestedScrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau, container, false);

        recyclerOdau = (RecyclerView) view.findViewById(R.id.recyclerOdau);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarODau);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestScrollViewODau);

        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));

        odauController = new OdauController(getContext());
        odauController.getDanhSachQuanAnController(nestedScrollView,recyclerOdau, getContext(), progressBar, vitrihientai);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
