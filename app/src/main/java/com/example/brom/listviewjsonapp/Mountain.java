package com.example.brom.listviewjsonapp;

public class Mountain {
    private String name;
    private String location;
    private int height;

    public Mountain(String n, String l, int h){
        name = n;
        location = l;
        height = h;
    }

    public String info(){
        String tmp;
        tmp = "Location: " + location + "\nHeight: " + height + " meters";

        return tmp;
    }

    public String namn(){
        return name;
    }
}
