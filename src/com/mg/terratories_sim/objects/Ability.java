package com.mg.terratories_sim.objects;

public class Ability implements Cloneable {

    String elemName;
    String name;
    int cooldown;
    int duration;
    String statName;
    int statIncreaseAmount;
    boolean buff;

    public Ability() {
    }

    public Ability(Ability a) {
        this.elemName = a.getElemName();
        this.name = a.getName();
        this.cooldown = a.getCooldown();
        this.duration = a.getDuration();
        this.statName = a.getStatName();
        this.statIncreaseAmount = a.getStatIncreaseAmount();
        this.buff = a.isBuff();
    }

    public Ability(String elemName, String name, int cooldown, int duration, String statName, int statIncreaseAmount, boolean buff) {
        this.elemName = elemName;
        this.name = name;
        this.cooldown = cooldown;
        this.duration = duration;
        this.statName = statName;
        this.statIncreaseAmount = statIncreaseAmount;
        this.buff = buff;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "elemName='" + elemName + '\'' +
                ", name='" + name + '\'' +
                ", cooldown=" + cooldown +
                ", duration=" + duration +
                ", statName='" + statName + '\'' +
                ", statIncreaseAmount=" + statIncreaseAmount +
                ", buff=" + buff +
                '}';
    }

    public String getElemName() {
        return elemName;
    }

    public void setElemName(String elemName) {
        this.elemName = elemName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public int getStatIncreaseAmount() {
        return statIncreaseAmount;
    }

    public void setStatIncreaseAmount(int statIncreaseAmount) {
        this.statIncreaseAmount = statIncreaseAmount;
    }

    public boolean isBuff() {
        return buff;
    }

    public void setBuff(boolean buff) {
        this.buff = buff;
    }

    @Override
    public Ability clone() {
        try {
            Ability clone = (Ability) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
