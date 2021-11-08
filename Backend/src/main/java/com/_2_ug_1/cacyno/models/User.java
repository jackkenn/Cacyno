package com._2_ug_1.cacyno.models;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Api(value = "User", description = "User Entity that holds the data of the Users")
@Entity
@Table(name = "user")
public class User {
    @ApiModelProperty(notes = "The id of the user", name = "id")
    @Id
    @Column(name = "id")
    private String id;
    @ApiModelProperty(notes = "the name of the user", name = "username")
    @Column(name = "username")
    private String username;
    @ApiModelProperty(notes = "the amount of money the user has", name = "money")
    @Column(name = "money")
    private int money;
    @ApiModelProperty(notes = "The name of the user that is displayed", name = "displayname")
    @Column(name = "displayname")
    private Boolean displayname;
    @ApiModelProperty(notes = "the amount of money that the user has in the current game that they are playing", name = "current_game_money")
    @Column(name = "current_game_money")
    private int current_game_money;
    @ApiModelProperty(notes = "keeps track of the first card the user is holding", name = "card1")
    @Column(name = "card1")
    private int card1;
    @ApiModelProperty(notes = "keeps track of the second card the user is holding", name = "card2")
    @Column(name = "card2")
    private int card2;
    @ApiModelProperty(notes = "keeps track if the user has folded or not", name = "folded")
    @Column(name = "folded")
    private boolean folded;
    @ApiModelProperty(notes = "keeps track if the user has played their turn yet that round", name = "hasPlayed")
    @Column(name = "hasPlayed")
    private boolean hasPlayed;
    @Column(name = "position")
    private int position;
    @ApiModelProperty(notes = "keeps track if the user is a spectator or a player in the game", name = "isSpectator")
    @Column(name = "isSpectator")
    private boolean isSpectator;
    @ApiModelProperty(notes = "holds the amount of how much the user is betting that round", name = "bet")
    @Column(name = "bet")
    private int bet;
    @ManyToOne(targetEntity = Game.class)
    @JoinColumn(name = "gameId")
    private Game gameId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Boolean getDisplayname() {
        return displayname;
    }

    public void setDisplayname(Boolean displayname) {
        this.displayname = displayname;
    }

    public int getCurrent_game_money() {
        return current_game_money;
    }

    public void setCurrent_game_money(int current_game_money) {
        this.current_game_money = current_game_money;
    }

    public int getCard1() {
        return card1;
    }

    public void setCard1(int card1) {
        this.card1 = card1;
    }

    public int getCard2() {
        return card2;
    }

    public void setCard2(int card2) {
        this.card2 = card2;
    }

    public boolean getFolded() {
        return folded;
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    public boolean getIsSpectator() {
        return isSpectator;
    }

    public void setIsSpectator(boolean isSpectator) {
        this.isSpectator = isSpectator;
    }

    public Game getGame() {
        return gameId;
    }

    public void setGame(Game gameId) {
        this.gameId = gameId;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public boolean getHasPlayed() {
        return hasPlayed;
    }

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
