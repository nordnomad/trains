package com.example.project1.entities;

import java.util.List;

public class Train {
    public String number;
    public int model;
    public Station fromStation;
    public Station tillStation;
    public int fromDate;
    public int tillDate;
    public List<Wagon> wagons;
}
