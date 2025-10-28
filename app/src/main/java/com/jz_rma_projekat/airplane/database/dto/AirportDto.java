package com.jz_rma_projekat.airplane.database.dto;

import com.google.gson.annotations.SerializedName;

public class AirportDto {

    @SerializedName("id")
    private String id;

    @SerializedName("gmt")
    private String gmt;

    @SerializedName("airport_id")
    private int airportId;

    @SerializedName("iata_code")
    private String iataCode;

    @SerializedName("city_iata_code")
    private String cityIataCode;

    @SerializedName("icao_code")
    private String icaoCode;

    @SerializedName("country_iso2")
    private String countryIso2;

    @SerializedName("geoname_id")
    private long geonameId;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("airport_name")
    private String name;

    @SerializedName("country_name")
    private String country;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("timezone")
    private String timezone;

    public AirportDto(String id, String gmt, int airportId, String iataCode,
                      String cityIataCode, String icaoCode, String countryIso2,
                      long geonameId, double latitude, double longitude,
                      String name, String country, String phoneNumber, String timezone) {
        this.id = id;
        this.gmt = gmt;
        this.airportId = airportId;
        this.iataCode = iataCode;
        this.cityIataCode = cityIataCode;
        this.icaoCode = icaoCode;
        this.countryIso2 = countryIso2;
        this.geonameId = geonameId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.timezone = timezone;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getGmt() {
        return gmt;
    }

    public int getAirportId() {
        return airportId;
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getCityIataCode() {
        return cityIataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public String getCountryIso2() {
        return countryIso2;
    }

    public long getGeonameId() {
        return geonameId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTimezone() {
        return timezone;
    }

    // Setters for all fields

    public void setId(String id) {
        this.id = id;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }

    public void setAirportId(int airportId) {
        this.airportId = airportId;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public void setCityIataCode(String cityIataCode) {
        this.cityIataCode = cityIataCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public void setCountryIso2(String countryIso2) {
        this.countryIso2 = countryIso2;
    }

    public void setGeonameId(long geonameId) {
        this.geonameId = geonameId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDisplayName() {
        return name + " - " + iataCode + " (" + country + ")";
    }
}