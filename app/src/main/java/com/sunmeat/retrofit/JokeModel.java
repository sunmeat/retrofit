package com.sunmeat.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// https://sv443.net/jokeapi/v2/

public class JokeModel {
    @SerializedName("joke") // для одночастных шуток
    @Expose
    private String joke;

    @SerializedName("setup") // для двухчастных шуток
    @Expose
    private String setup;

    @SerializedName("delivery") // для двухчастных шуток
    @Expose
    private String delivery;

    @SerializedName("type")
    @Expose
    private String type;

    public String getContent() {
        if ("single".equals(type)) {
            return joke;
        } else {
            return setup + "\n" + delivery;
        }
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public void setType(String type) {
        this.type = type;
    }
}