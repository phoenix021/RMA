package com.jz_rma_projekat.airplane.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "airports")
public class AirportEntity implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    private String gmt;
    private int airportId;
    private String iataCode;
    private String cityIataCode;
    private String icaoCode;
    private String countryIso2;
    private long geonameId;
    private double latitude;
    private double longitude;
    private String name;
    private String country;
    private String phoneNumber;
    private String timezone;

    // Getters and Setters
    public String getId() { return id; }
    public String getGmt() { return gmt; }
    public int getAirportId() { return airportId; }
    public String getIataCode() { return iataCode; }
    public String getCityIataCode() { return cityIataCode; }
    public String getIcaoCode() { return icaoCode; }
    public String getCountryIso2() { return countryIso2; }
    public long getGeonameId() { return geonameId; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getTimezone() { return timezone; }

    public void setId(String id) { this.id = id; }
    public void setGmt(String gmt) { this.gmt = gmt; }
    public void setAirportId(int airportId) { this.airportId = airportId; }
    public void setIataCode(String iataCode) { this.iataCode = iataCode; }
    public void setCityIataCode(String cityIataCode) { this.cityIataCode = cityIataCode; }
    public void setIcaoCode(String icaoCode) { this.icaoCode = icaoCode; }
    public void setCountryIso2(String countryIso2) { this.countryIso2 = countryIso2; }
    public void setGeonameId(long geonameId) { this.geonameId = geonameId; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    @Override
    public String toString() {
        return name + " " + iataCode + " " + country;
    }
}