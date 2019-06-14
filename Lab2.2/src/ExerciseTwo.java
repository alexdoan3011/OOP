/*
* Name: Van Nam Doan
* Student ID: 040943291
* Course & Section: CST8132 312
* Assignment: Lab 2
* Date: Jan 15, 2019
*/
public class ExerciseTwo {
	private int[][] myArray;

	public ExerciseTwo() {
		myArray = new int[6][10];// creating new instance of myArray
		for (int i = 0; i < 6; i++)
			for (int j = 1; j < 11; j++)
				myArray[i][j - 1] = (10 * i) + j;// creating array content
	}

	public void printArrayValues() {
		System.out.println("myArray = {");
		for (int i = 0; i < 6; i++) {
			System.out.print("{");
			for (int j = 0; j < 10; j++) {
				System.out.print(myArray[i][j]);// printing elements of array
				if (j != 9)
					System.out.print(",");// putting , between elements of array
			}
			System.out.print("}");// line break
			if (i != 5)
				System.out.print(",");// putting , between each line
			System.out.println();
		}
		System.out.println("};");
	}

	public void displayArrayTotal() {
		int total = 0;// instance variable to calculate sum
		for (int[] row : myArray)
			for (int element : row)
				total += element;
		System.out.println("The sum of all elements of myArray is " + total + ".");
	}

	public static void main(String arg[]) {
		ExerciseTwo exercise2 = new ExerciseTwo();
		exercise2.printArrayValues();
		exercise2.displayArrayTotal();
	}
}
