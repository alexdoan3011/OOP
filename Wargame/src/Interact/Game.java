package Interact;

import Actors.*;

import java.util.Random;

public class Game {
    public static void main(String[] args) {
        Actor[] one = new Actor[4];
        Actor[] two = new Actor[one.length];
        Random rd = new Random();
        Army army = new Army();
        for (int i = 0; i < one.length; i++) {
            one[i] = army.actorRandomHuman();
            for (int j = 0; j < 2; j++)
                if (chanceGenerator() <= 50)
                    one[i].addStt(rd.nextInt(Actor.getStatusNum()));
            if (chanceGenerator() <= 50)
                one[i].setDisarmed();
        }
        for (int i = 0; i < two.length; i++) {
            two[i] = army.actorRandomMonster();
            for (int j = 0; j < 2; j++)
                if (chanceGenerator() <= 50)
                    two[i].addStt(rd.nextInt(Actor.getStatusNum()));
            if (chanceGenerator() <= 50)
                two[i].setDisarmed();
        }
        for (int i = 0; i < one.length; i++) {
            Battle.printAttack(one[i], two[i]);
            Battle.attack(one[i],two[i]);
        }
        for (int i = 0; i < one.length; i++) {
            if (one[i].isDisarmed()) {
                ((ActorMeleeHuman) one[i]).setRearmed();
            }
        }
        for (int i = 0; i < two.length; i++) {
            if (two[i].isDisarmed()) {
                ((ActorMeleeMonster) two[i]).setRearmed();
            }
        }
    }

    public static int chanceGenerator() {
        Random rd = new Random();
        int chance = rd.nextInt(100) + 1;
        return chance;
    }


}

