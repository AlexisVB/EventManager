package aaaaa.p.eventmanager.BackEnd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by Alexis on 04/04/2018.
 */

public class CAp {
    private double update_epoch;
    private List<CDevice> observations;
    private  List<String> apTags;
    private String apMac;

    public CAp(double update_epoch, List<CDevice> observations, List<String> apTags, String apMac, List<String> apFloors) {
        this.update_epoch = update_epoch;
        this.observations = observations;
        this.apTags = apTags;
        this.apMac = apMac;

    }

    public double getUpdate_epoch() {
        return update_epoch;
    }

    public void setUpdate_epoch(double update_epoch) {
        this.update_epoch = update_epoch;
    }

    public List<CDevice> getObservations() {
        return observations;
    }

    public void setObservations(List<CDevice> observations) {
        this.observations = observations;
    }

    public List<String> getApTags() {
        return apTags;
    }

    public void setApTags(List<String> apTags) {
        this.apTags = apTags;
    }

    public String getApMac() {
        return apMac;
    }

    public void setApMac(String apMac) {
        this.apMac = apMac;
    }


    public static CDevice parseJSON(String response)
    {
        Gson gson=new GsonBuilder().create();
        CDevice d=gson.fromJson(response,CDevice.class);
        return d;
    }
}
