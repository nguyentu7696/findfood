package com.anhtu.hongngoc.findfood.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThanhVienModel {

    private DatabaseReference dataNodeThanhVien;

    private String hoten;
    private String hinhanh;
    private String mathanhvien;

    public ThanhVienModel(){
        dataNodeThanhVien = FirebaseDatabase.getInstance().getReference().child("thanhviens");
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMathanhvien() {
        return mathanhvien;
    }

    public void setMathanhvien(String mathanhvien) {
        this.mathanhvien = mathanhvien;
    }

    public void ThemThongTinThanhVien(ThanhVienModel thanhVienModel, String uid){
        dataNodeThanhVien.child(uid).setValue(thanhVienModel);
    }
}
