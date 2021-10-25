package com.travel.travelskuy.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.travel.travelskuy.R;
import com.travel.travelskuy.database.AppDatabase;
import com.travel.travelskuy.database.entity.UserEntity;
import com.travel.travelskuy.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.getLifecycleOwner();
        database = AppDatabase.getInstance(this);


        binding.btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edtusername.getText().toString().trim();
                String password = binding.edtpassword.getText().toString().trim();
                String cfrpassowrd = binding.edtcfrpassword.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty() && !cfrpassowrd.isEmpty()) {
                    if (password.equals(cfrpassowrd)){
                        UserEntity userEntity = database.userDao().cekuser(username);
                        if (userEntity==null){
                            database.userDao().insertAuth(username,password);
                            finish();
                        }else {
                            Snackbar.make(v, "email sudah terdaftar", Snackbar.LENGTH_LONG).show();
                        }


                    }else {
                        Snackbar.make(v, "Password tidak sama", Snackbar.LENGTH_LONG).show();

                    }
                } else {
                    Snackbar.make(v, "Isi semua kolom ", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
}