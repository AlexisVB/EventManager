package aaaaa.p.eventmanager.BackEnd;

import java.util.List;

/**
 * Created by Alexis on 04/04/2018.
 */

public class Clocation {
    private double lng;
    private double lat;
    private double unc;

    public Clocation(double lng, double lat, double unc) {
        this.lng = lng;
        this.lat = lat;
        this.unc = unc;

    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getUnc() {
        return unc;
    }

    public void setUnc(double unc) {
        this.unc = unc;
    }




}
