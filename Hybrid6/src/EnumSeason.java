class EnumSeason {
    /**
     * The main method
     */
    public static void main(String[] args) {
//create a reference variable of type Season
        Season season = Season.WINTER;
//convert the command line argument string to enum constant
        if (args.length == 1)
            season = Enum.valueOf(Season.class, args[0]);
        else
            season = Season.WINTER;
//enum types can be compared with ==, no need to use equals()
        if (season == Season.WINTER & season.equals(Season.WINTER))
            System.out.println("No command line argument provided.");
//enum variable can be used as a switch control variable
        switch (season) {
            case WINTER:
                System.out.println(season + " brings cold");
                break;
            case SPRING:
                System.out.println(season + " brings hope");
                break;
            case SUMMER:
                System.out.println(season + " brings joy");
                break;
            case AUTUMN:
                System.out.println(season + " brings colors");
        }
//create an array containing all of the Season constants
        Season[] as = Season.values();
//Print the array using enhanced for loop ("for each" loop)
        System.out.println("The names of the Season constants are:");
        for (Season s : as)
            System.out.println(s);
        calculating();
    }// end main

    public static void calculating() {
        int result = 0;
        Calculator calculator;
        for (int i = 0; i < Calculator.values().length; i++) {
            calculator = Calculator.values()[i];
            switch (calculator) {
                case PLUS:
                    result = 1 + 1;
                    break;
                case MINUS:
                    result = 1 - 1;
                    break;
                case MULT:
                    result = 1 * 1;
                    break;
                case DIV:
                    result = 1 / 1;
                    break;
            }
            System.out.println("one " + calculator.toString().toLowerCase() + " equals " + result);
            System.out.println("1 " + calculator.returnSign() + " 1 = " + result);
        }
    }

    /**
     * defines enum data type for seasons
     */
    public enum Season {
        WINTER, SPRING, SUMMER, AUTUMN
    }

    /**
     * defines enum data type for calculations
     */
    public enum Calculator {
        PLUS("+"), MINUS("-"), MULT("*"), DIV("/");
        private final String sign;

        Calculator(String sign) {
            this.sign = sign;
        }

        String returnSign() {
            return sign;
        }
    }
}// end class