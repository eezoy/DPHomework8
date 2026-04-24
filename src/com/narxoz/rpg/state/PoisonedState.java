package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {

    private int turnsRemaining;

    public PoisonedState() {
        this(3);
    }

    public PoisonedState(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String getName() { return "Poisoned"; }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int)(basePower * 0.7);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " takes 5 damage from poison.");
        hero.takeDirect(5);
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            System.out.println(hero.getName() + " is no longer poisoned.");
            hero.setState(new NormalState());
        } else {
            System.out.println(hero.getName() + " is still poisoned (" + turnsRemaining + " turns left).");
        }
    }

    @Override
    public boolean canAct() { return true; }
}
