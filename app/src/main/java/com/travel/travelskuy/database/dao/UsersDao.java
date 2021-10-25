package com.travel.travelskuy.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.travel.travelskuy.database.entity.UserEntity;


@Dao
public interface UsersDao {

    @Query("SELECT * FROM users where username=(:username)")
    UserEntity cekuser(String username);

    @Query("INSERT INTO users (username,password) VALUES (:username,:password)")
    void insertAuth(String username, String password);

    @Query("UPDATE users SET  username=:username,email=:email,password=:password,foto=:foto WHERE username=:username")
    void updateauth(String username,String email, String password, String foto);

    @Query("UPDATE users SET  email=:email,password=:password,foto=:foto WHERE username=:username")
    void updateauthnouser(String username,String email, String password, String foto);


    @Query("SELECT * FROM users where username=(:username) and password=(:password)")
    UserEntity login(String username, String password);
}
