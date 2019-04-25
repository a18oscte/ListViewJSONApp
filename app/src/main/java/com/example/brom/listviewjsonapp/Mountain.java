package com.example.brom.listviewjsonapp;

public class Mountain {
    private String name;
    private String location;
    private int height;
    private int id;
    private String type;
    private String bild;
    private String url;

    public Mountain(int i,String n,String t, String l, int h, String b, String u){
        id = i;
        name = n;
        type = t;
        location = l;
        height = h;
        bild = b;
        url = u;
    }

    public String info(){
        String tmp;
        tmp ="ID: "+ id + "\nType: "+ type+"\nLocation: " + location + "\nHeight: " + height + " meters\n" + bild + "\n"+ url;

        return tmp;
    }

    public String namn(){
        return name;
    }
}
