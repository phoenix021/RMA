package com.jz_rma_projekat.airplane;
import lombok.Data;

@Data
public class Live {
    public String updated;
    public String latitude;
    public String longitude;
    public String altitude;
    public String direction;
    public String speed_horizontal;
    public String speed_vertical;
}