package com.jungsu_site.jungsu_tutorial.controller;

//웹 등록 화면에서 데이터를 전달 받을 폼 객체 -> html의 name이랑 이어짐.
//입력받은거 처리하는곳인듯?
public class JoinForm {
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

    //html의 name과 동일하게 맞춰야하는듯함.
    private String id;
    private String pw;
    private String nickname;



}
