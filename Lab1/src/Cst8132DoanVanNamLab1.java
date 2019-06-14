/*
* Name: Van Nam Doan // ensure you use YOUR name
* Student ID: 040943291 // ensure you use YOUR student ID number
* Course & Section: CST8132 312 // ensure you use YOUR lab section number
* Assignment: Lab 1
* Date: Jan 07, 2019 // can be due date or submission date
*/
public class Cst8132DoanVanNamLab1 {
	public static void main(String arg[]) {
		int line=5;							//number of lines for each triangle
		for (int i=0;i<line;i++) {			//first triangle
			for (int j=0;j<line-i-1;j++) {
				System.out.print(" ");
			}
			for (int j=0;j<=i;j++) {
				System.out.print("$");
			}
			System.out.println();			//line break after each print
		}
		System.out.println();				//empty line
		for (int i=0;i<line;i++) {			//second triangle
			for (int j=0;j<=i;j++) {
				System.out.print("$");
			}
			System.out.println();			//line break after each print
		}
		System.out.println();				//empty line
		for (int i=0;i<line;i++) {			//third triangle
			for (int j=0;j<=i-1;j++) {
				System.out.print(" ");
			}
			for (int j=0;j<line-i;j++) {
				System.out.print("$");
			}
			System.out.println();			//line break after each print
		}
		System.out.println();				//empty line
		for (int i=0;i<line;i++) {			//forth triangle
			for (int j=0;j<line-i;j++) {
				System.out.print("$");
			}
			if (i<line-1)
				System.out.println();		//line break after each print except the after the last triangle
		}
	}
}
