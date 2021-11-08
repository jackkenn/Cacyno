package com._2_ug_1.cacyno.models;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Api(value = "Lobby", description = "Lobby Entity that holds the data of the Lobby state")
@Entity(name = "Lobby")
@Table(name = "game")
public class Lobby {
    @ApiModelProperty(notes = "The id of the lobby", name = "id")
    @Id
    @Column(name = "id")
    private String id;
    @ApiModelProperty(notes = "holds the value of the name of the lobby", name = "lobbyname")
    @Column(name = "lobbyname")
    private String lobbyname;
    @ApiModelProperty(notes = "keeps track if the lobby is active or not", name = "active")
    @Column(name = "active")
    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLobbyname() {
        return lobbyname;
    }

    public void setLobbyname(String lobbyname) {
        this.lobbyname = lobbyname;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}