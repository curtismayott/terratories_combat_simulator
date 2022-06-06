package com.mg.terratories_sim;

import com.mg.terratories_sim.objects.Game;
import com.mg.terratories_sim.utils.FileProcessor;
import com.mg.terratories_sim.utils.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static HashMap<String,Integer> scores;
    public static ArrayList<Integer> turnCounts;

    public static void main(String[] args) {
	    // write your code here
        FileProcessor processor = new FileProcessor();
        Game game = null;
        scores = new HashMap<>();
        turnCounts = new ArrayList<>();
        int matchCount = 100000;

        try {
            game = processor.processFile("C:\\Users\\Curtis MG\\IdeaProjects\\terratories_simulator\\src\\com\\mg\\terratories_sim\\input\\elem_input");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < matchCount; i++){
            game.fight();
            if(i != matchCount-1) {
                try {
                    game = processor.processFile("C:\\Users\\Curtis MG\\IdeaProjects\\terratories_simulator\\src\\com\\mg\\terratories_sim\\input\\elem_input");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e("\nFinal Scores:", false);
        for(String s : scores.keySet()){
            Log.e(s + " " + scores.get(s), false);
        }

        int averageTurns = 0;
        int turnHighestScore = 0;
        int turnLowestScore = 1000;
        for(int i = 0; i < turnCounts.size(); i++){
            averageTurns += turnCounts.get(i);
            if(turnCounts.get(i) > turnHighestScore){
                turnHighestScore = turnCounts.get(i);
            }
            if(turnCounts.get(i) < turnLowestScore){
                turnLowestScore = turnCounts.get(i);
            }
        }
        Log.e("Average Turn Counts: " + (averageTurns / turnCounts.size()), false);
        Log.e("Highest Score: " + turnHighestScore + " | Lowest Score: " + turnLowestScore, false);
    }
}
