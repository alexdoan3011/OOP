import java.util.ArrayList;

public class AddressResolution {

    public static ArrayList<Integer> maskIntToIntArray(int mask) {
        ArrayList<Integer> maskIntArray = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            maskIntArray.add(0);
        }
        if (mask < 9) {
            for (int i = 7; i >= 8 - mask; i--) {
                maskIntArray.set(0, (int) (maskIntArray.get(0) + Math.pow(2, i)));
            }
        } else if (mask < 17) {
            maskIntArray.set(0, 255);
            for (int i = 7; i >= 16 - mask; i--) {
                maskIntArray.set(1, (int) (maskIntArray.get(1) + Math.pow(2, i)));
            }
        } else if (mask < 25) {
            maskIntArray.set(0, 255);
            maskIntArray.set(1, 255);
            for (int i = 7; i >= 24 - mask; i--) {
                maskIntArray.set(2, (int) (maskIntArray.get(2) + Math.pow(2, i)));
            }
        } else {
            maskIntArray.set(0, 255);
            maskIntArray.set(1, 255);
            maskIntArray.set(2, 255);
            for (int i = 7; i >= 32 - mask; i--) {
                maskIntArray.set(3, (int) (maskIntArray.get(3) + Math.pow(2, i)));
            }
        }
        return maskIntArray;
    }


    public static ArrayList<Integer> addressIntArrayToBinaryArray(ArrayList<Integer> addressIntArray) {
        ArrayList<Integer> binaryAddress = new ArrayList<>();
        for (int part : addressIntArray) {
            ArrayList<Integer> sizeToEight = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                sizeToEight.add(0);
            }
            for (int i = 0; i < IntToBinary(part).size(); i++) {
                sizeToEight.add(IntToBinary(part).get(i));
                sizeToEight.remove(0);
            }
            binaryAddress.addAll(sizeToEight);
        }
        return binaryAddress;
    }

    public static ArrayList<Integer> addressBinaryArrayToIntArray(ArrayList<Integer> addressBinaryArray) {
        ArrayList<Integer> addressIntArray = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            addressIntArray.add(0);
        }
        for (int i = 0; i < 8; i++) {
            if (addressBinaryArray.get(i) == 1) {
                addressIntArray.set(0, (int) (addressIntArray.get(0) + Math.pow(2, 7 - i)));
            }
        }
        for (int i = 8; i < 16; i++) {
            if (addressBinaryArray.get(i) == 1) {
                addressIntArray.set(1, (int) (addressIntArray.get(1) + Math.pow(2, 7 - (i - 8))));
            }
        }
        for (int i = 16; i < 24; i++) {
            if (addressBinaryArray.get(i) == 1) {
                addressIntArray.set(2, (int) (addressIntArray.get(2) + Math.pow(2, 7 - (i - 16))));
            }
        }
        for (int i = 24; i < 32; i++) {
            if (addressBinaryArray.get(i) == 1) {
                addressIntArray.set(3, (int) (addressIntArray.get(3) + Math.pow(2, 7 - (i - 24))));
            }
        }
        return addressIntArray;
    }

    public static int maskIntArrayToInt(ArrayList<Integer> maskIntArray) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if (AddressResolution.addressIntArrayToBinaryArray(maskIntArray).get(i) == 1) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    static ArrayList<Integer> IntToBinary(int integer) {
        ArrayList<Integer> tempBinary = new ArrayList<>();
        ArrayList<Integer> binary = new ArrayList<>();
        while (integer > 0) {
            tempBinary.add(integer % 2);
            integer = integer / 2;
        }
        for (int i = 0; i < tempBinary.size(); i++) {
            binary.add(tempBinary.get(tempBinary.size() - i - 1));
        }
        return binary;
    }

    static int BinaryToInt(ArrayList<Integer> binary) {
        int integer = 0;
        for (int i = binary.size(); i > 0; i--) {
            if (binary.get(i - 1) == 1) {
                integer += Math.pow(2, binary.size() - i);
            }
        }
        return integer;
    }
}
