package Actors;

public class ActorMagicMonster extends Actor {
    private static final int side = 0;
    private static String[] charName = {"Shaman"};
    private static String[] charAttribute = {"Water", "Fire", "Flora", "Earth", "Frost"};
    private static int atNum = 5;
    private int at;
    private String[][] charMagic = {{"Armor Piercing Rain", "Molten Shower", "Maneater Bite", "Rock Grenade", "Frostfire Salvo"}};
    private int[][] atAtt = {{10, 10, 10, 10, 10}};
    private int[][] atAp = {{2, 2, 2, 2, 2}};
    private int[][] wepAp = {{2, 2}};
    private int[][] wepAtt = {{10, 10}};
    private int point = 10;

    public ActorMagicMonster(int id, int at) {
        this.id = id;
        this.at = at;
        getCharHP();
        getCharAp();
        getCharAtt();
        this.disarmed = false;
        this.type = 2;
    }

    public int getPoint() {
        return point;
    }

    public static int getIdNum() {
        return charName.length;
    }

    public static int getAtNum() {
        return atNum;
    }

    public String printCharAttribute() {
        return charAttribute[at];
    }

    public String printFullCharName() {
        String fullCharName;
        fullCharName = printStatus();
        if (!fullCharName.isEmpty()) {
            fullCharName = fullCharName + " ";
        }
        return fullCharName + printCharAttribute() + " " + printCharName();
    }

    public String printCharMagic() {
        return charMagic[id][at];
    }

    public String printCharName() {
        return charName[id];
    }

    public int getWepAtt() {
        if (!disarmed) {
            return atAtt[id][at];
        } else {
            return 0;
        }
    }

    public int getWepAp() {
        if (!disarmed) {
            return atAp[id][at];
        } else {
            return 0;
        }
    }

    public void setDisarmed() {
    }

}