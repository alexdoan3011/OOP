
/*
* Name: Van Nam Doan
* Student ID: 040943291
* Course & Section: CST8132 312
* Assignment: Lab 2
* Date: Jan 15, 2019
*/
import java.text.DecimalFormat;

public class ExerciseOne {
	private int[] myArray;
	private DecimalFormat df = new DecimalFormat("0,000.");

	public ExerciseOne() {
		myArray = new int[10];// creating a new instance of myArray
		for (int i = 1; i < 11; i++)
			myArray[i - 1] = i;// creating array content
	}

	public void printArrayValues() {
		System.out.print("myArray = {");
		for (int i = 0; i < 10; i++) {
			System.out.print(myArray[i]);// printing elements of array
			if (i != 9)
				System.out.print(",");// putting , between elements of array
		}
		System.out.println("};");
	}

	public void displayArrayProduct() {
		int product = 1;// instance variable to calculate product
		for (int element : myArray)
			product *= element;
		System.out.println("The product of all elements of myArray is " + df.format(product));
	}

	public static void main(String arg[]) {
		ExerciseOne exercise1 = new ExerciseOne();
		exercise1.printArrayValues();
		exercise1.displayArrayProduct();
	}
}
