/*
* Name: Van Nam Doan
* Student ID: 040943291
* Course & Section: CST8132 312
* Assignment: Lab 1
* Date: Jan 09, 2019
*/
public class Cst8132DoanVanNamLab1 {
	public static void main(String arg[]) {
		int line=5;												//number of lines for each triangle
		for (int i=1;i<=(line*4+3);i++) {
			for (int j=1;j<=line;j++) {
				if (i<line&&j<=line-i)							//first pattern
					System.out.print(" ");
				else if (i<=line)
					System.out.print("$");
				if ((i>line+1&&i<=line*2+1)&&j<i-line)			//second pattern
					System.out.print("$");
				if ((i>line*2+2&&i<=line*3+2)&&j<i-line*2-2)	//third pattern
					System.out.print(" ");
				else if (i>line*2+2&&i<=line*3+2)
					System.out.print("$");
				if (i>line*3+3&&j<=line*4+4-i)					//forth pattern
					System.out.print("$");
				if (j==line&&i!=(line*4+3))						//line breaking, but not at the end
					System.out.println();
			}
		}
	}
}
