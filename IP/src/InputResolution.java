import java.util.ArrayList;
import java.util.Scanner;

class InputResolution {
    private static boolean firstInput = true;
    private static int mask;
    private static int defaultMaskInt;
    private static String netClass;
    private static ArrayList<Integer> addressIntArray = new ArrayList<>();
    private static ArrayList<Integer> maskIntArray = new ArrayList<>();


    static ArrayList<Integer> getAddressIntArray() {
        return addressIntArray;
    }

    static int getMask() {
        return mask;
    }

    static int getDefaultMaskInt() {
        return defaultMaskInt;
    }

    static String getNetClass() {
        return netClass;
    }

    static void input() {
        if (firstInput) {
            System.out.println("\tInput IP address(x.x.x.x or x.x.x.x/x): ");
        }
        Scanner input = new Scanner(System.in);
        String address = null;
        boolean valid = false;
        ArrayList<Integer> addressIntArray = new ArrayList<>();
        ArrayList<Integer> maskIntArray = new ArrayList<>();
        while (!valid) {
            valid = true;
            address = input.nextLine();
            if (!address.matches("((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))" +
                    "\\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))" +
                    "\\.((25[0-5])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[0-9]))" +
                    "\\.((25[0-4])|(2[0-4][0-9])|(1[0-9][0-9])|([1-9]?[1-9]))(/(3[0-2]|([12][0-9])|[1-9]))?")) {

                System.out.println("\tInvalid address. Please re-enter:");
                valid = false;
            }
        }
        for (String a : address.split("[\\./]")) {
            addressIntArray.add(Integer.parseInt(a));
        }
        if (addressIntArray.size() == 4) {
            System.out.println("\tPlease input the mask:");
            boolean validMask = false;
            String mask;
            while (!validMask) {
                maskIntArray = new ArrayList<>();
                validMask = true;
                mask = input.nextLine();
                try {
                    for (String a : mask.split("\\.")) {
                        maskIntArray.add(Integer.parseInt(a));
                    }
                    if (maskIntArray.get(0) > 0 && maskIntArray.get(1) == 0 && maskIntArray.get(2) == 0 && maskIntArray.get(3) == 0) {
                        if (maskIntArray.get(0) != 255 && maskIntArray.get(0) != 254 && maskIntArray.get(0) != 252 && maskIntArray.get(0) != 248 && maskIntArray.get(0) != 224 && maskIntArray.get(0) != 192 && maskIntArray.get(0) != 240 && maskIntArray.get(0) != 128) {
                            validMask = false;
                        }
                    } else if (maskIntArray.get(0) == 255 && maskIntArray.get(1) > 0 && maskIntArray.get(2) == 0 && maskIntArray.get(3) == 0) {
                        if (maskIntArray.get(1) != 255 && maskIntArray.get(1) != 254 && maskIntArray.get(1) != 252 && maskIntArray.get(1) != 248 && maskIntArray.get(1) != 224 && maskIntArray.get(1) != 192 && maskIntArray.get(1) != 240 && maskIntArray.get(1) != 128) {
                            validMask = false;
                        }
                    } else if (maskIntArray.get(0) == 255 && maskIntArray.get(1) == 255 && maskIntArray.get(2) > 0 && maskIntArray.get(3) == 0) {
                        if (maskIntArray.get(2) != 255 && maskIntArray.get(2) != 254 && maskIntArray.get(2) != 252 && maskIntArray.get(2) != 248 && maskIntArray.get(2) != 224 && maskIntArray.get(2) != 192 && maskIntArray.get(2) != 240 && maskIntArray.get(2) != 128) {
                            validMask = false;
                        }
                    } else if (maskIntArray.get(0) == 255 && maskIntArray.get(1) == 255 && maskIntArray.get(2) == 255 && maskIntArray.get(3) > 0) {
                        if (maskIntArray.get(3) != 255 && maskIntArray.get(3) != 254 && maskIntArray.get(3) != 252 && maskIntArray.get(3) != 248 && maskIntArray.get(3) != 224 && maskIntArray.get(3) != 192 && maskIntArray.get(3) != 240 && maskIntArray.get(3) != 128) {
                            validMask = false;
                        }
                    } else {
                        validMask = false;
                    }
                    if (!validMask) {
                        System.out.println("\tInvalid mask. Please re-enter:");
                    }
                } catch (Exception e) {
                    System.out.println("\tInvalid mask. Please re-enter:");
                    validMask = false;
                }
            }
            for (int i = 0; i < addressIntArray.size(); i++) {
                InputResolution.maskIntArray.add(maskIntArray.get(i));
            }
            InputResolution.mask = AddressResolution.maskIntArrayToInt(InputResolution.maskIntArray);
        } else {
            mask = addressIntArray.get(4);
        }
        for (int i = 0; i < 4; i++) {
            InputResolution.addressIntArray.add(addressIntArray.get(i));
        }
        if (InputResolution.addressIntArray.get(0) < 128) {
            defaultMaskInt = 8;
            netClass = "A";
        } else if (InputResolution.addressIntArray.get(0) < 192) {
            defaultMaskInt = 16;
            netClass = "B";
        } else if (InputResolution.addressIntArray.get(0) < 224) {
            defaultMaskInt = 24;
            netClass = "C";
        } else {
            defaultMaskInt = -1;
            if (InputResolution.addressIntArray.get(0) < 240) {
                netClass = "D";
            } else {
                netClass = "E";
            }
        }
        if (defaultMaskInt > InputResolution.mask) {
            firstInput = false;
            System.out.println("\tInvalid mask: Default mask is of higher value than mask entered. Please input valid IP address and mask:");
            InputResolution.input();
        }
    }
}
