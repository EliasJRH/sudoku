package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Sudoku {

    public static JFrame game = new JFrame("Sudoku"); //Class for the frame of the program
    public static GridLayout grid = new GridLayout(3, 3); //grid layout specifically for the board
    public static GridLayout buttonLayout = new GridLayout(3, 3); //gird layout for the buttons
    public static GridLayout labelLayout = new GridLayout(3, 3); //grid layout for the labels inside the board
    public static JPanel gridPanel = new JPanel(); //overall game board
    public static JButton newBoard = new JButton("New Board"); //button to reset the game and add different numbers
    public static JLabel gap1 = new JLabel("Sudoku V1, By: Elias H", SwingConstants.CENTER); //gap for the top of the screen
    public static JPanel gap2 = new JPanel(); //gap on the left side
    public static JPanel gap3 = new JPanel(); //gap on the right side and also the panel that holds the buttons
    public static JPanel buttonGrid = new JPanel(); //panel to hold the buttons
    public static JButton Button1 = new JButton("1"); //buttons to select a specific number and the clear a cell
    public static JButton Button2 = new JButton("2");
    public static JButton Button3 = new JButton("3");
    public static JButton Button4 = new JButton("4");
    public static JButton Button5 = new JButton("5");
    public static JButton Button6 = new JButton("6");
    public static JButton Button7 = new JButton("7");
    public static JButton Button8 = new JButton("8");
    public static JButton Button9 = new JButton("9");
    public static JButton clearCell = new JButton("Clear Cell");
    public static JPanel A1 = new JPanel(); //The 9 3x3 panels
    public static JPanel A2 = new JPanel();
    public static JPanel A3 = new JPanel();
    public static JPanel B1 = new JPanel();
    public static JPanel B2 = new JPanel();
    public static JPanel B3 = new JPanel();
    public static JPanel C1 = new JPanel();
    public static JPanel C2 = new JPanel();
    public static JPanel C3 = new JPanel();
    public static int mins = 0, secs = 0, msecs = 0; //int and strings to hold the minute second and millisecond timer values
    public static String Smins = "", Ssecs = "", Smsecs = "";
    public static boolean timerOn = false; //boolean to check if the timer is on or off, this determines whether the time is counting or not
    public static JLabel timerLabel = new JLabel("00.00.0000"); //jlabel to hold the timer
    public static Timer t = new Timer(); //Timer
    public static TimerTask counter = new TimerTask() {
        public void run() {
            if (timerOn) {
                msecs++;
                if (msecs == 1000) {
                    secs++;
                    msecs = 0000;
                }
                if (secs == 60) {
                    mins++;
                    secs = 00;
                }
            }
            Smins = String.valueOf(mins);
            Ssecs = String.valueOf(secs);
            Smsecs = String.valueOf(msecs);
            if (mins < 10){
                Smins = "0" + mins;
            }
            if (secs < 10){
                Ssecs = "0" + secs;
            }
            if (msecs < 10){
                Smsecs = "00" + msecs;
            }else if (msecs >= 10 && msecs <= 99){
                Smsecs = "0" + msecs;
            }
            timerLabel.setText(Smins + "." + Ssecs + "." + Smsecs);

        }
    }; //Timer task for the counter timer
    public static JLabel[] AllLabels = new JLabel[81]; //Creating all the jlabels and giving them their properties
    public static boolean[] labelBooleans = new boolean[81];
    public static boolean[] labelSelected = new boolean[81];
    public static Border blackline = BorderFactory.createLineBorder(Color.black);
    public static int[][] board = new int[9][9]; //array for the board
    public static int[][] boardToSolve = new int[9][9];
    public static boolean boardFilled = false; //boolean to check if the array is full

    public static void main(String[] args) {
        initializeBoard(); //creates the board
        initializeLabels(); //organizes the labels and panels
        resetGame(); //sets all the booleans to false
        resetBoard(); //calls a method to fill the board with zeroes
        fillBoard(0, 0); //calls the first fillBoard method at the top left corner of the board
        findGaps(); // Calls the method to find the gaps after the initial board was made
        transferBoard();
        printBoard();
        randomRemoval(); //Calls the method to randomly remove numbers
        fillBoxes(); // Calls the method to fill the boxes of the board
        runTimer();
    } //Main method to start the game

    public static void initializeBoard() {
        //Setting up the frame
        game.setVisible(true);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setLayout(new BorderLayout());
        game.setResizable(false);
        ////////////////////////////////
        //Properties of the board
        gridPanel.setBorder(blackline);
        gridPanel.setLayout(grid);
        grid.setHgap(10);
        grid.setVgap(10);
        gridPanel.add(A1);
        gridPanel.add(A2);
        gridPanel.add(A3);
        gridPanel.add(B1);
        gridPanel.add(B2);
        gridPanel.add(B3);
        gridPanel.add(C1);
        gridPanel.add(C2);
        gridPanel.add(C3);
        gridPanel.setBackground(Color.BLACK);
        gridPanel.setVisible(true);
        ////////////////////////////////
        //Using JPanels to make the grid in the center of the screen
        game.add(gap1, BorderLayout.NORTH);
        gap1.setPreferredSize(new Dimension(0, 40));
        game.add(newBoard, BorderLayout.SOUTH);
        newBoard.setPreferredSize(new Dimension(0, 80));
        newBoard.addActionListener(new NewGameAction());
        game.add(gap2, BorderLayout.WEST);
        gap2.setPreferredSize(new Dimension(10, 0));
        game.add(gap3, BorderLayout.EAST);
        gap3.setPreferredSize(new Dimension(120, 0));
        ////////////////////////////////
        //Adding the buttons
        gap3.setLayout(new BorderLayout());
        JPanel gap3Top = new JPanel();
        JPanel gap3Bot = new JPanel();
        JPanel gap3Buttons = new JPanel();
        gap3.add(gap3Top, BorderLayout.NORTH);
        gap3Top.setPreferredSize(new Dimension(0, 120));
        gap3Top.add(timerLabel);
        gap3.add(gap3Bot, BorderLayout.SOUTH);
        gap3Bot.setPreferredSize(new Dimension(0, 120));
        gap3.add(gap3Buttons, BorderLayout.CENTER);
        gap3Buttons.setLayout(buttonLayout);
        gap3Buttons.add(Button1);
        Button1.addActionListener(new SetOne());
        gap3Buttons.add(Button2);
        Button2.addActionListener(new SetTwo());
        gap3Buttons.add(Button3);
        Button3.addActionListener(new SetThree());
        gap3Buttons.add(Button4);
        Button4.addActionListener(new SetFour());
        gap3Buttons.add(Button5);
        Button5.addActionListener(new SetFive());
        gap3Buttons.add(Button6);
        Button6.addActionListener(new SetSix());
        gap3Buttons.add(Button7);
        Button7.addActionListener(new SetSeven());
        gap3Buttons.add(Button8);
        Button8.addActionListener(new SetEight());
        gap3Buttons.add(Button9);
        Button9.addActionListener(new SetNine());
        gap3Bot.add(clearCell);
        clearCell.addActionListener(new Clear());
        game.add(gridPanel, BorderLayout.CENTER);
        game.setPreferredSize(new Dimension(800, 800));
        ////////////////////////////////
        game.pack();
        game.setLocationRelativeTo(null);
    } //Method to create and organize the board

    public static void initializeLabels() {
        //Setting layouts and background colors for 3x3 boxes
        A1.setLayout(labelLayout);
        A1.setBackground(Color.BLACK);
        A2.setLayout(labelLayout);
        A2.setBackground(Color.BLACK);
        A3.setLayout(labelLayout);
        A3.setBackground(Color.BLACK);
        B1.setLayout(labelLayout);
        B1.setBackground(Color.BLACK);
        B2.setLayout(labelLayout);
        B2.setBackground(Color.BLACK);
        B3.setLayout(labelLayout);
        B3.setBackground(Color.BLACK);
        C1.setLayout(labelLayout);
        C1.setBackground(Color.BLACK);
        C2.setLayout(labelLayout);
        C2.setBackground(Color.BLACK);
        C3.setLayout(labelLayout);
        C3.setBackground(Color.BLACK);
        //setting horizontal and vertical gap
        labelLayout.setHgap(5);
        labelLayout.setVgap(5);
        for (int i = 0; i < AllLabels.length; i++) { //for loop to add properties to all labels
            AllLabels[i] = new JLabel("", SwingConstants.CENTER); //sets their text at the center
            AllLabels[i].setFont(new Font("Verdana", Font.BOLD, 18)); //font
            AllLabels[i].setOpaque(true); //makes them opaque
            AllLabels[i].setForeground(Color.black); //font color is black
            AllLabels[i].setBorder(blackline); //gives them a black outline
            final int ref = i; //make i a final variable
            AllLabels[i].addMouseListener(new MouseAdapter() { //Adds mouselistener to each jlabel
                @Override
                public void mousePressed(MouseEvent e) { //When the certain jlabel is pressed
                    if (labelSelected[ref]) {
                        if (labelBooleans[ref] == false) { //it will turn blue if its boolean is false
                            for (int j = 0; j < AllLabels.length; j++) { //changes all other label back to white
                                if (j != ref) {
                                    AllLabels[j].setBackground(Color.white);
                                    labelBooleans[j] = false; //sets their booleans as false
                                }
                            }
                            AllLabels[ref].setBackground(Color.green);
                            labelBooleans[ref] = true;
                        } else { //it will turn back to white if it's boolean is fal
                            AllLabels[ref].setBackground(Color.white);
                            labelBooleans[ref] = false;
                        }
                    }
                }
            });
        }
        for (int i = 0; i < 81; i++) { //for loop that adds all the labels to the central panel
            if (i < 27) {
                if ((i <= 2) || (i >= 9 && i <= 11) || (i >= 18 && i <= 20)) {
                    A1.add(AllLabels[i]);
                } else if ((i >= 3 && i <= 5) || (i >= 12 && i <= 14) || (i >= 21 && i <= 23)) {
                    A2.add(AllLabels[i]);
                } else if ((i >= 6 && i <= 8) || (i >= 15 && i <= 17) || (i >= 24 && i <= 26)) {
                    A3.add(AllLabels[i]);
                }
            } else if (i < 54) {
                if ((i >= 27 && i <= 29) || (i >= 36 && i <= 38) || (i >= 45 && i <= 47)) {
                    B1.add(AllLabels[i]);
                } else if ((i >= 30 && i <= 32) || (i >= 39 && i <= 41) || (i >= 48 && i <= 50)) {
                    B2.add(AllLabels[i]);
                } else if ((i >= 33 && i <= 35) || (i >= 42 && i <= 44) || (i >= 51 && i <= 53)) {
                    B3.add(AllLabels[i]);
                }
            } else {
                if ((i >= 54 && i <= 56) || (i >= 63 && i <= 65) || (i >= 72 && i <= 74)) {
                    C1.add(AllLabels[i]);
                } else if ((i >= 57 && i <= 59) || (i >= 66 && i <= 68) || (i >= 75 && i <= 77)) {
                    C2.add(AllLabels[i]);
                } else if ((i >= 60 && i <= 62) || (i >= 69 && i <= 71) || (i >= 78 && i <= 80)) {
                    C3.add(AllLabels[i]);
                }
            }
        }

    } //Method to create and organize the labels

    public static void resetGame() {
        for (int i = 0; i < labelBooleans.length; i++) {
            labelBooleans[i] = false;
            labelSelected[i] = false;
        }
    } //method to reset all label booleans

    static class NewGameAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            resetBoard(); //calls a method to fill the board with zeroes
            resetGame(); //resets all the booleans
            boardFilled = false; //sets the boardfilled boolean to false so it can remake the board
            fillBoard(0, 0); //calls the first fillBoard method at the top left corner of the board
            findGaps(); // Calls the method to find the gaps after the initial board was made
            transferBoard();
            randomRemoval(); //removes numbers randomly
            fillBoxes(); // fills all the boxes with their corresponding numbers
            timerOn = false;
            timerLabel.setText("00.00.0000");
            mins = 0;
            secs = 0;
            msecs = 0;
        }
    } //action listener to reset the board

    static class SetOne implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < labelBooleans.length; i++) {
                if (labelBooleans[i]) {
                    AllLabels[i].setBackground(Color.white);
                    AllLabels[i].setText("1");
                    break;
                }
            }
            if (timerOn == false) {
                timerOn = true;
            }
            if (checkForFullBoard()) {
                checkForWin();
            }
        }
    } //action listeners to add numbers to cells

    static class SetTwo implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < labelBooleans.length; i++) {
                if (labelBooleans[i]) {
                    AllLabels[i].setBackground(Color.white);
                    AllLabels[i].setText("2");
                    break;
                }
            }
            if (timerOn == false) {
                timerOn = true;
            }
            if (checkForFullBoard()) {
                checkForWin();
            }
        }
    }

    static class SetThree implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < labelBooleans.length; i++) {
                if (labelBooleans[i]) {
                    AllLabels[i].setBackground(Color.white);
                    AllLabels[i].setText("3");
                    break;
                }
            }
            if (timerOn == false) {
                timerOn = true;
            }
            if (checkForFullBoard()) {
                checkForWin();
            }
        }
    }

    static class SetFour implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < labelBooleans.length; i++) {
                if (labelBooleans[i]) {
                    AllLabels[i].setBackground(Color.white);
                    AllLabels[i].setText("4");
                    break;
                }
            }
            if (timerOn == false) {
                timerOn = true;
            }
            if (checkForFullBoard()) {
                checkForWin();
            }
        }
    }

    static class SetFive implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < labelBooleans.length; i++) {
                if (labelBooleans[i]) {
                    AllLabels[i].setBackground(Color.white);
                    AllLabels[i].setText("5");
                    break;
                }
            }
            if (timerOn == false) {
                timerOn = true;
            }
            if (checkForFullBoard()) {
                checkForWin();
            }
        }
    }

    static class SetSix implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < labelBooleans.length; i++) {
                if (labelBooleans[i]) {
                    AllLabels[i].setBackground(Color.white);
                    AllLabels[i].setText("6");
                    break;
                }
            }
            if (timerOn == false) {
                timerOn = true;
            }
            if (checkForFullBoard()) {
                checkForWin();
            }
        }
    }

    static class SetSeven implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < labelBooleans.length; i++) {
                if (labelBooleans[i]) {
                    AllLabels[i].setBackground(Color.white);
                    AllLabels[i].setText("7");
                    break;
                }
            }
            if (timerOn == false) {
                timerOn = true;
            }
            if (checkForFullBoard()) {
                checkForWin();
            }
        }
    }

    static class SetEight implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < labelBooleans.length; i++) {
                if (labelBooleans[i]) {
                    AllLabels[i].setBackground(Color.white);
                    AllLabels[i].setText("8");
                    break;
                }
            }
            if (timerOn == false) {
                timerOn = true;
            }
            if (checkForFullBoard()) {
                checkForWin();
            }
        }
    }

    static class SetNine implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < labelBooleans.length; i++) {
                if (labelBooleans[i]) {
                    AllLabels[i].setBackground(Color.white);
                    AllLabels[i].setText("9");
                    break;
                }
            }
            if (timerOn == false) {
                timerOn = true;
            }
            if (checkForFullBoard()) {
                checkForWin();
            }
        }
    }

    static class Clear implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < labelBooleans.length; i++) {
                if (labelBooleans[i]) {
                    AllLabels[i].setBackground(Color.white);
                    AllLabels[i].setText("");
                    break;
                }
            }
        }
    } //action listener to clear a cell

    public static boolean fillBoard(int rowNum, int columnNum) {
        int number; //the number that will be random
        ArrayList<Integer> numsTried = new ArrayList<Integer>(); //an arraylist of numbers tried for a specific slot
        number = (int) (Math.random() * ((9 - 1) + 1) + 1); //a random number is generated from 1-9
        while (boardFilled == false) { //while the board still isn't full
            if (checkRows(number, columnNum) && checkColumns(number, rowNum) && check3x3(number, rowNum, columnNum)) {
                //It will check if the number generated can properly fit the slot
                //If it can, it will put it into that board slot
                board[rowNum][columnNum] = number;
                if (checkIfFull()) {// checks if the board is full
                    boardFilled = true;
                    break;
                }
                //Calls the method again to fill the next board
                if (columnNum != 8) { //if it's not at the end of the board
                    fillBoard(rowNum, columnNum + 1); //call the function for the next column over
                } else { //if it is at the end of the board
                    fillBoard(rowNum + 1, 0); //call the function for the first column at the next row
                }
            }
            if (!numsTried.contains(number)) { //if the number isn't in the called method, numsTried array list then it adds it to it
                //Then it adds it to it
                numsTried.add(number);
            }
            if (numsTried.size() == 9) { //if the size of the arraylist is 9 meaning that every number has been tried
                board[rowNum][columnNum] = 0; //it sets the slot in the board back to zero
                numsTried.clear(); //clears the array list
                return (false); //and returns back to the previous function call
                //this way I'm able to backtrack through the board and find numbers that will work together
            }
            number = (int) (Math.random() * ((9 - 1) + 1) + 1); //generates a new random number at the end of this while loop
        }
        return (true); //returns true when this is done
    } //Recursive method that is used to fill the board

    public static boolean checkRows(int number, int column) {
        for (int rowScanner = 0; rowScanner < board.length; rowScanner++) { //for every row at the column
            if (number == board[rowScanner][column]) { //if the number is the same as any other
                return (false); //return false
            }
        }
        return (true); //otherwise this will return true
    } //Method to check the rows at the indexed column

    public static boolean checkColumns(int number, int row) {
        for (int columnScanner = 0; columnScanner < board[row].length; columnScanner++) { //for every column at the row
            if (number == board[row][columnScanner]) { //if the number is the same as any other in the row
                return (false); //return false
            }
        }
        return (true); //otherwise this will return true
    } //Method to check the columns at the indexed row

    public static boolean check3x3(int number, int row, int column) {
        if (row <= 2) { //figures out the row and column that the point it is in
            if (column <= 2) { //A1
                for (int rowNum = 0; rowNum < 3; rowNum++) { //loops throught the 3x3 block to check if the number exists there
                    for (int columnNum = 0; columnNum < 3; columnNum++) {
                        if (board[rowNum][columnNum] == number) {
                            return (false); //if so, return false
                        }
                    }
                }
            } else if (column >= 3 && column <= 5) { //A2
                for (int rowNum = 0; rowNum < 3; rowNum++) {
                    for (int columnNum = 3; columnNum < 6; columnNum++) {
                        if (board[rowNum][columnNum] == number) {
                            return (false);
                        }
                    }
                }
            } else if (column >= 6 && column <= 8) { //A3
                for (int rowNum = 0; rowNum < 3; rowNum++) {
                    for (int columnNum = 6; columnNum < 9; columnNum++) {
                        if (board[rowNum][columnNum] == number) {
                            return (false);
                        }
                    }
                }
            }
        } else if (row >= 3 && row <= 5) {
            if (column <= 2) { //B1
                for (int rowNum = 3; rowNum < 6; rowNum++) {
                    for (int columnNum = 0; columnNum < 3; columnNum++) {
                        if (board[rowNum][columnNum] == number) {
                            return (false);
                        }
                    }
                }
            } else if (column >= 3 && column <= 5) { //B2
                for (int rowNum = 3; rowNum < 6; rowNum++) {
                    for (int columnNum = 3; columnNum < 6; columnNum++) {
                        if (board[rowNum][columnNum] == number) {
                            return (false);
                        }
                    }
                }
            } else if (column >= 6 && column <= 8) { //B3
                for (int rowNum = 3; rowNum < 6; rowNum++) {
                    for (int columnNum = 6; columnNum < 9; columnNum++) {
                        if (board[rowNum][columnNum] == number) {
                            return (false);
                        }
                    }
                }
            }
        } else if (row >= 6 && row <= 8) {
            if (column <= 2) { //C1
                for (int rowNum = 6; rowNum < 9; rowNum++) {
                    for (int columnNum = 0; columnNum < 3; columnNum++) {
                        if (board[rowNum][columnNum] == number) {
                            return (false);
                        }
                    }
                }
            } else if (column >= 3 && column <= 5) { //C2
                for (int rowNum = 6; rowNum < 9; rowNum++) {
                    for (int columnNum = 3; columnNum < 6; columnNum++) {
                        if (board[rowNum][columnNum] == number) {
                            return (false);
                        }
                    }
                }
            } else if (column >= 6 && column <= 8) { //C3
                for (int rowNum = 6; rowNum < 9; rowNum++) {
                    for (int columnNum = 6; columnNum < 9; columnNum++) {
                        if (board[rowNum][columnNum] == number) {
                            return (false);
                        }
                    }
                }
            }
        }
        return (true); //otherwise this will return true
    } //Method to check the 3x3 box at the index row and column

    public static void resetBoard() {
        for (int rowNum = 0; rowNum < board.length; rowNum++) { //for every index in the board
            for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) {
                board[rowNum][columnNum] = 0; //make it zero
            }
        }
    } //Method to reset the board and fill it with zeores

    public static boolean checkIfFull() {
        for (int rowNum = 0; rowNum < board.length; rowNum++) { //for every index in the board
            for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) {
                if (board[rowNum][columnNum] == 0) { //if there exists a zero that means the board isn't full
                    return (false); //return false
                }
            }
        }
        return (true); //other return true
    } //Method to check if the board is full

    public static void findGaps() {
        for (int rowNum = 0; rowNum < board.length; rowNum++) { //for every index in the board
            for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) {
                if (board[rowNum][columnNum] == 0) {  //if there is a zero
                    fillGap(rowNum, columnNum); //calls the fillgap method passing through the row and column number
                }
            }
        }
    } //Method to find the gaps that the above functions may have left

    public static void fillGap(int row, int column) {
        int r = row; //gets the row and column that was passed through
        int c = column;
        ArrayList<Integer> numsUsed = new ArrayList<Integer>(); //creates an array list of ints used
        for (int i = 1; i < 10; i++) {
            numsUsed.add(i); //adds 1-9
        }
        for (int columnNum = 0; columnNum < board[r].length; columnNum++) { //check the row at that index
            if (numsUsed.contains(board[r][columnNum])) {  //if a number exists in the array list
                numsUsed.remove(Integer.valueOf(board[r][columnNum])); //it gets removed
            }
        }
        for (int rowNum = 0; rowNum < board.length; rowNum++) { //check the column at that index
            if (numsUsed.contains(board[rowNum][c])) { //if a number exists in the array list
                numsUsed.remove(Integer.valueOf(board[rowNum][c])); //it gets removed
            }
        }
        board[row][column] = numsUsed.get(0); //adds the last remaining number to that slot
    } //Method to fill gaps

    public static void randomRemoval() {
        int numsToRemove = (int) (Math.random() * ((40 - 30) + 1) + 30);
        int numsRemoved = 0;
        int row, column;
        while (numsRemoved != numsToRemove) {
            row = (int) (Math.random() * ((8) + 1));
            column = (int) (Math.random() * ((8) + 1));
            board[row][column] = 0;
            numsRemoved++;
        }
    } //Method that randomly removes numbers from the board

    public static void fillBoxes() {
        int i = 0;
        for (int j = 0; j < AllLabels.length; j++) {
            AllLabels[j].setText(""); //resets all the labels
        }
        for (int rowNum = 0; rowNum < board.length; rowNum++) { //for every cell in the board
            for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) {
                if (board[rowNum][columnNum] != 0) { //if the number is not zero
                    AllLabels[i].setForeground(Color.black); //sets the forground color to black
                    AllLabels[i].setText(String.valueOf(board[rowNum][columnNum])); //sets the jlabel to whatever the number is
                } else { //otherwise
                    AllLabels[i].setForeground(Color.blue); //change it to green so the player knows its a changing number
                    labelSelected[i] = true; //make it activated so can be changed
                }
                i++;
            }
        }
    } //Method that fills the cells

    public static void checkForWin() {
        int i = 0;
        boolean win = true;
        for (int rowNum = 0; rowNum < boardToSolve.length; rowNum++) {
            for (int columnNum = 0; columnNum < boardToSolve[rowNum].length; columnNum++) {
                if (Integer.valueOf(AllLabels[i].getText()) != boardToSolve[rowNum][columnNum]) {
                    win = false;
                    break;
                }
                i++;
            }
        }
        if (win) {
            timerOn = false;
            JOptionPane.showMessageDialog(null, "You have won! \n"
                    + "Time: " + mins + "." + secs + "." + msecs);
        }

    } //Method that checks for a complete and correct board

    public static void transferBoard() {
        for (int rowNum = 0; rowNum < board.length; rowNum++) {
            for (int columnNum = 0; columnNum < board[rowNum].length; columnNum++) {
                boardToSolve[rowNum][columnNum] = board[rowNum][columnNum];
            }
        }
    } //Method that transfers the boar used to check for a win

    public static boolean checkForFullBoard() {
        for (int i = 0; i < AllLabels.length; i++) {
            if (AllLabels[i].getText().equals("")) {
                return (false);
            }
        }
        return (true);
    } //Method to check if the board is full

    public static void runTimer(){
        t.scheduleAtFixedRate(counter,0,1);
    }
    
    public static void printBoard() {
        for (int rowNum = 0; rowNum < boardToSolve.length; rowNum++) { //for every index
            for (int columnNum = 0; columnNum < boardToSolve[rowNum].length; columnNum++) {
                System.out.print(boardToSolve[rowNum][columnNum]); //print it
            }
            System.out.println(""); //print a new line when finished printing the row
        }
    } //Method to print the board

}
