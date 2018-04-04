package aaaaa.p.eventmanager.BackEnd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by Alexis on 04/04/2018.
 */

public class CContainer {
    private String last_update;
    private List<CAp> update_set;

    public CContainer(String last_update, List<CAp> update_set) {
        this.last_update = last_update;
        this.update_set = update_set;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public List<CAp> getUpdate_set() {
        return update_set;
    }

    public void setUpdate_set(List<CAp> update_set) {
        this.update_set = update_set;
    }
    public static CAp  parseJSON(String response)
    {
        Gson gson=new GsonBuilder().create();
        CAp al=gson.fromJson(response,CAp.class);
        return al;
    }
}
