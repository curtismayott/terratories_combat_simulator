package com.mg.terratories_sim.objects;

import com.mg.terratories_sim.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Elemental {
    String name;
    int hit;
    int strength;
    int barrier;
    int toughness;
    int health;
    int speed;
    int focus;
    int supression;
    int resiliency;
    int terraforming;
    HashMap<Integer,Ability> abilities;
    ArrayList<Ability> statusEffects;
    ArrayList<Ability> coolDowns;

    public Elemental() {
        abilities = new HashMap<>();
        statusEffects = new ArrayList<>();
        coolDowns = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Elemental{" +
                "name='" + name + '\'' +
                ", hit=" + hit +
                ", strength=" + strength +
                ", barrier=" + barrier +
                ", toughness=" + toughness +
                ", health=" + health +
                ", speed=" + speed +
                ", focus=" + focus +
                ", supression=" + supression +
                ", resiliency=" + resiliency +
                ", terraforming=" + terraforming +
                ", abilities=" + abilities +
                ", statusEffects=" + statusEffects +
                ", coolDowns=" + coolDowns +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getBarrier() {
        return barrier;
    }

    public void setBarrier(int barrier) {
        this.barrier = barrier;
    }

    public int getToughness() {
        return toughness;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    public int getSupression() {
        return supression;
    }

    public void setSupression(int supression) {
        this.supression = supression;
    }

    public int getResiliency() {
        return resiliency;
    }

    public void setResiliency(int resiliency) {
        this.resiliency = resiliency;
    }

    public int getTerraforming() {
        return terraforming;
    }

    public void setTerraforming(int terraforming) {
        this.terraforming = terraforming;
    }

    public HashMap<Integer,Ability> getAbilities() {
        return abilities;
    }

    public void addAbility(Ability ability) {
        abilities.put(abilities.size(), ability);
        Log.e("ability: " + getName() + " " + ability.getName(), true);
    }

    public Ability determineAbility(){
        Ability a = null;
        for(int i : abilities.keySet()){
            boolean isOnCooldown = false;
            for(int j = 0; j < coolDowns.size(); j++){
                if(coolDowns.get(j).getName().equals(abilities.get(i).getName())){
                    isOnCooldown = true;
                }
            }
            // add check for if abilities are on cooldown
            if(!isOnCooldown) {
                if (a == null) {
                    a = abilities.get(i);
                } else {
                    if (abilities.get(i).getStatName().equals("Barrier")) {
                        a = abilities.get(i);
                        return a;
                    }
                    if (abilities.get(i).getStatName().equals("Hit")) {
                        a = abilities.get(i);
                        return a;
                    }
                    // apply other prioritization logic
                }
                return a;
            }
        }
        return a;
    }

    // perform after updateStatusEffect
    public void applyStatusEffect(Ability a){
        statusEffects.add(a);
        modifyStat(a.getStatName(), a.getStatIncreaseAmount(), a.isBuff(), false);
    }

    public void applyCooldown(Ability a){
        coolDowns.add(a);
    }

    public void updateCooldowns(){
        ArrayList<Integer> exhaustedCooldowns = new ArrayList<>();
        for(int i = 0; i < coolDowns.size(); i++){
            coolDowns.get(i).setCooldown(coolDowns.get(i).getCooldown() - 1);
            if(coolDowns.get(i).getCooldown() <= 0){
                exhaustedCooldowns.add(i);
            }
        }

        int offset = 0;
        for(int i = 0; i < exhaustedCooldowns.size(); i++){
            Log.e("removing status effect: " + coolDowns.get(exhaustedCooldowns.get(i - offset)).getName() + " index: " + (i-offset), true);
            int index = exhaustedCooldowns.get(i - offset);
            coolDowns.remove(index);
            //statusEffects.remove(0);
            offset++;
        }
    }

    public void updateStatusEffects(){
        ArrayList<Integer> exhaustedStatusEffects = new ArrayList<Integer>();
        for(int i = 0; i < statusEffects.size(); i++){
            int currentDuration = statusEffects.get(i).getDuration();
            statusEffects.get(i).setDuration(currentDuration - 1);
            Log.e("updating status effect: " + statusEffects.get(i).getName() + " duration: " + statusEffects.get(i).getDuration(), true);
            if(statusEffects.get(i).getDuration() <= 0){
                Log.e("exhausting status effect: " + statusEffects.get(i).getName() + " duration: " + statusEffects.get(i).getDuration(), true);
                exhaustedStatusEffects.add(i);
                modifyStat(statusEffects.get(i).getStatName(), statusEffects.get(i).getStatIncreaseAmount(), statusEffects.get(i).isBuff(), true);
            }
        }
        int offset = 0;
        for(int i = 0; i < exhaustedStatusEffects.size(); i++){
            Log.e("removing status effect: " + statusEffects.get(exhaustedStatusEffects.get(i - offset)).getName() + " index: " + (i-offset), true);
            int index = exhaustedStatusEffects.get(i - offset);
            statusEffects.remove(index);
            offset++;
        }
        for(int i = 0; i < statusEffects.size(); i++){
            //Log.e("effects: " + abilities.get(i).toString(), true);
        }
    }

    public boolean isStunned(){
        for(int i = 0; i < statusEffects.size(); i++){
            if(statusEffects.get(i).getStatName().equals("Stun")){
                return true;
            }
        }
        return false;
    }

    public void modifyStat(String statName, int value, boolean isBuff, boolean exhaust){
        if(exhaust) {
            if (statName.equals("Barrier")) {
                if (isBuff) {
                    setBarrier(getBarrier() - value);
                } else {
                    setBarrier(getBarrier() + value);
                }
            }
            if (statName.equals("Hit")) {
                if (isBuff) {
                    setHit(getHit() - value);
                } else {
                    setHit(getHit() + value);
                }
            }
            if (statName.equals("Strength")) {
                if (isBuff) {
                    setStrength(getStrength() - value);
                } else {
                    setStrength(getStrength() + value);
                }
            }
            if (statName.equals("Toughness")) {
                if (isBuff) {
                    setToughness(getToughness() - value);
                } else {
                    setToughness(getToughness() + value);
                }
            }
        }else{
            if (statName.equals("Barrier")) {
                if (isBuff) {
                    setBarrier(getBarrier() + value);
                } else {
                    setBarrier(getBarrier() - value);
                }
            }
            if (statName.equals("Hit")) {
                if (isBuff) {
                    setHit(getHit() + value);
                } else {
                    setHit(getHit() - value);
                }
            }
            if (statName.equals("Strength")) {
                if (isBuff) {
                    setStrength(getStrength() + value);
                } else {
                    setStrength(getStrength() - value);
                }
            }
            if (statName.equals("Toughness")) {
                if (isBuff) {
                    setToughness(getToughness() + value);
                } else {
                    setToughness(getToughness() - value);
                }
            }
        }
    }
}
