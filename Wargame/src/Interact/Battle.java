package Interact;

import Actors.Actor;
import Actors.ActorMagicHuman;

import java.util.Random;

public class Battle {
    public static int getAttackPoint(Actor a, Actor b) {
        int att = 0;
        att += a.getAtt();
        att *= attackPointFactor(a, b);
        return att;
    }

    public static double attackPointFactor(Actor a, Actor b) {
        double factor = 1;
        boolean criticalHit = false;
        for (int status : a.getStt()) {
            switch (status) {
                case 1:// weakened attacker, 75% damage
                    factor *= 0.75;
                    break;
                case 2:// burning attacker, 50% more chance of missing attacks
                    if (Game.chanceGenerator() <= 50) {
                        factor *= 0;
                    }
                    break;
                case 4:// crippled attacker, half damage, 30% more chance of missing attacks
                    factor *= 0.5;
                    if (Game.chanceGenerator() <= 30) {
                        factor *= 0;
                    }
                    break;
            }
        }
        for (int status : b.getStt()) {
            switch (status) {
                case 1:// weakened defender, 125% more damage
                    factor *= 1.25;
                    break;
                case 2:// burning defender, 20% more chance of attacks being missed
                    if (Game.chanceGenerator() <= 20) {
                        factor *= 0;
                    }
                    break;
                case 4:// crippled defender, 40% chance of critical hit - 300% damage
                    if (Game.chanceGenerator() <= 40 && factor < 3.0) {
                        criticalHit = true;
                        factor = 3;
                    }
                    break;
                case 5:// frigid defender, 10% chance of insta-kill
                    if (Game.chanceGenerator() <= 10) {
                        factor = 1000;
                    }
                    break;
            }
        }
        if (Game.chanceGenerator() <= 20 && !b.getStt().contains(3)) {// random 20% chance of missing attacks. No miss for slowed defender
            factor *= 0;
        }
        if (Game.chanceGenerator() <= 10 && !criticalHit) {// random 10% chance of critical hit for all attacks
            criticalHit = true;
            factor = 3;
        }
        if (factor == 0) {
            criticalHit = false;
        }
        if (criticalHit) {
            System.out.println("CRITICAL HIT!!!");
        }
        if (factor == 0) {
            System.out.println("MISSED!!!");
        }
        Random rd = new Random();
        double factorRandomization = 0.1 * rd.nextDouble();
        if (Game.chanceGenerator() <= 50) {
            factorRandomization *= -1;
        }
        factor = factor + factor * factorRandomization;
        return factor;
    }

    public static void attack(Actor a, Actor b) {
        int attackPoint;
        b.setHp(b.getHp() - (attackPoint = getAttackPoint(a, b)));
        System.out.println(b.printFullCharName() + " took " + attackPoint + " damage.");
        if (b.getHp() <= 0) {
            int statement = Game.chanceGenerator();
            if (statement <= 20) {
                System.out.println(b.printFullCharName() + " was no more.");
            } else if (statement <= 40) {
                System.out.println(b.printFullCharName() + " died.");
            } else if (statement <= 60) {
                System.out.println(b.printFullCharName() + " was defeated.");
            } else if (statement <= 80) {
                System.out.println(b.printFullCharName() + " bit the dust.");
            } else if (statement <= 100) {
                System.out.println(b.printFullCharName() + " collapsed.");
            }
        }
    }

        public static void printAttack (Actor a, Actor b){
            System.out.println();
            switch (a.getType()) {
                case 0:
                    int statement = Game.chanceGenerator();
                    if (statement <= 20) {
                        System.out.println(a.printFullCharName() + " charged at " + b.printFullCharName());
                    } else if (statement <= 40) {
                        System.out.println(a.printFullCharName() + " went to work on " + b.printFullCharName());
                    } else if (statement <= 60) {
                        System.out.println(a.printFullCharName() + " hit " + b.printFullCharName() + " repeatedly");
                    } else if (statement <= 80) {
                        System.out.println(a.printFullCharName() + " charged at " + b.printFullCharName());
                    } else if (statement <= 100) {
                        System.out.println(a.printFullCharName() + " charged at " + b.printFullCharName());
                    }
                    break;
                case 1:
                    System.out.println(a.printFullCharName() + " took a shot at " + b.printFullCharName());
                    break;
                case 2:
                    System.out.println(a.printFullCharName() + " casted " + ((ActorMagicHuman) a).printCharMagic() + " on " + b.printFullCharName());
                    break;
                default:
                    System.out.println("error");
                    System.out.println(a.getType());
                    break;
            }
        }
    }
