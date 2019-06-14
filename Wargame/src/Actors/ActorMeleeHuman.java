package Actors;

public class ActorMeleeHuman extends Actor {
    private static String[] charName = {"Mercenary"};
    private static String[][] charWep = {{"Sword", "War Hammer"}};
    private static final int side = 0;
    private int[][] wepAp = {{2, 2}};
    private int[][] wepAtt = {{10, 10}};
    private int wep;
    private int point = 10;
    private String name = Names.getHumanName();

    public ActorMeleeHuman(int id, int wep) {
        this.id = id;
        getCharHP();
        getCharAp();
        getCharAtt();
        this.disarmed = false;
        this.wep = wep;
        this.type = 0;
    }

    public int getPoint() {
        return point;
    }

    public static int getIdNum() {
        return charName.length;
    }

    public static int getWepNum() {
        return charWep[0].length;
    }

    public String printCharName() {
        return charName[id];
    }

    public int getWepAtt() {
        if (!disarmed) {
            return wepAtt[id][wep];
        } else {
            return 0;
        }
    }

    public int getWepAp() {
        if (!disarmed) {
            return wepAp[id][wep];
        } else {
            return 0;
        }
    }

    public String printCharWep() {
        return charWep[id][wep];
    }

    public void setDisarmed() {
        if (!charWep[id][wep].isEmpty())
            disarmed = true;
        disarmedWep.add(side + "" + id + "" + wep);
    }

    public boolean setRearmed() {
        for (int i = 0; i < charWep[id].length; i++) {
            if (disarmedWep.contains(side + "" + id + "" + i)) {
                this.wep = i;
                System.out.println();
                System.out.println(printFullCharName() + " picked up a " + charWep[id][i] + " on the battlefield. " + printFullCharName() + " is no longer [Disarmed]");
                disarmedWep.remove(side + "" + id + "" + i);
                disarmed = false;
                return true;
            }
        }
        return false;
    }

    public String printFullCharName() {
        String fullCharName;
        String fullCharName2;
        fullCharName = printStatus();
        if (!fullCharName.isEmpty()) {
            fullCharName = fullCharName + " ";
        }
        if (!disarmed) {
            fullCharName2 = fullCharName + printCharWep();
            if (!fullCharName2.equals(fullCharName)) {
                fullCharName2 = fullCharName2 + " wielding ";
            }
            fullCharName2 += printCharName();
        } else {
            fullCharName2 = fullCharName + printCharName();
        }
        fullCharName2 += " " + name;
        return fullCharName2;
    }
}

