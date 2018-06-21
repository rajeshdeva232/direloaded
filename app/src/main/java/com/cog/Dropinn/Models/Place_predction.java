package com.cog.Dropinn.Models;

import java.util.ArrayList;

/**
 * Created by test on 2/8/18.
 */

public class Place_predction {
    public ArrayList<NotificationAdapter_Model> getPlaces() {
        return predictions;
    }

    public void setPlaces(ArrayList<NotificationAdapter_Model> places) {
        this.predictions = places;
    }

    private ArrayList<NotificationAdapter_Model> predictions;
}
