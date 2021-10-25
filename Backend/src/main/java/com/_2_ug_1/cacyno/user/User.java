package com._2_ug_1.cacyno.user;

import com._2_ug_1.cacyno.game.Game;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "username")
    private String username;
    @Column(name = "money")
    private int money;
    @Column(name = "displayname")
    private Boolean displayname;
    @Column
    private int current_game_money;
    @Column
    private int card1;
    @Column
    private int card2;
    @Column
    private boolean folded;
    @Column
    private boolean isSpectator;
    @OneToOne(targetEntity = Game.class)
    @JoinColumn(name = "game_id")
    private Game game_id;


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

    public Boolean getDisplayname(){
        return displayname;
    }

    public void setDisplayname(Boolean displayname){
        this.displayname = displayname;
    }

    public int getCurrent_game_money(){return current_game_money;}

    public void setCurrent_game_money(int current_game_money){this.current_game_money = current_game_money;}

    public int getCard1(){return card1;}

    public void setCard1(int card1){this.card1 = card1;}

    public int getCard2(){return card2;}

    public void setCard2(int card2){this.card2 = card2;}

    public boolean getFolded(){return folded;}

    public void setFolded(boolean folded){this.folded = folded;}

    public boolean getIsSpectator() {return isSpectator;}

    public void setIsSpectator(boolean isSpectator){this.isSpectator = isSpectator;}

    public Game getGame() {return game_id;}

    public void setGame(Game game_id){this.game_id = game_id;}
}
