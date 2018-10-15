package com.anhtu.hongngoc.findfood.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.anhtu.hongngoc.findfood.controller.interfaces.OdauInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanAnModel {
    private boolean giaohang;
    private String giodongcua,giomocua,tenquanan,videogioithieu,maquanan;
    private List<String> tienich;
    private List<String> hinhanhquanan;
    private List<BinhLuanModel> binhLuanModelList;


    private long giatoida;
    private long giatoithieu;
    private long luotthich;

    private DatabaseReference nodeRoot;
    private DataSnapshot dataRoot;

    public QuanAnModel(){
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    public void getDanhSachQuanAn(final OdauInterface odauInterface){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");

                //lấy danh sách quán ăn
                for(DataSnapshot valueQuanAn: dataSnapshotQuanAn.getChildren()){
                    QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
                    quanAnModel.setMaquanan(valueQuanAn.getKey());

                    //Lấy danh sách hình ảnh của quán ăn theo mã
                    DataSnapshot dataSnapshotHinhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());

                    List<String> hinhanhlist = new ArrayList<>();

                    for (DataSnapshot valueHinhQuanAn : dataSnapshotHinhQuanAn.getChildren()){
                        hinhanhlist.add(valueHinhQuanAn.getValue(String.class));
                    }
                    quanAnModel.setHinhanhquanan(hinhanhlist);


                    //Lấy danh sách bình luân của quán ăn
                    DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(quanAnModel.getMaquanan());
                    List<BinhLuanModel> binhLuanModels = new ArrayList<>();

                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()){
                        BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                        binhLuanModel.setManbinhluan(valueBinhLuan.getKey());
                        ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                        binhLuanModel.setThanhVienModel(thanhVienModel);

                        List<String> hinhanhBinhLuanList = new ArrayList<>();
                        DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                        for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()){
                            hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                        }
                        binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);

                        binhLuanModels.add(binhLuanModel);
                    }
                    quanAnModel.setBinhLuanModelList(binhLuanModels);


                    odauInterface.getDanhSachQuanAnModel(quanAnModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        nodeRoot.addValueEventListener(valueEventListener);
    }
}
