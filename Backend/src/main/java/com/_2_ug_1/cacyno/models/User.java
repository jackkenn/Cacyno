package com._2_ug_1.cacyno.models;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Api(value = "User", description = "User Entity that holds the data of the Users")
@Entity
@Table(name = "user")
/**
 * This class creates a user object that has multiple variables such has id, username, money, cards, isSpectator, etc. This player is used in a casino game and can make actions in the game.
 */
public class User {
    @ApiModelProperty(notes = "The id of the user", name = "id")
    @Id
    @Column(name = "id")
    /**
     * holds the ID of the user
     */
    private String id;
    @ApiModelProperty(notes = "the name of the user", name = "username")
    @Column(name = "username")
    /**
     * holds the name of the user
     */
    private String username;
    @ApiModelProperty(notes = "the amount of money the user has", name = "money")
    @Column(name = "money")
    /**
     * keeps track of how much money the user has
     */
    private int money;
    @ApiModelProperty(notes = "The name of the user that is displayed", name = "displayname")
    @Column(name = "displayname")
    /**
     * holds the value of the name the user wants to be called
     */
    private Boolean displayname;
    @ApiModelProperty(notes = "the amount of money that the user has in the current game that they are playing", name = "current_game_money")
    @Column(name = "current_game_money")
    /**
     * The amount of money that the user has in the current game that they are playing
     */
    private int current_game_money;
    @ApiModelProperty(notes = "keeps track of the first card the user is holding", name = "card1")
    @Column(name = "card1")
    /**
     * keeps track of the first card the user is holding
     */
    private int card1;
    @ApiModelProperty(notes = "keeps track of the second card the user is holding", name = "card2")
    @Column(name = "card2")
    /**
     * keeps track of the second card the user is holding
     */
    private int card2;
    @ApiModelProperty(notes = "keeps track if the user has folded or not", name = "folded")
    @Column(name = "folded")
    /**
     * keeps track if the user has folded or not
     */
    private boolean folded;
    @ApiModelProperty(notes = "keeps track if the user has played their turn yet that round", name = "hasPlayed")
    @Column(name = "hasPlayed")
    /**
     * keeps track if the user has played their turn yet that round
     */
    private boolean hasPlayed;
    @Column(name = "position")
    private int position;
    @ApiModelProperty(notes = "keeps track if the user is a spectator or a player in the game", name = "isSpectator")
    @Column(name = "isSpectator")
    /**
     * keeps track if the user is a spectator or a player in the game
     */
    private boolean isSpectator;
    @ApiModelProperty(notes = "holds the amount of how much the user is betting that round", name = "bet")
    @Column(name = "bet")
    /**
     * holds the amount of how much the user is betting that round
     */
    private int bet;
    @ApiModelProperty(notes = "gets the id of the game the user is in", name = "gameId")
    @ManyToOne(targetEntity = Game.class)
    @JoinColumn(name = "gameId")
    private Game gameId;

    /**
     * returns an id of a user
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * sets the id of the user
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * gets the username of the user
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username for the user
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the amount of money the user has
     *
     * @return money
     */
    public int getMoney() {
        return money;
    }

    /**
     * sets the amount of money the user has
     *
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * gets the users display name
     *
     * @return displayname
     */
    public Boolean getDisplayname() {
        return displayname;
    }

    /**
     * sets the users display name
     *
     * @param displayname
     */
    public void setDisplayname(Boolean displayname) {
        this.displayname = displayname;
    }

    /**
     * gets the users amount of money in the current game they are in
     *
     * @return game money
     */
    public int getCurrent_game_money() {
        return current_game_money;
    }

    /**
     * sets the amount of money the user has in their current game
     *
     * @param current_game_money
     */
    public void setCurrent_game_money(int current_game_money) {
        this.current_game_money = current_game_money;
    }

    /**
     * gets the first card that the user is holding
     *
     * @return card1
     */
    public int getCard1() {
        return card1;
    }

    /**
     * sets the first card that the user is holding
     *
     * @param card1
     */
    public void setCard1(int card1) {
        this.card1 = card1;
    }

    /**
     * gets the second card that the user is holding
     *
     * @return card2
     */
    public int getCard2() {
        return card2;
    }

    /**
     * sets the second card that the user is holding
     *
     * @param card2
     */
    public void setCard2(int card2) {
        this.card2 = card2;
    }

    /**
     * looks for if the user has folded that round or not
     *
     * @return if the user has folded
     */
    public boolean getFolded() {
        return folded;
    }

    /**
     * sets the user folded to a fixed yes or no
     *
     * @param folded
     */
    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    /**
     * gets if the user is a spectator or a player
     *
     * @return getIsSpectator()
     */
    public boolean getIsSpectator() {
        return isSpectator;
    }

    /**
     * sets the user to a spectator or a player
     *
     * @param isSpectator
     */
    public void setIsSpectator(boolean isSpectator) {
        this.isSpectator = isSpectator;
    }

    /**
     * gets the gameis that the user is in
     *
     * @return game that the user is in
     */
    public Game getGame() {
        return gameId;
    }

    /**
     * sets the gameid that the user is in
     *
     * @param gameId
     */
    public void setGame(Game gameId) {
        this.gameId = gameId;
    }

    /**
     * gets the amount that the user has bet
     *
     * @return amount user has bet
     */
    public int getBet() {
        return bet;
    }

    /**
     * sets the amount that the user bets
     *
     * @param bet
     */
    public void setBet(int bet) {
        this.bet = bet;
    }

    /**
     * gets if the user has played this round or not
     *
     * @return if the user has played or not
     */
    public boolean getHasPlayed() {
        return hasPlayed;
    }

    /**
     * sets if the user has played this round or not
     *
     * @param hasPlayed
     */
    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    /**
     * gets the postion of the player
     *
     * @return position of the player
     */
    public int getPosition() {
        return position;
    }

    /**
     * sets the position of the player
     *
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }
}
