package com.narxoz.rpg;

import com.narxoz.rpg.combatant.*;
import com.narxoz.rpg.floor.*;
import com.narxoz.rpg.state.*;
import com.narxoz.rpg.tower.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Hero knight = new Hero("Mega Knight", 120, 18, 15);
        Hero warlock = new Hero("Dark Warlock", 90, 22, 5, new PoisonedState(2));

        List<Hero> party = new ArrayList<>();
        party.add(knight);
        party.add(warlock);

        List<TowerFloor> floors = new ArrayList<>();
        floors.add(new TrapFloor("Spike Corridor", 15));
        floors.add(new RestFloor("Abandoned Chapel", 25));
        floors.add(new CombatFloor("Guard Room", new Monster("Skeleton Guard", 100, 15)));
        floors.add(new CombatFloor("Tower Summit", new Monster("Wraith Lord", 180, 17)));

        TowerRunner runner = new TowerRunner(floors);

        System.out.println("========== PARTY ==========");
        for (Hero hero : party) {
            System.out.println("  " + hero.getName() + " | HP: " + hero.getHp() + "/" + hero.getMaxHp() + " | ATK: " + hero.getAttackPower() + " | DEF: " + hero.getDefense() + " | State: " + hero.getState().getName());
        }
        System.out.println("===========================");

        TowerRunResult result = runner.run(party);

        System.out.println("========== TOWER RUN COMPLETE ==========");
        System.out.println("Floors cleared : " + result.getFloorsCleared() + " / " + floors.size());
        System.out.println("Heroes surviving: " + result.getHeroesSurviving());
        System.out.println("Reached the top : " + (result.isReachedTop() ? "Yes" : "No"));
        System.out.println("========================================");
    }
}

