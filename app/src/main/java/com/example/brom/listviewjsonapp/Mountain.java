package com.example.brom.listviewjsonapp;

public class Mountain {
    private String name;
    private String location;
    private int height;
    private String bild;
    private String url;

    public Mountain(String n, String l, int h, String b, String u){
        name = n;

        location = l;
        height = h;
        bild = b;
        url = u;
    }

    public String info(){
        String tmp;
        tmp ="Location: " + location + "\nHeight: " + height + " meters\nÖppna i Wikipedia:";

        return tmp;
    }

    public String namn(){
        return name;
    }

    public String img(){
        return bild;
    }

    public String ur(){
        return url;
    }
}
