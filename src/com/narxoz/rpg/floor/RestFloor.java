package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;

import java.util.*;

public class RestFloor extends TowerFloor {

    private final String floorName;
    private final int healAmount;

    public RestFloor(String floorName, int healAmount) {
        this.floorName = floorName;
        this.healAmount = healAmount;
    }

    @Override
    protected String getFloorName() { return floorName; }

    @Override
    protected void announce() {
        System.out.println("\n--- " + getFloorName() + " ---");
        System.out.println("A quiet room. The party takes a moment to rest.");
    }

    @Override
    protected void setup(List<Hero> party) {}

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        for (Hero hero : party) {
            if (!hero.isAlive()) continue;
            hero.heal(healAmount);
            System.out.println("  " + hero.getName() + " recovers " + healAmount + " HP. (" + hero.getHp() + " HP)");
        }
        return new FloorResult(true, 0, "Party rested.");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {}
}
