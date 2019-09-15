package com.example.e_quiz;

public class User
{

    public String name,email,password;
     public String q,a,b,c,d,ans;
     public String msg,user;

    public User(){}


    public String getMsg() {
        return msg;
    }

    public String getUser() {
        return user;
    }

    public User(String msg, String user) {
        this.msg = msg;
        this.user = user;
    }

    public User(String q)
    {
        this.q=q;
    }
    public User(String name, String email, String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User(String q, String a, String b, String c, String d, String ans) {
        this.q = q;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.ans = ans;
    }

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public String getAns() {
        return ans;
    }

    public String getQ() {
        return q;
    }
}
