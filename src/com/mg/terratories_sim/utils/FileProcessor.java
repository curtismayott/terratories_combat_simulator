package com.mg.terratories_sim.utils;

import com.mg.terratories_sim.Main;
import com.mg.terratories_sim.objects.Ability;
import com.mg.terratories_sim.objects.Elemental;
import com.mg.terratories_sim.objects.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileProcessor {

    public Game processFile(String fileName) throws FileNotFoundException {
        Game g = new Game();
        Scanner scanner = new Scanner(new File(fileName));
        if(scanner != null) {
            while (scanner.hasNextLine()) {
                g.addElemental(parseElemental(scanner.nextLine()));
            }
        }
        return g;
    }

    public Elemental parseElemental(String line){
        Elemental e = new Elemental();
        e.setName(line.split(",")[0]);
        e.setHit(Integer.parseInt(line.split(",")[1]));
        e.setStrength(Integer.parseInt(line.split(",")[2]));
        e.setBarrier(Integer.parseInt(line.split(",")[3]));
        e.setToughness(Integer.parseInt(line.split(",")[4]));
        e.setHealth(Integer.parseInt(line.split(",")[5]));
        e.setSpeed(Integer.parseInt(line.split(",")[6]));
        e.setFocus(Integer.parseInt(line.split(",")[7]));
        e.setSupression(Integer.parseInt(line.split(",")[8]));
        e.setResiliency(Integer.parseInt(line.split(",")[9]));
        e.setTerraforming(Integer.parseInt(line.split(",")[10]));

        Scanner scan = null;
        try {
            scan = new Scanner(new File("C:\\Users\\Curtis MG\\IdeaProjects\\terratories_simulator\\src\\com\\mg\\terratories_sim\\input\\ability_input"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if(scan != null) {
            while (scan.hasNextLine()) {
                Ability ability = parseAbility(scan.nextLine());
                if(ability.getElemName().equals(e.getName())) {
                    e.addAbility(ability);
                }
            }
        }

        if(Main.scores.size() < 2){
            Main.scores.put(e.getName(), 0);
        }
        return e;
    }

    public Ability parseAbility(String line){
        Ability a = new Ability();
        a.setElemName(line.split(",")[0]);
        a.setName(line.split(",")[1]);
        a.setDuration(Integer.parseInt(line.split(",")[2]));
        a.setCooldown(Integer.parseInt(line.split(",")[3]));
        a.setStatName(line.split(",")[4]);
        a.setStatIncreaseAmount(Integer.parseInt(line.split(",")[5]));
        a.setBuff(Integer.parseInt(line.split(",")[6]) == 1);
        return a;
    }
}
