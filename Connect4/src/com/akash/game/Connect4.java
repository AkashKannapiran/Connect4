package com.akash.game;

import java.util.Objects;
import java.util.Scanner;

public class Connect4 {

    String[][] board;
    Boolean winner;
    Boolean draw;
    int winningPlayer;
    int playerTurn;

    public Connect4() {
        winningPlayer = 0;
        draw = false;
        playerTurn = 1;
        board = new String[6][7];
        newBoard();
        displayBoard();
    }

    private void newBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = " - ";
            }
        }
    }

    private void displayBoard() {
        System.out.println("\n***   CONNECT 4   ***\n");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean validInput(String input) {
        return (Objects.equals(input, "1") ||
                Objects.equals(input, "2") ||
                Objects.equals(input, "3") ||
                Objects.equals(input, "4") ||
                Objects.equals(input, "5") ||
                Objects.equals(input, "6") ||
                Objects.equals(input, "7"));
    }

    private boolean isColumnFull(int column) {
        return (board[0][column - 1] == " X " || board[0][column - 1] == " O ");
    }

    private int getNextAvailableSlot(int column) {
        int position = 5;
        boolean found = false;
        while (position >= 0 && !found) {
            if (!Objects.equals(board[position][column], " X ") && !Objects.equals(board[position][column], " O ")) {
                found = true;
            } else {
                position--;
            }
        }
        return position;
    }

    private void swapPlayerTurn() {
        if (playerTurn == 1) {
            playerTurn = 2;
        } else {
            playerTurn = 1;
        }
    }

    private void placePiece() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Player " + playerTurn + " - place select which column to place your piece(1-7):");
        String input = scan.nextLine();

        while (!validInput(input) || isColumnFull(Integer.parseInt(input))) {
            while (!validInput(input)) {
                System.out.println("Invalid input - please select column from 1-7");
                input = scan.nextLine();
            }
            while (isColumnFull(Integer.parseInt(input))) {
                System.out.println("Column full, choose another column");
                input = scan.nextLine();
                if (!validInput(input)) {
                    break;
                }
            }
        }
        int columnChoice = Integer.parseInt(input) - 1;

        System.out.println("Next available row in column: " + getNextAvailableSlot(columnChoice));

        String pieceToPlace;
        if (playerTurn == 1) {
            pieceToPlace = " X ";
        } else {
            pieceToPlace = " O ";
        }
        board[getNextAvailableSlot(columnChoice)][columnChoice] = pieceToPlace;
        displayBoard();
        swapPlayerTurn();
    }

    private boolean isBoardFull() {
        boolean full = true;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] != " X " && board[i][j] != " O ") {
                    full = false;
                }
            }
        }
        return full;
    }

    private String checkVerticalWinner() {
        String symbol = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if ((board[i][j] == board[i + 1][j]) && (board[i][j] == board[i + 2][j]) && (board[i][j] == board[i+3][j])) {
                    if (board[i][j] == " X " || board[i][j] == " O ") {
                        symbol = board[i][j];
                    }
                }
            }
        }
        return symbol;
    }

    private String checkHorizontalWinner() {
        String symbol = null;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if ((board[i][j] == board[i][j + 1]) && (board[i][j] == board[i][j + 2]) && (board[i][j] ==board[i][j+3])) {
                    if (board[i][j] == " X " || board[i][j] == " O ") {
                        symbol = board[i][j];
                    }
                }
            }
        }
        return symbol;
    }

    private String checkLeftDiagonalWinner() {
        String symbol = null;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if ((board[i][j] == board[i + 1][j + 1]) && (board[i][j] == board[i + 2][j + 2]) && (board[i][j] ==board[i+3][j+3])) {
                    if (board[i][j] == " X " || board[i][j] == " O ") {
                        symbol = board[i][j];
                    }
                }
            }
        }
        return symbol;
    }

    private String checkRightDiagonalWinner() {
        String symbol = null;

        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                if ((board[i][j] == board[i + 1][j - 1]) && (board[i][j] == board[i + 2][j - 2]) && board[i][j] == board[i+3][j-3]) {
                    if (board[i][j] == " X " || board[i][j] == " O ") {
                        symbol = board[i][j];
                    }
                }
            }
        }
        return symbol;
    }

    private int checkForWinner() {
        int winner = 0;
        String symbol = " ";

        if (checkVerticalWinner() == " X " || checkVerticalWinner() == " O ") {
            symbol = checkVerticalWinner();
        }
        else if (checkHorizontalWinner() == " X " || checkHorizontalWinner() == " O ") {
            symbol = checkHorizontalWinner();
        }
        else if (checkLeftDiagonalWinner() == " X " || checkLeftDiagonalWinner() == " O ") {
            symbol = checkLeftDiagonalWinner();
        }
        else if (checkRightDiagonalWinner() == " X " || checkRightDiagonalWinner() == " O ") {
            symbol = checkRightDiagonalWinner();
        }

        if (symbol == " X "){
            winner = 1;
        } else if (symbol == " O ") {
            winner =2;
        }

        return winner;
    }

    private boolean checkForDraw(){
        return (isBoardFull() == true && (checkForWinner() != 1 && checkForWinner() != 2));
    }

    private void showWinner(){
        System.out.println("PLAYER: " + winningPlayer + " WINS !!!");
    }

    public void playGame(){

        while (winningPlayer == 0){
            winningPlayer = checkForWinner();
            draw = checkForDraw();

            if (winningPlayer == 1){
                showWinner();
                break;
            } else if (winningPlayer == 2) {
                showWinner();
                break;
            } else if (draw == true) {
                System.out.println("Its a draw!!");
                break;
            }
            placePiece();
        }
    }

    public static void main(String[] args) {

        Connect4 c4 = new Connect4();
        c4.playGame();
    }
}
