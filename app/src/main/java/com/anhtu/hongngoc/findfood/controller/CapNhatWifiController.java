package com.anhtu.hongngoc.findfood.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.anhtu.hongngoc.findfood.Adapters.AdapterDanhSachWifi;
import com.anhtu.hongngoc.findfood.R;
import com.anhtu.hongngoc.findfood.controller.interfaces.ChiTietQuanAnInterface;
import com.anhtu.hongngoc.findfood.model.WifiQuanAnModel;
import com.anhtu.hongngoc.findfood.view.CapNhatDanhSachWifiActivity;

import java.util.ArrayList;
import java.util.List;

public class CapNhatWifiController {
    private WifiQuanAnModel wifiQuanAnModel;
    private Context context;
    private List<WifiQuanAnModel> wifiQuanAnModelList;

    public CapNhatWifiController(Context context){
        wifiQuanAnModel = new WifiQuanAnModel();
        this.context = context;
    }

    public void HienThiDanhSachWifi(String maquanan, final RecyclerView recyclerView){

        wifiQuanAnModelList = new ArrayList<>();
        ChiTietQuanAnInterface chiTietQuanAnInterface = new ChiTietQuanAnInterface() {
            @Override
            public void HienThiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                AdapterDanhSachWifi adapterDanhSachWifi = new AdapterDanhSachWifi(context, R.layout.layout_wifi_chitietquanan,wifiQuanAnModelList);
                recyclerView.setAdapter(adapterDanhSachWifi);
                adapterDanhSachWifi.notifyDataSetChanged();
            }
        };

        wifiQuanAnModel.LayDanhSachWifiQuanAn(maquanan,chiTietQuanAnInterface);
    }

    public void ThemWifi(Context context, WifiQuanAnModel wifiQuanAnModel, String maquanan){
        wifiQuanAnModel.ThemWifiQuanAn(context,wifiQuanAnModel,maquanan);
    }
}
