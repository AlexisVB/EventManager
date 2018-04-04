package aaaaa.p.eventmanager.BackEnd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Alexis on 04/04/2018.
 */

public class CDevice {
    private int IdDevice;
    private String Mac;
    private int IdUser;
    private String os;
    private String ssid;
    private String manufacturer;
    private String seeenEpoch;
    private Clocation location;
    private String ipv6;
    private String rssi;
    private String clientMac;
    private String seenTime;
    private String ipv4;

    public CDevice(int idDevice, String mac, int idUser, String os, String ssid, String manufacturer, String seeenEpoch, Clocation location, String ipv6, String rssi, String clientMac, String seenTime, String ipv4) {
        IdDevice = idDevice;
        Mac = mac;
        IdUser = idUser;
        this.os = os;
        this.ssid = ssid;
        this.manufacturer = manufacturer;
        this.seeenEpoch = seeenEpoch;
        this.location = location;
        this.ipv6 = ipv6;
        this.rssi = rssi;
        this.clientMac = clientMac;
        this.seenTime = seenTime;
        this.ipv4 = ipv4;
    }

    public int getIdDevice() {
        return IdDevice;
    }

    public void setIdDevice(int idDevice) {
        IdDevice = idDevice;
    }

    public String getMac() {
        return Mac;
    }

    public void setMac(String mac) {
        Mac = mac;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSeeenEpoch() {
        return seeenEpoch;
    }

    public void setSeeenEpoch(String seeenEpoch) {
        this.seeenEpoch = seeenEpoch;
    }

    public Clocation getLocation() {
        return location;
    }

    public void setLocation(Clocation location) {
        this.location = location;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getClientMac() {
        return clientMac;
    }

    public void setClientMac(String clientMac) {
        this.clientMac = clientMac;
    }

    public String getSeenTime() {
        return seenTime;
    }

    public void setSeenTime(String seenTime) {
        this.seenTime = seenTime;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }
    public static Clocation  parseJSON(String response)
    {
        Gson gson=new GsonBuilder().create();
        Clocation al=gson.fromJson(response,Clocation.class);
        return al;
    }
}
