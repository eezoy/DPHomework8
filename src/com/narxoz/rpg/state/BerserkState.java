package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {

    private int turnsRemaining;

    public BerserkState() {
        this(2);
    }

    public BerserkState(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String getName() { return "Berserk"; }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int)(basePower * 1.6);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int)(rawDamage * 1.25);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " fights in a blind rage.");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0 || hero.getHp() > hero.getMaxHp() * 0.5) {
            System.out.println(hero.getName() + " calms down.");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() { return true; }
}
