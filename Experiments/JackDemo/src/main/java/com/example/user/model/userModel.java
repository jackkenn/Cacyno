package com.example.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class userModel {
    private final UUID _id;
    private final String _name;

    public userModel(@JsonProperty("id") UUID _id, @JsonProperty("name") String _name) {
        this._id = _id;
        this._name = _name;
    }

    public UUID get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }
}
