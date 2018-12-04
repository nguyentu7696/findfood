package com.anhtu.hongngoc.findfood.controller;

import android.util.Log;

import com.anhtu.hongngoc.findfood.model.DownloadPolyLine;
import com.anhtu.hongngoc.findfood.model.ParserPolyline;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DanDuongToiQuanAnController {
    ParserPolyline parserPolyline;
    DownloadPolyLine downloadPolyLine;

    public  DanDuongToiQuanAnController(){

    }

    public void HienThiDanDuongToiQuanAn(GoogleMap googleMap, String duongdan){
        parserPolyline = new ParserPolyline();
        downloadPolyLine = new DownloadPolyLine();
        downloadPolyLine.execute(duongdan);

        try {
            String dataJSON = downloadPolyLine.get();
            Log.d("datajson", dataJSON);
            List<LatLng> latLngList = parserPolyline.LayDanhSachToaDo(dataJSON);
            Log.d("datajson", latLngList.toString());

            PolylineOptions polylineOptions = new PolylineOptions();
            for (LatLng toado : latLngList){
                Log.d("kiemtra", String.valueOf(toado.latitude));
                polylineOptions.add(toado);
            }

            Polyline polyline = googleMap.addPolyline(polylineOptions);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
