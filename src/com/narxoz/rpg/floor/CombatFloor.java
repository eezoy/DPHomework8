package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.*;
import com.narxoz.rpg.state.*;

import java.util.*;

public class CombatFloor extends TowerFloor {

    private final String floorName;
    private Monster monster;

    public CombatFloor(String floorName, Monster monster) {
        this.floorName = floorName;
        this.monster = monster;
    }

    @Override
    protected String getFloorName() { return floorName; }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println(monster.getName() + " blocks the way (HP: " + monster.getHp() + ").");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamage = 0;
        int round = 1;

        while (monster.isAlive() && anyAlive(party)) {
            System.out.println("Round " + round + ":");

            for (Hero hero : party) {
                if (!hero.isAlive()) continue;

                hero.getState().onTurnStart(hero);

                if (hero.getState().canAct()) {
                    int dmg = hero.attack(monster);
                    System.out.println("  " + hero.getName() + " attacks " + monster.getName() + " for " + dmg + " damage.");
                } 

                hero.getState().onTurnEnd(hero);

                if (!monster.isAlive()) break;
            }

            if (monster.isAlive()) {
                for (Hero hero : party) {
                    if (!hero.isAlive()) continue;
                    monster.attack(hero);
                    int dmgDealt = Math.max(1, monster.getAttackPower() - 2);
                    totalDamage += dmgDealt;
                    System.out.println("  " + monster.getName() + " hits " + hero.getName() + " for " + dmgDealt + " damage. (" + hero.getHp() + " HP left)");

                    if (monster.getAttackPower() >= 12 && hero.getHp() < hero.getMaxHp() * 0.4 && !(hero.getState() instanceof StunnedState)) {
                        System.out.println("  The blow stuns " + hero.getName() + "!");
                        hero.setState(new StunnedState());
                    } 
                    
                    else if (hero.getHp() < hero.getMaxHp() * 0.3 && !(hero.getState() instanceof BerserkState) && !(hero.getState() instanceof StunnedState)) {
                        System.out.println("  " + hero.getName() + " is close to death and enters a rage!");
                        hero.setState(new BerserkState());
                    }
                }
            }

            round++;
        }

        boolean cleared = !monster.isAlive();
        String summary;
        if (cleared) {
            summary = monster.getName() + " defeated.";
        } 
        
        else {
            summary = "Party wiped on " + floorName + ".";
        }

        return new FloorResult(cleared, totalDamage, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            System.out.println("The party finds a small health potion.");
            for (Hero hero : party) {
                if (hero.isAlive()) hero.heal(10);
            }
        }
    }

    private boolean anyAlive(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) return true;
        }
        return false;
    }
}
