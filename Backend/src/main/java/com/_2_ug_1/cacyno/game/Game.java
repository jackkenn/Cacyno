package com._2_ug_1.cacyno.game;

import com._2_ug_1.cacyno.players.Players;

import javax.persistence.*;
@Entity
@Table(name = "game")
public class Game {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "isSpectator")
    private boolean isSpectator;
    @Column(name = "public_card1")
    private int public_card1;
    @Column(name = "public_card2")
    private int public_card2;
    @Column(name = "public_card3")
    private int public_card3;
    @Column(name = "public_card4")
    private int public_card4;
    @Column(name = "public_card5")
    private int public_card5;
    @Column(name = "pot")
    private int pot;

    public String getId(){return id;}

    public void setId(String id){this.id = id;}

    public boolean isSpectator() {return isSpectator;}

    public void setisSpectator(boolean isSpectator) {this.isSpectator = isSpectator;}

    public int getPublic_card1(){return public_card1;}

    public int getPublic_card2(){return public_card2;}

    public int getPublic_card3(){return public_card3;}

    public int getPublic_card4(){return public_card4;}

    public int getPublic_card5(){return public_card5;}

    public void setPublic_card1(int public_card1){this.public_card1 = public_card1;}

    public void setPublic_card2(int public_card2){this.public_card2 = public_card2;}

    public void setPublic_card3(int public_card3){this.public_card3 = public_card3;}

    public void setPublic_card4(int public_card4){this.public_card4 = public_card4;}

    public void setPublic_card5(int public_card5){this.public_card5= public_card5;}

    public int getPot(){return pot;}

    public void setPot(int pot){this.pot = pot;}
}
