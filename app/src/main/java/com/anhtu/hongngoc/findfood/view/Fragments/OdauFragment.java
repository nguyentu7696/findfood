package com.anhtu.hongngoc.findfood.view.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anhtu.hongngoc.findfood.R;
import com.anhtu.hongngoc.findfood.controller.OdauController;
import com.anhtu.hongngoc.findfood.model.QuanAnModel;

import java.security.interfaces.RSAPublicKey;

public class OdauFragment extends Fragment{
    private OdauController odauController;
    private RecyclerView recyclerOdau;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_odau, container, false);

        recyclerOdau = (RecyclerView) view.findViewById(R.id.recyclerOdau);

        odauController = new OdauController(getContext());
        odauController.getDanhSachQuanAnController(recyclerOdau, getContext());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
