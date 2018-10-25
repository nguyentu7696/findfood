package com.anhtu.hongngoc.findfood.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
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
import com.anhtu.hongngoc.findfood.controller.ChiTietQuanController;
import com.anhtu.hongngoc.findfood.model.QuanAnModel;
import com.anhtu.hongngoc.findfood.model.TienIchModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private TextView txtTenQuanAn, txtDiaChi, txtThoiGianHoatDong, txtTrangThaiHoatDong, txtTongSoHinhAnh, txtTongSoBinhLuan, txtTongSoCheckIn, txtTongSoLuuLai, txtTieuDeToolbar, txtGioiHanGia, txtTenWifi, txtMatKhauWifi, txtNgayDangWifi;
    private ImageView imHinhAnhQuanAn, imgPlayTrailer;
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
    private GoogleMap googleMap;
    private MapFragment mapFragment;

    private ChiTietQuanController chiTietQuanController;


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
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        txtGioiHanGia = (TextView) findViewById(R.id.txtGioiHanGia);
        khungTienIch = (LinearLayout) findViewById(R.id.khungTienTich);
        txtTenWifi = (TextView) findViewById(R.id.txtTenWifi);
        txtMatKhauWifi = (TextView) findViewById(R.id.txtMatKhauWifi);
        khungWifi = (LinearLayout) findViewById(R.id.khungWifi);
        txtNgayDangWifi = (TextView) findViewById(R.id.txtNgayDangWifi);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chiTietQuanController = new ChiTietQuanController();

        mapFragment.getMapAsync(ChiTietQuanAnActivity.this);

        hienThiChiTietQuanAn();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void hienThiChiTietQuanAn() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = quanAnModel.getGiomocua();
        String giodongcua = quanAnModel.getGiodongcua();

        try {
            Date dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giomocua);
            Date dateDongCua = dateFormat.parse(giodongcua);

            if (dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)) {
                //gio mo cua
                txtTrangThaiHoatDong.setText(getString(R.string.dangmocua));
            } else {
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

        downLoadHinhTienIch();

        if (quanAnModel.getGiatoida() != 0 && quanAnModel.getGiatoithieu() != 0) {
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String giatoithieu = numberFormat.format(quanAnModel.getGiatoithieu()) + " đ";
            String giatoida = numberFormat.format(quanAnModel.getGiatoida()) + " đ";
            txtGioiHanGia.setText(giatoithieu + " - " + giatoida);
        } else {
            txtGioiHanGia.setVisibility(View.INVISIBLE);
        }

        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhquanan").child(quanAnModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imHinhAnhQuanAn.setImageBitmap(bitmap);
            }
        });

        //Load danh sach binh luan cua quan
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBinhLuan.setLayoutManager(layoutManager);
        adapterBinhLuan = new ApdaterBinhLuan(this, R.layout.custom_layout_binhluan, quanAnModel.getBinhLuanModelList());
        recyclerViewBinhLuan.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();

        NestedScrollView nestedScrollViewChiTiet = (NestedScrollView) findViewById(R.id.nestScrollViewChiTiet);
        nestedScrollViewChiTiet.smoothScrollTo(0,0);

        chiTietQuanController.HienThiDanhSachWifiQuanAn(quanAnModel.getMaquanan(), txtTenWifi, txtMatKhauWifi, txtNgayDangWifi);
        khungWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDanhSachWifi = new Intent(ChiTietQuanAnActivity.this,CapNhatDanhSachWifiActivity.class);
                iDanhSachWifi.putExtra("maquanan",quanAnModel.getMaquanan());
                startActivity(iDanhSachWifi);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        this.googleMap = googleMap;
//
//        double latitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude();
//        double longitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude();
//
//        LatLng latLng = new LatLng(latitude,longitude);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title(quanAnModel.getTenquanan());
//
//        googleMap.addMarker(markerOptions);
//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,14);
//        googleMap.moveCamera(cameraUpdate);
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng XXX = new LatLng(21.014557, 105.804000);
        googleMap.addMarker(new MarkerOptions().position(XXX).title("XXX"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(XXX, 18));
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

    }

    private void downLoadHinhTienIch(){

        for (String matienich : quanAnModel.getTienich()){
            DatabaseReference nodeTienIch = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
            nodeTienIch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);

                    StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            ImageView imageTienIch = new ImageView(ChiTietQuanAnActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100,100);
                            layoutParams.setMargins(10,10,10,10);
                            imageTienIch.setLayoutParams(layoutParams);
                            imageTienIch.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageTienIch.setPadding(5,5,5,5);


                            imageTienIch.setImageBitmap(bitmap);
                            khungTienIch.addView(imageTienIch);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
}
