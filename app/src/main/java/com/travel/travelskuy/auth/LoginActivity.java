package com.travel.travelskuy.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.travel.travelskuy.MainActivity;
import com.travel.travelskuy.R;
import com.travel.travelskuy.database.AppDatabase;
import com.travel.travelskuy.database.dao.UsersDao;
import com.travel.travelskuy.database.entity.UserEntity;
import com.travel.travelskuy.databinding.ActivityLoginBinding;
import com.travel.travelskuy.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AppDatabase database;
    private List<UserEntity> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.getLifecycleOwner();

        database = AppDatabase.getInstance(this);


        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edtusername.getText().toString().trim();
                String password = binding.edtpassword.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()){
                    final UsersDao userDao = database.userDao();
                    UserEntity userEntity =  userDao.login(username,password);
                    if (userEntity==null){
                        Snackbar.make(v, "Login Gagal ", Snackbar.LENGTH_LONG).show();
                    }else {
                        SessionManager.setIsLogin(getBaseContext(),true);
                        SessionManager.setIsEmail(getBaseContext(),userEntity.getEmail());
                        SessionManager.setIsPassword(getBaseContext(),userEntity.getPasssword());
                        SessionManager.setIsFoto(getBaseContext(),userEntity.getFoto());
                        SessionManager.setIsUsername(getBaseContext(),userEntity.getUsername());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }else {
                    Snackbar.make(v, "Isi semua kolom ", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        binding.btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (SessionManager.getIsLogin(getBaseContext())){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}