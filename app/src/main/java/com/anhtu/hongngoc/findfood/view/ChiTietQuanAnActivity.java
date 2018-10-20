package com.anhtu.hongngoc.findfood.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.VideoView;

import com.anhtu.hongngoc.findfood.Adapters.ApdaterBinhLuan;
import com.anhtu.hongngoc.findfood.R;
import com.anhtu.hongngoc.findfood.model.QuanAnModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChiTietQuanAnActivity extends AppCompatActivity {
    private TextView txtTenQuanAn,txtDiaChi,txtThoiGianHoatDong,txtTrangThaiHoatDong,txtTongSoHinhAnh,txtTongSoBinhLuan,txtTongSoCheckIn,txtTongSoLuuLai,txtTieuDeToolbar,txtGioiHanGia,txtTenWifi,txtMatKhauWifi,txtNgayDangWifi;
    private ImageView imHinhAnhQuanAn,imgPlayTrailer;
    private Button btnBinhLuan;
    private LinearLayout khungWifi;
    private QuanAnModel quanAnModel;
    private Toolbar toolbar;
    private RecyclerView recyclerViewBinhLuan;
    private LinearLayout khungTienIch;
    private View khungTinhNang;
    private VideoView videoView;
    private RecyclerView recyclerThucDon;
    private ApdaterBinhLuan adapterBinhLuan;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);

        quanAnModel = getIntent().getParcelableExtra("quanan");
        Log.d("kiemtra", quanAnModel.getTenquanan());

        txtTenQuanAn = (TextView) findViewById(R.id.txtTenQuanAn);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChiQuanAn);
        txtThoiGianHoatDong = (TextView) findViewById(R.id.txtThoiGianHoatDong);
        txtTrangThaiHoatDong = (TextView) findViewById(R.id.txtTrangThaiHoatDong);
        txtTongSoBinhLuan = (TextView) findViewById(R.id.tongSoBinhLuan);
        txtTongSoCheckIn = (TextView) findViewById(R.id.tongSoCheckIn);
        txtTongSoHinhAnh = (TextView) findViewById(R.id.tongSoHinhAnh);
        txtTongSoLuuLai = (TextView) findViewById(R.id.tongSoLuuLai);
        imHinhAnhQuanAn = (ImageView) findViewById(R.id.imHinhQuanAn);
        txtTieuDeToolbar = (TextView) findViewById(R.id.txtTieuDeToolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnBinhLuan = (Button) findViewById(R.id.btnBinhLuan);
        videoView = (VideoView) findViewById(R.id.videoTrailer);
        recyclerViewBinhLuan = (RecyclerView) findViewById(R.id.recyclerBinhLuanChiTietQuanAn);

        hienThiChiTietQuanAn();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void hienThiChiTietQuanAn(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = quanAnModel.getGiomocua();
        String giodongcua = quanAnModel.getGiodongcua();

        try {
            Date dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giomocua);
            Date dateDongCua = dateFormat.parse(giodongcua);

            if(dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)){
                //gio mo cua
                txtTrangThaiHoatDong.setText(getString(R.string.dangmocua));
            }else{
                //dong cua
                txtTrangThaiHoatDong.setText(getString(R.string.dadongcua));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        txtTieuDeToolbar.setText(quanAnModel.getTenquanan());

        txtTenQuanAn.setText(quanAnModel.getTenquanan());
        txtDiaChi.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txtThoiGianHoatDong.setText(quanAnModel.getGiomocua() + " - " + quanAnModel.getGiodongcua());
        txtTongSoHinhAnh.setText(quanAnModel.getHinhanhquanan().size() + "");
        txtTongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
        txtThoiGianHoatDong.setText(giomocua + " - " + giodongcua);

        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhquanan").child(quanAnModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imHinhAnhQuanAn.setImageBitmap(bitmap);
            }
        });

        //Load danh sach binh luan cua quan
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBinhLuan.setLayoutManager(layoutManager);
        adapterBinhLuan = new ApdaterBinhLuan(this,R.layout.custom_layout_binhluan,quanAnModel.getBinhLuanModelList());
        recyclerViewBinhLuan.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
