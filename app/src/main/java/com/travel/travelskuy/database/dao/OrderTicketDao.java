package com.travel.travelskuy.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import com.travel.travelskuy.database.entity.OrderTicketEntity;

import java.util.List;

@Dao
public interface OrderTicketDao {

    //Table Transaksi
    @Query("SELECT * FROM tb_ticket WHERE username=:username")
    List<OrderTicketEntity> getorder(String username);

    @Query("INSERT INTO tb_ticket (`from`, `to`,waktu,passenger,flight_class,username, type_ticket) VALUES (:from,:to,:waktu,:passenger,:flight_class,:username,:type_ticket)")
    void insertorder(String from, String  to, String  waktu, Integer passenger, String flight_class, String username,String type_ticket);

    @Delete
    void delete(OrderTicketEntity transaksi);




}
