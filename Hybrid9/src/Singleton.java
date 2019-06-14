/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Hybrid 9
 * Date: Apr 13, 2019
 */

/**
 * This class implements the Singleton design pattern
 *
 * @author Svillen Ranev
 */
public class Singleton {
    /**
     * number of singleton to be created
     */
    private static final int numberOfSingleton = 10;
    /**
     * The reference to an array of instance of the class
     */
    private static Singleton[] arrayOfSingleton = new Singleton[numberOfSingleton];// array of singletons, which should be an array of reference to the same object


    /**
     * Private constructor - instances of this class
     * can not be created directly by calling the constructor.
     */
    private Singleton() {
        System.out.println("Singleton constructor called ...\n");
    }

    /**
     * Accessor class method responsible for creating
     * and maintaining an unique instance
     */
    public static Singleton[] getSingleton() {
        for (int i = 0; i < arrayOfSingleton.length; i++) {
            if (arrayOfSingleton[i] == null) {
                arrayOfSingleton[i] = new Singleton();
            }
        }
        return arrayOfSingleton;
    }//end class

    /**
     * Overrides Object's toString()
     */
    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
//Q1. Do design patterns in object-oriented design help avoiding the “reinvention of the wheel” in programming?
//A: Yes, design patterns in object-oriented design help avoiding the “reinvention of the wheel” in programming.
//Q2. Can the Singleton design pattern ensure that a class has only one single, unique instance?
//A: Yes, the Singleton design pattern ensure that a class has only one single, unique instance by forbidding access to the constructor and implementing an accessor that returns a reference to the unique instance.