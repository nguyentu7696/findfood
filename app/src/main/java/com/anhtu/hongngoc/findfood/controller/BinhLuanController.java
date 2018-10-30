package com.anhtu.hongngoc.findfood.controller;

import com.anhtu.hongngoc.findfood.model.BinhLuanModel;

import java.util.List;

public class BinhLuanController {

    BinhLuanModel binhLuanModel;

    public  BinhLuanController(){
        binhLuanModel = new BinhLuanModel();
    }

    public void ThemBinhLuan(String maQuanAn, BinhLuanModel binhLuanModel, List<String> listHinh){
        binhLuanModel.ThemBinhLuan(maQuanAn,binhLuanModel,listHinh);
    }
}
