package com.travel.travelskuy.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_ticket")

public class OrderTicketEntity {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "username")
    public String username;
    @ColumnInfo(name = "from")
    public String from;
    @ColumnInfo(name = "to")
    public String to;
    @ColumnInfo(name = "waktu")
    public String waktu;
    @ColumnInfo(name = "passenger")
    public Integer passenger;
    @ColumnInfo(name = "flight_class")
    public String flight_class;
    @ColumnInfo(name = "type_ticket")
    public String type_ticket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public Integer getPassenger() {
        return passenger;
    }

    public void setPassenger(Integer passenger) {
        this.passenger = passenger;
    }

    public String getFlight_class() {
        return flight_class;
    }

    public void setFlight_class(String flight_class) {
        this.flight_class = flight_class;
    }

    public String getType_ticket() {
        return type_ticket;
    }

    public void setType_ticket(String type_ticket) {
        this.type_ticket = type_ticket;
    }
}
