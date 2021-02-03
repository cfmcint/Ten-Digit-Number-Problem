import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author Clara
 * @version 1.0 2/21/2020
 *
 * Creates 10 digit number problem
 * A Java algorithm and a UI created with the JavaFX graphics library which both verifies a user imputed ten-digit number and finds a unique ten-digit number where each sub number is divisible by its length (ex. 38165/5 = 7633). 
 */
public class Lab5 extends Application {
    private TextField uInput = new TextField("");
    private Label message = new Label("");
    private Label tenDigNum = new Label("");
    private int divis = 0;
    private Label number = new Label("");

    /**
     * @param field a textField that the user enters the number they want to check into
     * @param newVal the new value the user has input into the textField
     *
     * Makes sure the value the user is trying to enter into the textField is an integer
     */
    private static void checkInt (TextField field, String newVal) {
        field.setText(field.getText().trim());
        if (!newVal.matches("\\d*")) {
            field.setText(newVal.replaceAll("[^\\d]", ""));
        }
    }

    /**
     * Makes sure the user entered number cannot be longer than 10 characters
     * Calculates whether the user entered number is properly divisible and change a label to represent the findings
     */
    private void intActions () {
        uInput.textProperty().addListener((obs, oldVal, newVal) -> {
            checkInt(uInput, newVal);

            if (!uInput.getText().equals("")) {
                if (uInput.getText().length() > 10) {
                    uInput.setText(oldVal);
                }
            }
        });

        uInput.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                if (uInput.getText().equals("")) {
                    uInput.setText("0");
                }

                divisible();

                if (divis == uInput.getText().length()) {
                    message.setText("YES");
                } else {
                    message.setText("NO: " + uInput.getText().substring(0, divis + 1) + " is not divisible by " + (divis + 1));
                }
            }
        });
    }

    /**
     * checks if a user entered number is divisible by all of its parts
     */
    private void divisible () {
        int i;

        divis = 0;

        for (i = 0; i < uInput.getText().length(); i++) {
            if ((Long.parseLong(uInput.getText().substring(0, i+1)) % (i + 1) == 0) && (divis == i))divis = i + 1;
        }
    }

    /**
     * calls the methods to make part one function
     */
    private void part1() {
        uInput.setPromptText("Enter an integer");
        intActions();
    }

    /**
     * @param num a long which is the current long being checked for correct divisibility
     * @return the length of the long num
     *
     * Finds and returns the length of long num
     */
    private int length(long num) {
        int temp = 1;
        int length = 0;

        while (temp < num) {
            length ++;
            temp *= 10;
        }

        return length;
    }

    /**
     * @param num a long which is the current long being checked for correct divisibility
     * @return returns true if the long num could be divided by its last digit
     *
     * checks if the long num can be divided without remainder by its last digit
     */
    private boolean check(long num) {
        boolean divid = true;
        int length;

        length = length(num);

        if (num % length != 0) divid = false;

        return divid;
    }

    /**
     * @param num a long which is the current long being checked for correct divisibility
     * @param newNum an integer which is the last digit of long num
     * @return the boolean unique which is true if all of the digits in long num are unique
     *
     * Checks whether int newNum is already in long num
     */
    private boolean integerIn(long num, int newNum) {
        String sNum = Long.toString(num);
        Character sNewNum = Integer.toString(newNum).charAt(0);
        boolean unique = true;
        int i;

        for (i = 0; i < sNum.length()-1; i++) {
            if (sNewNum.equals(sNum.charAt(i))) unique = false;
        }

        return unique;
    }

    /**
     * @param num a long which is the current long being checked for correct divisibility
     * @param add the number being added to the end of the long num
     *
     * This method is called if num is unique and divisible without a remainder
     * If the length of long num is not 9, 9 is added to the end of num and part2 is recalled
     * If the length of long num is 9 0 is added to it and the label for the 10 digit number is changed
     */
    private void works (long num, int add) {
        if (length(num) < 9) {
            add = 9;
            num = num * 10 + add;
            part2(num, add);
        } else {
            number.setText(Long.toString(num * 10));
            part2(((num / 10) * 10) + ((num % 10) - 1), (int)((num % 10) - 1));
        }
    }

    /**
     * @param num a long which is the current long being checked for correct divisibility
     * @param add the number being added to the end of the long num
     *
     * This method is called if the last digit of long num is not unique or not divisible and add = 1
     * It recalls part two but num is now one place smaller and the last digit and add are 9
     */
    private void moveBackPlace (long num, int add) {
        num = (num / 10) - 1;
        if (num % 10 == 0) num = (num / 10) - 1;
        if (num == 1 ) {
            add = 9;
            num = 19;
        }
        else add = (int) num % 10;
        part2(num, add);
    }

    /**
     * @param num a long which is the current long being checked for correct divisibility
     * @param add the number being added to the end of the long num
     *
     * This method uses recursion and backtracking to start at number 9 and find all of the unique digit 10 digit numbers that each sub-number is divisible by its length
     */
    private void part2(long num, int add) {
        boolean uniq = true;
        boolean div;

        if (num != 123) {
            div = check(num);

            if (num > 10) {
                uniq = integerIn(num, add);
            } else {
                uniq = true;
            }

            if (div && uniq) {
                works (num, add);
            } else if ((!uniq && add > 1) || (uniq && add > 1)) {
                num = ((num / 10) * 10) + (add - 1);
                part2(num, add - 1);
            } else if (add < 2) {
                moveBackPlace(num, add);
            }
        }
    }

    /**
     * prints out proof that sub-number of the 10 digit unique numbers are divisible by their lengths
     */
    private void print() {
        String numb = number.getText();
        int i;

        for (i = 1; i < 11; i++) {
            number.setText(number.getText() + "\n" + numb.substring(0,i) + " / " + i + " = " + Long.parseLong(numb) / i);
        }
    }

    /**
     * @param args arguments
     *
     * main method which launches the program
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param myStage stage which the scene is set on
     *
     * Calls methods for parts 1 and 2, sets up formatting
     */
    public void start(Stage myStage) {
        myStage.setTitle("Ten Digit Number Problem");

        Label title = new Label("Enter an integer to check if every sub-number from the left is divisible by its size");
        VBox rootNode = new VBox(20);
        Scene myScene = new Scene(rootNode, 500, 630);
        myStage.setScene(myScene);

        rootNode.setAlignment(Pos.CENTER);
        rootNode.setPadding(new Insets(10,30,10,30));
        title.setWrapText(true);
        number.setTextAlignment(TextAlignment.CENTER);

        part1();
        part2(9, 8);

        tenDigNum.setText("The ten digit numbers are: ");
        print();

        rootNode.getChildren().addAll(title, uInput, message, tenDigNum, number);
        myStage.show();
    }
}
