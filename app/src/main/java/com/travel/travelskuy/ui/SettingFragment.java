package com.travel.travelskuy.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.travel.travelskuy.R;
import com.travel.travelskuy.auth.LoginActivity;
import com.travel.travelskuy.database.AppDatabase;
import com.travel.travelskuy.database.entity.UserEntity;
import com.travel.travelskuy.databinding.FragmentSettingBinding;
import com.travel.travelskuy.session.SessionManager;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;


public class SettingFragment extends Fragment {
    private static final int IMAGE_CODE_CAPTURE = 10;
    String pathToFile;
    private static final int PERMISSION_CAMERA = 1;
    Uri imageuri;
    FragmentSettingBinding binding;
    private AppDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_setting,container,false);
        binding.getLifecycleOwner();
        database = AppDatabase.getInstance(requireContext());

        //Requesting storage permission
        if (Build.VERSION.SDK_INT > 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CAMERA);
        }


        binding.imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });


        binding.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.setIsLogin(requireContext(),false);
                SessionManager.setIsEmail(requireContext(),"");
                SessionManager.setIsPassword(requireContext(),"");
                SessionManager.setIsFoto(requireContext(),"");
                SessionManager.setIsUsername(requireContext(),"");
                Intent intent = new Intent(requireContext().getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();

            }
        });

        binding.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edtusername.getText().toString().trim();
                String email = binding.edtemail.getText().toString().trim();
                String password = binding.edtpassword.getText().toString().trim();
                String cfrpassword = binding.edtcfrpassword.getText().toString().trim();

                if (username.isEmpty() && password.isEmpty() && cfrpassword.isEmpty()){
                    Snackbar.make(v, "Isi semua kolom ", Snackbar.LENGTH_LONG).show();
                }else {
                    if (password.equals(cfrpassword)){
                        UserEntity cekuser = database.userDao().cekuser(username);
                        if (cekuser == null){
                            SessionManager.setIsEmail(requireContext(),email);
                            SessionManager.setIsPassword(requireContext(),password);
                            SessionManager.setIsFoto(requireContext(),pathToFile);
                            SessionManager.setIsUsername(requireContext(),username);
                            database.userDao().updateauth(username,email,password,pathToFile);
                            Snackbar.make(v, "Update berhasil ", Snackbar.LENGTH_LONG).show();
                        }else {
                            SessionManager.setIsEmail(requireContext(),email);
                            SessionManager.setIsPassword(requireContext(),password);
                            SessionManager.setIsFoto(requireContext(),pathToFile);
                            SessionManager.setIsUsername(requireContext(),username);
                            database.userDao().updateauthnouser(username,email,password,pathToFile);
                            Snackbar.make(v, "Update berhasil ", Snackbar.LENGTH_LONG).show();
                        }

                    }else {
                        Snackbar.make(v, "Password harus sama ", Snackbar.LENGTH_LONG).show();

                    }

                }

            }
        });
        return binding.getRoot();
    }

    private void openCamera() {

        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photofile = null;
        photofile = createFotoFile();

        if (photofile != null) {
            pathToFile = photofile.getAbsolutePath();
            Uri photoUri = FileProvider.getUriForFile(getActivity(), "com.travel.fileprovider", photofile);
            takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePic, 1);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                        || getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permission, PERMISSION_CAMERA);
                } else {
                    //permisi granted
                    bukakamera();
                }
            }else {
                //system < marshamloow
            }
        }

    }

    private void bukakamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(cameraIntent, IMAGE_CODE_CAPTURE);


    }

    private void opengallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),87);
    }

    @SuppressLint("QueryPermissionsNeeded")
    private File createFotoFile() {

        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("alfan", "Excep :" + e.toString());
        }

        return image;

    }
    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);

        MaterialButton camera = bottomSheetDialog.findViewById(R.id.btncamera);
        MaterialButton gallery = bottomSheetDialog.findViewById(R.id.btngalery);
        MaterialButton close = bottomSheetDialog.findViewById(R.id.btnclose);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            binding.imgfoto.setImageBitmap(photo);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(requireContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));
            pathToFile = finalFile.getPath();


        }

        if (requestCode == 87) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                        Uri tempUri = getImageUri(requireContext(), bitmap);

                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        File finalFile = new File(getRealPathFromURI(tempUri));
                        pathToFile = finalFile.getPath();

                        binding.imgfoto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, name, null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CAMERA: {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(requireContext(), "permisi  diijinkan", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(requireContext(), "permisi tidak diijinkan", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        String namauser = SessionManager.getIsUsername(requireContext());
        String email = SessionManager.getIsEmail(requireContext());
        String password = SessionManager.getIsPassword(requireContext());
        String foto = SessionManager.getIsFoto(requireContext());

        File imgFile = new  File(foto);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            binding.imgfoto.setImageBitmap(myBitmap);
        }

        binding.txtnamauser.setText(namauser);
        binding.edtusername.setText(namauser);
        binding.edtemail.setText(email);
        binding.edtpassword.setText(password);
        binding.edtcfrpassword.setText(password);

    }
}