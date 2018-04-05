package aaaaa.p.eventmanager.BackEnd;

import java.util.List;

/**
 * Created by Alexis on 04/04/2018.
 */

public class Clocation {
    private String lng;
    private String  lat;
    private String  unc;

    public Clocation(String lng, String lat, String unc) {
        this.lng = lng;
        this.lat = lat;
        this.unc = unc;

    }

    public String  getLng() {
        return lng;
    }

    public void setLng(String  lng) {
        this.lng = lng;
    }

    public String  getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String  getUnc() {
        return unc;
    }

    public void setUnc(String unc) {
        this.unc = unc;
    }

    public double ConvertLng()
    {
        return Double.parseDouble(this.lng);
    }
    public double ConvertLat()
    {
        return Double.parseDouble(this.lat);
    }

}
