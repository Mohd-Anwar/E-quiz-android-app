package com.example.e_quiz;

public class UserDetails
{
   public String msg,from,time;


   public UserDetails(){}
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUserid(String userid) {
        this.from = userid;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserDetails(String msg, String userid, String time) {
        this.msg = msg;
        this.from = userid;
        this.time = time;
    }
}
