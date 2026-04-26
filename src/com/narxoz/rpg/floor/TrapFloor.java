package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.PoisonedState;

import java.util.*;

public class TrapFloor extends TowerFloor {

    private final String floorName;
    private final int trapDamage;

    public TrapFloor(String floorName, int trapDamage) {
        this.floorName = floorName;
        this.trapDamage = trapDamage;
    }

    @Override
    protected String getFloorName() { return floorName; }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("The floor is riddled with hidden traps.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamage = 0;

        for (Hero hero : party) {
            if (!hero.isAlive()) continue;

            hero.takeDamage(trapDamage);
            totalDamage += trapDamage;
            System.out.println("  " + hero.getName() + " triggers a trap and takes " + trapDamage + " damage. (" + hero.getHp() + " HP left)");

            
            if (hero.getHp() < hero.getMaxHp() * 0.5 && !(hero.getState() instanceof PoisonedState)) {
                System.out.println("  The trap was poisoned! " + hero.getName() + " is now poisoned.");
                hero.setState(new PoisonedState());
            }
        }

        boolean cleared = anyAlive(party);
        String summary = cleared ? "Traps survived." : "Party wiped on " + floorName + ".";
        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("Behind the traps, the party finds a stash of gold.");
        }
    }

    private boolean anyAlive(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) return true;
        }
        return false;
    }
}
