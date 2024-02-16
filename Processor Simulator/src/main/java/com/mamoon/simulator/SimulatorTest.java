
package com.mamoon.simulator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimulatorTest extends Application {
    private Button button;
    private TextField inputField;
    private TextField outputField;
    private Label inputText;
    private Label outputText;
    private static final Simulator simulator = Simulator.getInstance();
    public static void main(String[] args) {
        /*
        //in case black screen working
        Scanner sc=new Scanner(System.in);
        System.out.println("enter input file with .xlsx");
        String inputPath=sc.nextLine();
        System.out.println("enter output file with .xlsx");
        String outPath=sc.nextLine();
        sc.close();

        simulator.readExcel(inputPath);
        while (!simulator.isSimulationDone())
        {
            simulator.simulation();
        }
        simulator.writeToExcel(outPath);
        System.out.println(simulator);//in implementation

    */
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simulator");
        initialize();
        button.setOnAction(event -> {
            try{startSimulating();}
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        });
        VBox layout =new VBox(20);
        layout.getChildren().addAll(inputText,inputField,outputText,outputField,button);
        prepareScene(primaryStage, layout);
    }

    private void prepareScene(Stage primaryStage, VBox layout) {
        Scene scene=new Scene(layout,300,250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startSimulating() {
        String inputPath=inputField.getText();
        String outPath=outputField.getText();
        simulator.readExcel(inputPath);
        while (!simulator.isSimulationDone()) {
            simulator.simulation();
        }
        simulator.writeToExcel(outPath);
        button.setDisable(true);
        System.exit(0);
    }

    private void initialize() {
        button =new Button();
        button.setText("start");
        inputField=new TextField("Input.xlsx");
        outputField=new TextField("Output.xlsx");
        inputText=new Label("Input file .xlsx");
        outputText=new Label("Output file .xlsx");
    }
}


