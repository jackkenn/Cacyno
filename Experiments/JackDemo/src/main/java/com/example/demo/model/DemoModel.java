package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DemoModel {
    private final UUID _id;
    private final String _name;

    public DemoModel(@JsonProperty("id") UUID _id,@JsonProperty("name") String _name) {
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
