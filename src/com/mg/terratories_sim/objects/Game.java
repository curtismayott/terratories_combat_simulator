package com.mg.terratories_sim.objects;

import com.mg.terratories_sim.Main;
import com.mg.terratories_sim.utils.Log;
import com.mg.terratories_sim.utils.RNG;

import java.util.HashMap;

public class Game {
    HashMap<Integer, Elemental> elementals;
    int turnCount;

    public Game() {
        elementals = new HashMap<>();
        turnCount = 0;
    }

    public HashMap<Integer, Elemental> getElementals() {
        return elementals;
    }

    public void setElementals(HashMap<Integer, Elemental> elementals) {
        this.elementals = elementals;
    }

    public void addElemental(Elemental elemental){
        elementals.put(elementals.size(), elemental);
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public void fight(){
        Elemental elemA = null;
        Elemental elemB = null;
        if(elementals.get(0).getSpeed() < elementals.get(1).getSpeed()){
            elemA = elementals.get(1);
            elemB = elementals.get(0);
            elementals.clear();
            elementals.put(0, elemA);
            elementals.put(1, elemB);
        }
        // elementals ordered by speed
        int turn = 0;
        while(elementals.get(0).getHealth() > 0 && elementals.get(1).getHealth() > 0){
            // determine whose turn it is
            int attackerTurn = 0;
            int defenderTurn = 0;
            if(turn == 0){
                attackerTurn = 0;
                defenderTurn = 1;
            }else{
                attackerTurn = 1;
                defenderTurn = 0;
            }
            //System.out.println("attacker: " + (attackerTurn) + " " + (defenderTurn));
            takeTurn(elementals.get(attackerTurn), elementals.get(defenderTurn));
            if(turn == 1){
                turn = 0;
            }else{
                turn = 1;
            }
        }
        if(elementals.get(0).getHealth() > 0){
            Main.scores.replace(elementals.get(0).getName(), Main.scores.get(elementals.get(0).getName()) + 1);
        }else{
            Main.scores.replace(elementals.get(1).getName(), Main.scores.get(elementals.get(1).getName()) + 1);
        }
        Main.turnCounts.add(turnCount);
        Log.e("first turn: " + elementals.get(0).getName() + " second turn: " + elementals.get(1).getName(), false);
        Log.e("Results: | turnCount: " + turnCount, false);
        Log.e(elementals.get(0).getName() + ": " + elementals.get(0).getHealth() + " | " + elementals.get(1).getName() + ": " + elementals.get(1).getHealth(), false);
    }

    public void takeTurn(Elemental attacker, Elemental defender){
        turnCount++;
        Ability a = null;
        //check for stun
        if(!attacker.isStunned()) {
            if (attacker.determineAbility() != null) {
                Ability tempAbility = attacker.determineAbility();
                String tElemName = tempAbility.getElemName();
                String tName = tempAbility.getName();
                int tCoolDown = tempAbility.getCooldown();
                int tDuration = tempAbility.getDuration();
                String tStatName = tempAbility.getStatName();
                int tStatIncreaseAmount = tempAbility.getStatIncreaseAmount();
                boolean tIsBuff = tempAbility.isBuff();
                a = new Ability(tElemName, tName, tCoolDown, tDuration, tStatName, tStatIncreaseAmount, tIsBuff);
            }
            if (a == null) {
                Log.e("no ability cast", true);
                int hitRNG = RNG.rollD6();
                if (attacker.getHit() + hitRNG > defender.getBarrier()) {
                    int damage = RNG.rollD6() + attacker.getStrength();
                    int toughness = RNG.rollD6() + defender.getToughness();
                    if (damage > toughness) {
                        int damageDealt = damage - toughness;
                        defender.setHealth(defender.getHealth() - damageDealt);
                    } else {
                        // no damage
                    }
                } else {
                    // no hit
                }
            }
        }
        attacker.updateStatusEffects();
        attacker.updateCooldowns();
        if(a != null){
            if(a.isBuff()){
                attacker.applyStatusEffect(a.clone());
            }else {
                boolean resistEffect = false;
                if(a.getStatName().equals("Stun")){
                    if(defender.getResiliency() + RNG.rollD12() >= 8){
                        resistEffect = true;
                        Log.e("resisted stun: " + a.getName(), true);
                    }
                }
                if(!resistEffect) {
                    defender.applyStatusEffect(a.clone());
                    Log.e("applied status effect: " + a.getName(), true);
                }
            }
            attacker.applyCooldown(a.clone());
        }
        Log.e("turn: " + turnCount, true);
        for(int i : elementals.keySet()){
            Log.e(elementals.get(i).toString(), true);
        }
    }
}
