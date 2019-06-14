import javax.swing.*;
import java.awt.*;

public class SwingDemo {
    public static void main(String... args){
        JFrame frame = new JFrame();
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");
        JButton button4 = new JButton("Button 4");
        JButton button5 = new JButton("Button 5");
        frame.getContentPane().add(button1, BorderLayout.NORTH);
        button1.addActionListener(e->displayButton(1));
        button2.addActionListener(e->displayButton(2));
        button3.addActionListener(e->displayButton(3));
        button4.addActionListener(e->displayButton(4));
        frame.getContentPane().add(button2, BorderLayout.SOUTH);
        frame.getContentPane().add(button3, BorderLayout.WEST);
        frame.getContentPane().add(button4, BorderLayout.EAST);
        frame.getContentPane().add(button5, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
    public static void displayButton(int i){
        JOptionPane.showMessageDialog(null, "Button " + i + " pressed");
    }
}
