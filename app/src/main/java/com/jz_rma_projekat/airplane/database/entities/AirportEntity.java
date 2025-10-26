package com.jz_rma_projekat.airplane.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "airports")
public class AirportEntity {

    @PrimaryKey
    @NonNull
    public String iataCode;

    public String name;
    public String country;

    public AirportEntity(@NonNull String iataCode, String name, String country) {
        this.iataCode = iataCode;
        this.name = name;
        this.country = country;
    }
}