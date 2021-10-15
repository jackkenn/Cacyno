package com._2_ug_1.lobby;

import javax.persistence.*;

@Entity
@Table(name = "lobby")
public class Lobby{
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "lobbyname")
    private String lobbyname;
    @Column(name = "active")
    private boolean active;

    public String getId(){return id;}

    public void setId(String id){this.id = id;}

    public String getLobbyname(){return lobbyname;}

    public void setLobbyname(String lobbyname){this.lobbyname = lobbyname;}

    public boolean getActive(){return active;}

    public void setActive(boolean active){this.active = active;}
}