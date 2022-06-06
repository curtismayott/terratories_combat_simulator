package com.mg.terratories_sim.utils;

import java.util.Random;

public class RNG {
    public static int rollD6(){
        Random rand = new Random();
        return rand.nextInt(5) + 1;
    }
    public static int rollD12(){
        Random rand = new Random();
        return rand.nextInt(11) + 1;
    }
}
