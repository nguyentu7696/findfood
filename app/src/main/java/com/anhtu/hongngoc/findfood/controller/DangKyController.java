package com.anhtu.hongngoc.findfood.controller;

import com.anhtu.hongngoc.findfood.model.ThanhVienModel;

public class DangKyController {
    private ThanhVienModel thanhVienModel;

    public DangKyController(){
        thanhVienModel = new ThanhVienModel();
    }

    public void ThemThongTinThanhVienController(ThanhVienModel thanhVienModel,String uid){
        thanhVienModel.ThemThongTinThanhVien(thanhVienModel,uid);
    }
}
