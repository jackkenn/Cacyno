package com._2_ug_1.cacyno.models;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
@Api(value = "Game", description = "Game Entity that holds the data of the game state")
@Entity(name = "Game")
@Table(name = "game")
public class Game {
    @ApiModelProperty(notes = "The id of the game", name = "id")
    @Id
    @Column(name = "id")
    private String id;
    @ApiModelProperty(notes = "holds the data of the first card on the table", name = "public_card1")
    @Column(name = "public_card1")
    private int public_card1;
    @ApiModelProperty(notes = "holds the data of the second card on the table", name = "public_card2")
    @Column(name = "public_card2")
    private int public_card2;
    @ApiModelProperty(notes = "holds the data of the third card on the table", name = "public_card3")
    @Column(name = "public_card3")
    private int public_card3;
    @ApiModelProperty(notes = "holds the data of the fourth card on the table", name = "public_card4")
    @Column(name = "public_card4")
    private int public_card4;
    @ApiModelProperty(notes = "holds the data of the fifth card on the table", name = "public_card5")
    @Column(name = "public_card5")
    private int public_card5;
    @ApiModelProperty(notes = "holds the data of the total money in the round", name = "pot")
    @Column(name = "pot")
    private int pot;
    @ApiModelProperty(notes = "keeps track of how many rounds have been played in the game", name = "round")
    @Column(name = "round")
    private int round;
    @ApiModelProperty(notes = "holds the name of the lobby", name = "lobbyname")
    @Column(name = "lobbyname")
    private String lobbyname;
    @ApiModelProperty(notes = "keeps track of if the game is active or not", name = "active")
    @Column(name = "active")
    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPublic_card1() {
        return public_card1;
    }

    public int getPublic_card2() {
        return public_card2;
    }

    public int getPublic_card3() {
        return public_card3;
    }

    public int getPublic_card4() {
        return public_card4;
    }

    public int getPublic_card5() {
        return public_card5;
    }

    public void setPublic_card1(int public_card1) {
        this.public_card1 = public_card1;
    }

    public void setPublic_card2(int public_card2) {
        this.public_card2 = public_card2;
    }

    public void setPublic_card3(int public_card3) {
        this.public_card3 = public_card3;
    }

    public void setPublic_card4(int public_card4) {
        this.public_card4 = public_card4;
    }

    public void setPublic_card5(int public_card5) {
        this.public_card5 = public_card5;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getLobbyname() {
        return lobbyname;
    }

    public void setLobbyname(String lobbyname) {
        this.lobbyname = lobbyname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
