package com.anhtu.hongngoc.findfood.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.anhtu.hongngoc.findfood.R;
import com.anhtu.hongngoc.findfood.model.BinhLuanModel;

import java.util.List;

public class AdapterRecyclerHinhBinhLuan extends RecyclerView.Adapter<AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan> {

    private Context context;
    private int resource;
    private List<Bitmap> listHinh;
    private BinhLuanModel binhLuanModel;
    private boolean isChiTietBinhLuan;

    public AdapterRecyclerHinhBinhLuan(Context context, int resource, List<Bitmap> listHinh, BinhLuanModel binhLuanModel, boolean isChiTietBinhLuan){
        this.context = context;
        this.resource = resource;
        this.binhLuanModel = binhLuanModel;
        this.listHinh = listHinh;
        this.isChiTietBinhLuan = isChiTietBinhLuan;

    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imageHinhBinhLuan;
        TextView txtSoHinhBinhLuan;
        FrameLayout khungSoHinhBinhLuan;

        public ViewHolderHinhBinhLuan(View itemView) {
            super(itemView);

            imageHinhBinhLuan = (ImageView) itemView.findViewById(R.id.imageBinhLuan);
            txtSoHinhBinhLuan = (TextView) itemView.findViewById(R.id.txtSoHinhBinhLuan);
            khungSoHinhBinhLuan = (FrameLayout) itemView.findViewById(R.id.khungSoHinhBinhLuan);
        }
    }

    @Override
    public ViewHolderHinhBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);
        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(ViewHolderHinhBinhLuan holder, int position) {
        holder.imageHinhBinhLuan.setImageBitmap(listHinh.get(position));

        if(!isChiTietBinhLuan){
            if(position == 3){

                int sohinhconlai = listHinh.size()-4;
                if(sohinhconlai > 0){
                    holder.khungSoHinhBinhLuan.setVisibility(View.VISIBLE);
                    holder.txtSoHinhBinhLuan.setText("+" + sohinhconlai);
                    holder.imageHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent iChiTietBinhLuan = new Intent(context, HienThiChiTietBinhLuanActivity.class);
//                            iChiTietBinhLuan.putExtra("binhluanmodel",binhLuanModel);
//                            context.startActivity(iChiTietBinhLuan);
                        }
                    });

                }

            }
        }
    }

    @Override
    public int getItemCount() {
        if(!isChiTietBinhLuan){
            if(listHinh.size() < 4){
                return listHinh.size();
            }else{
                return 4;
            }

        }else{
            return listHinh.size();
        }
    }


}
