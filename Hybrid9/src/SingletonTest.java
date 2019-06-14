/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Hybrid 9
 * Date: Apr 13, 2019
 */
/**
 * This is a test unit class for the Singleton implementation
 *
 * @author Svillen Ranev
 */
public class SingletonTest {

    public static void main(String[] args) {
        Singleton[] arrayOne, arrayTwo,arrayThree;
        arrayOne = Singleton.getSingleton();// array of singletons, which should be an array of reference to the same object
        arrayTwo = Singleton.getSingleton();// array of singletons, which should be an array of reference to the same object
        arrayThree = Singleton.getSingleton();// array of singletons, which should be an array of reference to the same object

        for (int i = 0; i < arrayOne.length; i++) {
            System.out.println("arrayOne[" + i + "] is a reference to " + arrayOne[i]);
        }
        for (int i = 0; i < arrayTwo.length; i++) {
            System.out.println("arrayTwo[" + i + "] is a reference to " + arrayTwo[i]);
        }
        for (int i = 0; i < arrayThree.length; i++) {
            System.out.println("arrayThree[" + i + "] is a reference to " + arrayThree[i]);
        }
   /* use operator == to compare objects.For non-Singleton classes the result will be false.
      comparing references produces true only and only when the objects occupy the same memory space
   */
        if (arrayOne == arrayTwo&&arrayTwo==arrayThree)
            System.out.println("\nThe references in the array arrayOfSingleton refer to one unique object of type " + arrayOne[0]);
    }
}
//Q1. Do design patterns in object-oriented design help avoiding the “reinvention of the wheel” in programming?
//A: Yes, design patterns in object-oriented design help avoiding the “reinvention of the wheel” in programming.
//Q2. Can the Singleton design pattern ensure that a class has only one single, unique instance?
//A: Yes, the Singleton design pattern ensure that a class has only one single, unique instance by forbidding access to the constructor and implementing an accessor that returns a reference to the unique instance.
