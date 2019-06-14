
/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 8
 * Date: April 14th, 2019
 */
import java.awt.*;

/**
 * this class launches the application
 *
 * @author Van Nam Doan
 * @version 1.3
 * @since 1.8
 */
public class TipCalculator {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TipCalculatorFrame frame = new TipCalculatorFrame();
					frame.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
