/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 9
 * Date: Apr 9, 2019
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * run the application, set parameters for the application window
 *
 * @author Van Nam Doan
 * @version 1.2
 * @since 1.8
 */
public class Assign2 extends Application {
    /**
     * launch program
     *
     * @param args command line option
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * set root, create new scene, set title for the application
     *
     * @param primaryStage stage for the main window
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Lab9.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 590, 400);
            primaryStage.setTitle("Bank Portal");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(610);
            primaryStage.setMinHeight(440);
            primaryStage.show();
            final Controller currentController = loader.getController();// get the controller for the current stage
            primaryStage.setOnCloseRequest(e -> {// does not allow user to close it directly
                e.consume();
                currentController.confirmation(primaryStage);// call confirmation dialog
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
