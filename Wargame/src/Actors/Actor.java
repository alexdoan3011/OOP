package Actors;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Actor {
    protected static ArrayList<String> disarmedWep = new ArrayList<>();
    protected static String[] status = {"", "[Weakened]", "[Burning]", "[Slowed]", "[Crippled]", "[Frigid]", "[Bleeding]"};
    protected int side;
    protected int type;
    protected int id;
    protected int hp;
    protected int ap;
    protected int att;
    protected ArrayList<Integer> stt = new ArrayList<>();
    protected boolean disarmed;

    Actor() {
        this.disarmed = false;
    }

    public static ArrayList<String> getDisarmedWep() {
        return disarmedWep;
    }

    public static int getStatusNum() {
        return status.length;
    }

    public abstract int getPoint();

    public int getSide() {
        return side;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAp() {
        return ap;
    }

    public abstract String printCharName();

    public int getAtt() {
        return att;
    }

    public abstract int getWepAtt();

    public void getCharHP() {
        switch (side) {
            case 0:
                switch (type) {
                    case 0:
                        this.hp = 100;// mercenary hp
                        break;
                    case 1:
                        this.hp = 100;// archer hp
                        break;
                    case 2:
                        this.hp = 100;// magician hp
                        break;
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        this.hp = 100;// orc hp
                        break;
                    case 1:
                        this.hp = 100;// spitter hp
                        break;
                    case 2:
                        this.hp = 100;// shaman hp
                        break;
                }
                break;
        }
    }

    public boolean isDisarmed() {
        return disarmed;
    }

    public void getCharAp() {
        switch (side) {
            case 0:
                switch (type) {
                    case 0:
                        this.ap = 10;
                        break;
                    case 1:
                        this.ap = 10;
                        break;
                    case 2:
                        this.ap = 10;
                        break;
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        this.ap = 10;
                        break;
                    case 1:
                        this.ap = 10;
                        break;
                    case 2:
                        this.ap = 10;
                        break;
                }
                break;
        }
    }

    public ArrayList<Integer> getStt() {
        return stt;
    }

    public boolean addStt(int newStt) {
        if (stt.contains(newStt)) {
            return false;
        }
        stt.add(newStt);
        Collections.sort(stt);
        return true;
    }

    public String printStatus() {
        String sttprt = "";
        for (int stat : stt) {
            sttprt += status[stat];
        }
        if (disarmed)
            sttprt = "[Disarmed]" + sttprt;
        return sttprt;
    }

    public abstract void setDisarmed();

    public void getCharAtt() {
        switch (side) {
            case 0:
                switch (type) {
                    case 0:
                        att = 100;
                        break;
                    case 1:
                        att = 100;
                        break;
                    case 2:
                        att = 100;
                        break;
                }
                break;
            case 1:
                switch (type) {
                    case 0:
                        att = 100;
                        break;
                    case 1:
                        att = 100;
                        break;
                    case 2:
                        att = 100;
                        break;
                }
                break;
        }
        if (!disarmed)
            att += getWepAtt();
    }

    public abstract String printFullCharName();
}
