import java.util.ArrayList;

public class Program {
    public static void main(String... args) {
        InputResolution.input();
        System.out.print("\tAddress:\t\t");
        printAddressIntArray(InputResolution.getAddressIntArray());
        System.out.print("/");
        System.out.println(InputResolution.getMask());
        System.out.print("\tClass:\t\t\t");
        System.out.println(InputResolution.getNetClass());
        System.out.print("\tMask:\t\t\t");
        printAddressIntArray(AddressResolution.maskIntToIntArray(InputResolution.getMask()));
        System.out.println();
        switch (InputResolution.getNetClass()) {
            case "A":
            case "B":
            case "C":
                System.out.print("\tDefault mask:\t");
                printAddressIntArray(AddressResolution.maskIntToIntArray(InputResolution.getDefaultMaskInt()));
                System.out.println();
                System.out.print("\tBits borrowed: \t");
                System.out.println(InputResolution.getMask() - InputResolution.getDefaultMaskInt());

                System.out.println("\tIP address in binary: ");
                printAddressBinaryIntArray(AddressResolution.addressIntArrayToBinaryArray(InputResolution.getAddressIntArray()));
                System.out.println();
                System.out.println("\tIP address for broadcast: ");
                printAddressIntArray(AddressResolution.addressBinaryArrayToIntArray(binaryBroadcast()));
                System.out.println();
                System.out.println("\tIP address for broadcast in binary: ");
                printAddressBinaryIntArray(binaryBroadcast());
                System.out.println();
                System.out.println("\tIP address for network: ");
                printAddressIntArray(AddressResolution.addressBinaryArrayToIntArray(binaryNetwork()));
                System.out.println();
                System.out.println("\tIP address for network in binary: ");
                printAddressBinaryIntArray(binaryNetwork());
                System.out.println();
                System.out.println("\tIP address for first host: ");
                ArrayList<Integer> firstHostIntArray = AddressResolution.addressBinaryArrayToIntArray(binaryNetwork());
                firstHostIntArray.set(3, firstHostIntArray.get(3) + 1);
                printAddressIntArray(firstHostIntArray);
                System.out.println();
                System.out.println("\tIP address for last host: ");
                ArrayList<Integer> lastHostIntArray = AddressResolution.addressBinaryArrayToIntArray(binaryBroadcast());
                lastHostIntArray.set(3, lastHostIntArray.get(3) - 1);
                printAddressIntArray(lastHostIntArray);
                System.out.println();
                System.out.print("Subnet number: " + subnetNumber());
                break;
        }
    }

    public static void printAddressBinaryIntArray(ArrayList<Integer> binary) {
        for (int i = 0; i < binary.size(); i++) {
            if (i == 8 || i == 16 || i == 24) {
                System.out.print(".");
            }
            System.out.print(binary.get(i));
        }
    }

    public static void printAddressIntArray(ArrayList<Integer> addressIntArray) {
        int dotCount = 0;
        for (int i = 0; i < 4; i++) {
            System.out.print(addressIntArray.get(i));
            dotCount++;
            if (dotCount < 4) {
                System.out.print(".");
            }
        }
    }

    public static ArrayList<Integer> binaryBroadcast() {
        ArrayList<Integer> binaryBroadcast;
        binaryBroadcast = AddressResolution.addressIntArrayToBinaryArray(InputResolution.getAddressIntArray());
        for (int i = 31; i >= InputResolution.getMask(); i--) {
            binaryBroadcast.set(i, 1);
        }
        return binaryBroadcast;
    }

    public static ArrayList<Integer> binaryNetwork() {
        ArrayList<Integer> binaryNetwork;
        binaryNetwork = AddressResolution.addressIntArrayToBinaryArray(InputResolution.getAddressIntArray());
        for (int i = 31; i >= InputResolution.getMask(); i--) {
            binaryNetwork.set(i, 0);
        }
        return binaryNetwork;
    }

    static int subnetNumber() {
        int subnetNumber;
        ArrayList<Integer> sIDBinary = new ArrayList<>();
        for (int i = InputResolution.getDefaultMaskInt(); i < InputResolution.getMask(); i++) {
            sIDBinary.add(AddressResolution.addressIntArrayToBinaryArray(InputResolution.getAddressIntArray()).get(i));
        }
        subnetNumber = AddressResolution.BinaryToInt(sIDBinary) + 1;
        return subnetNumber;
    }
}
