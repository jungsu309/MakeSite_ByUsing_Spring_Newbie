package com.jungsu_site.jungsu_tutorial.domain;

public class Member {

    private Long Auto_serial_number;
    private String id;
    private String pw;
    private String nickname;


    public Long getAuto_serial_number() {
        return Auto_serial_number;
    }

    public void setAuto_serial_number(Long auto_serial_number) {
        Auto_serial_number = auto_serial_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
