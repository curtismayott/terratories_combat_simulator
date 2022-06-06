package com.mg.terratories_sim.utils;

public class Log {
    static boolean isActive = true;
    static boolean debugging = false;

    public static void e(String message, boolean isDebugging){
        if(isActive){
            if(isDebugging){
                // do nothing
                if(debugging){
                    System.out.println(message);
                }
            }else{
                System.out.println(message);
            }
        }
    }
}
