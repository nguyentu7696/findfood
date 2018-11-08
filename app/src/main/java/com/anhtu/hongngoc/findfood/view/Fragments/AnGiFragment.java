package com.anhtu.hongngoc.findfood.view.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anhtu.hongngoc.findfood.R;
import com.anhtu.hongngoc.findfood.model.ThanhVienModel;
import com.anhtu.hongngoc.findfood.view.DangNhapActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnGiFragment extends Fragment  {
    private CircleImageView imgPicture;
    private TextView tvName;
    private Button btnOut;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_angi, container, false);
        btnOut=(Button)view.findViewById(R.id.btnOut);
        imgPicture=(CircleImageView)view.findViewById(R.id.cicleImageUser);
        tvName=(TextView)view.findViewById(R.id.tvNameUser);
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        final String userID=user.getUid();
        if(user.getEmail()!=null && user.getPhotoUrl()!=null){
            tvName.setText(user.getEmail());
            Log.d("d",user.getPhotoUrl()+"");
            Picasso.get().load(user.getPhotoUrl()).into(imgPicture);
        }
        else {
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot dataSnapshotThanhVien = dataSnapshot.child("thanhviens");
                    for (DataSnapshot valThanhVien : dataSnapshotThanhVien.getChildren()) {
                        ThanhVienModel thanhVienModel = valThanhVien.getValue(ThanhVienModel.class);
                        thanhVienModel.setMathanhvien(valThanhVien.getKey());
                        if (userID.equals(thanhVienModel.getMathanhvien())) {
                            setHinhAnhBinhLuan(imgPicture, thanhVienModel.getHinhanh());
                            tvName.setText(thanhVienModel.getHoten());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
            }
        });
        return view;
    }

    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh){
        try {
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    circleImageView.setImageBitmap(bitmap);
                }
            });
        }catch (Exception ex){

        }

    }
}