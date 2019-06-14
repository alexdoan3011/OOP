package Actors;

import java.util.ArrayList;
import java.util.Random;

public class Names {
    protected static String[] humanName1 = {"Al", "Day", "Der", "Au", "Jay", "Ro", "Ry", "Ben", "Ki", "Nik", "Loki", "Ea", "Eri", "O", "Vao", "Siki", "Af", "Bog", "Dao", "Pi"};
    protected static String[] humanName2 = {"done", "ne", "ril", "gus", "me", "berd", "lan", "tom", "ran", "las", "yen", "si", "as", "rar", "bis", "re", "ze", "yoa", "vof", "li"};
    protected static ArrayList<String> humanName = new ArrayList<>();

    public static String getHumanName() {
        boolean repeated = true;
        String name;
        while (repeated) {
            int rnd1 = new Random().nextInt(humanName1.length);
            int rnd2 = new Random().nextInt(humanName2.length);
            name = (humanName1[rnd1] + humanName2[rnd2]);
            if (!humanName.contains(name)){
                humanName.add(name);
                return name;
            }
        }
        return "with no name";
    }

}
