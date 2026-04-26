package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.*;

import java.util.*;

public class TowerRunner {

    private final List<TowerFloor> floors;

    public TowerRunner(List<TowerFloor> floors) {
        this.floors = floors;
    }

    public TowerRunResult run(List<Hero> party) {
        int floorsCleared = 0;

        for (TowerFloor floor : floors) {
            if (!anyAlive(party)) break;

            FloorResult result = floor.explore(party);
            System.out.println("Result: " + result.getSummary());

            if (result.isCleared()) floorsCleared++;

            else break;
        }

        int surviving = countAlive(party);
        boolean reachedTop = (floorsCleared == floors.size());

        return new TowerRunResult(floorsCleared, surviving, reachedTop);
    }

    private boolean anyAlive(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) return true;
        }
        return false;
    }

    private int countAlive(List<Hero> party) {
        int count = 0;
        for (Hero hero : party) {
            if (hero.isAlive()) count++;
        }
        return count;
    }
}
