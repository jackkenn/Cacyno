package com._2_ug_1.cacyno.players;

import com._2_ug_1.cacyno.game.Game;
import com._2_ug_1.cacyno.user.User;

import javax.persistence.*;


@Entity
@Table(name = "players")
public class Players {
    @Id
    @OneToOne(targetEntity = User.class)
    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Id
    @OneToOne(targetEntity = Game.class)
    @PrimaryKeyJoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @Column(name = "current_game_money")
    private int current_game_money;

    @Column(name = "card1")
    private int card1;

    @Column(name = "card2")
    private int card2;

    @Column(name = "folded")
    private boolean folded;

    public User getUser_id(){return user;}

    public void setUser_id(User user){this.user = user;}

    public Game getGame_id(){return game;}

    public void setGame_id(Game game){this.game = game;}

    public int getCurrent_game_money(){return current_game_money;}

    public void setCurrent_game_money(int current_game_money){this.current_game_money = current_game_money;}

    public int getCard1(){return card1;}

    public void setCard1(int card1){this.card1 = card1;}

    public int getCard2(){return card2;}

    public void setCard2(int card2){this.card2 = card2;}

    public boolean getFolded(){return folded;}

    public void setFolded(boolean folded){this.folded = folded;}
}
