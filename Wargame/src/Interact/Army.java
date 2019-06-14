package Interact;

import java.util.Random;
import java.util.ArrayList;
import Actors.*;

public class Army {

    public Actor actorRandomHuman() {
        Random rd = new Random();
        int type = rd.nextInt(3);
        int id = rd.nextInt(3);
        int wep;
        Actor a = null;
        switch (type) {
            case 0:
                wep = rd.nextInt(ActorMeleeHuman.getWepNum());
                int id1 = rd.nextInt(ActorMeleeHuman.getIdNum());
                a = new ActorMeleeHuman(id1, wep);
                break;
            case 1:
                wep = rd.nextInt(ActorRangedHuman.getWepNum());
                int id2 = rd.nextInt(ActorRangedHuman.getIdNum());
                a = new ActorRangedHuman(id2, wep);
                break;
            case 2:
                int at = rd.nextInt(ActorMagicHuman.getAtNum());
                int id3 = rd.nextInt(ActorMagicHuman.getIdNum());
                a = new ActorMagicHuman(id3, at);
                break;
        }
        return a;
    }

    public Actor actorRandomMonster() {
        Random rd = new Random();
        int type = rd.nextInt(3);
        int wep;
        Actor a = null;
        switch (type) {
            case 0:
                wep = rd.nextInt(ActorMeleeMonster.getWepNum());
                int id1 = rd.nextInt(ActorMeleeMonster.getIdNum());
                a = new ActorMeleeMonster(id1, wep);
                break;
            case 1:
                wep = rd.nextInt(ActorRangedMonster.getWepNum());
                int id2 = rd.nextInt(ActorRangedMonster.getIdNum());
                a = new ActorRangedMonster(id2, wep);
                break;
            case 2:
                int at = rd.nextInt(ActorMagicMonster.getAtNum());
                int id3 = rd.nextInt(ActorMagicMonster.getIdNum());
                a = new ActorMagicMonster(id3, at);
                break;
        }
        return a;
    }

    public ArrayList<Actor> actorRandomHumanArmy(int point){
        ArrayList<Actor> humanArmy = new ArrayList<>();
        while (point >=0){
            Actor randomHuman = actorRandomHuman();
            point-=randomHuman.getPoint();
        }
        return humanArmy;
    }

    public ArrayList<Actor> actorRandomMonsterArmy(int point){
        ArrayList<Actor> monsterArmy = new ArrayList<>();
        while (point >=0){
            Actor randomMonster = actorRandomMonster();
            point-=randomMonster.getPoint();
        }
        return monsterArmy;
    }
}
