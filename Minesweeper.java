import java.util.Scanner;

enum Status {
    Pass, Flag, Blown, Won;
}

class Cell {
    private boolean mine;
    private boolean isOpened;
    private boolean isflagged;
    private int value;

    Cell() {
        mine = false;
        isOpened = false;
        isflagged = false;
        value = 0;
    }

    boolean getMine() {
        return mine;
    }

    void setMine() {
        mine = true;
    }

    void incValue() {
        value++;
    }

    int getValue() {
        return value;
    }

    boolean isflagged() {
        return isflagged;
    }

    void flag() {
        isflagged = true;
    }

    void unflag() {
        isflagged = false;
    }

    boolean isOpened() {
        return isOpened;
    }

    void openCell() {
        isOpened = true;

    }
}

public class Minesweeper {
    static final int B_SIZE = 5;
    private int r, c, opened;
    private char action;
    Scanner sc;
    private final Cell[][] field;
    private Status st;

    Minesweeper() {
        r = 0;
        c = 0;
        opened = 0;
        action = '\u0000';
        sc = new Scanner(System.in);
        field = new Cell[B_SIZE][B_SIZE];
        st = Status.Pass;
        for (int i = 0; i < B_SIZE; i++)
            for (int j = 0; j < B_SIZE; j++)
                field[i][j] = new Cell();
        initMines(field, 3);
    }

    void initMines(Cell[][] field, int count) {
        for (int i = 1; i <= count; i++)
            while (true) {
                int row = (int) (Math.random() * field.length);
                int col = (int) (Math.random() * field[0].length);
                if (!field[row][col].getMine()) {
                    field[row][col].setMine();
                    if (row > 0) {
                        field[row - 1][col].incValue();
                        if (col > 0)
                            field[row - 1][col - 1].incValue();
                        if (col < field[0].length - 1)
                            field[row - 1][col + 1].incValue();
                    }
                    if (row < field.length - 1) {
                        field[row + 1][col].incValue();
                        if (col > 0)
                            field[row + 1][col - 1].incValue();
                        if (col < field[0].length - 1)
                            field[row + 1][col + 1].incValue();
                    }
                    if (col > 0)
                        field[row][col - 1].incValue();
                    if (col < field[0].length - 1)
                        field[row][col + 1].incValue();

                    break;
                }
            }
    }

    void input() {
        System.out.println("Input coordinates and function 'O' : Open\t'F' : Flag\t'U' : Unflag" +
                "\nFor example \"1 1 O\"");
        String in = sc.nextLine().trim();
        while (true) {
            if (in.length() != 5 || in.charAt(1) != ' ' || in.charAt(3) != ' ') {
                System.out.println("Invalid Input! Try Again!");
                continue;
            }
            char ch = in.charAt(0);
            if (Character.isDigit(ch))
                r = ch - 48;
            else {
                System.out.println("Invalid Input! Try Again!");
                continue;
            }

            ch = in.charAt(2);
            if (Character.isDigit(ch))
                c = ch - 48;
            else {
                System.out.println("Invalid Input! Try Again!");
                continue;
            }

            ch = in.charAt(4);
            if (Character.isLetter(ch))
                action = ch;
            else {
                System.out.println("Invalid Input! Try Again!");
                continue;
            }
        }
    }

    void floodOpen(int r, int c) {
        if (!field[r][c].isOpened() && !field[r][c].isflagged()) {
            field[r][c].openCell();
            opened++;
            if (field[r][c].getValue() == 0) {
                if (r > 0)
                    floodOpen(r - 1, c);
                if (c > 0)
                    floodOpen(r, c - 1);
                if (r < field.length - 1)
                    floodOpen(r + 1, c);
                if (c < field[0].length - 1)
                    floodOpen(r, c + 1);
            }
        }
    }

    void action() {
        if (action == 'F' || action == 'f') {
            if (!field[r][c].isflagged())
                field[r][c].flag();
            action = '\u0000';
        } else if (action == 'U' || action == 'u') {
            field[r][c].unflag();
            action = '\u0000';
        } else if (action == 'O' || action == 'o') {
            action = '\u0000';
            if (field[r][c].isflagged()) {
                System.out.println("Flagged");
                st = Status.Flag;
            } else if (field[r][c].getMine()) {
                System.out.println("Blow UP!");
                st = Status.Blown;
            } else
                floodOpen(r, c);
        }

    }

    void updateUI() {
        System.out.println("+-----------+");
        for (int i = 0; i < B_SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < B_SIZE; j++)
                if (field[i][j].isflagged())
                    System.out.print("F ");
                else if (field[i][j].isOpened()) {
                    if (field[i][j].getMine())
                        System.out.print("M ");
                    else if (field[i][j].getValue() > 0)
                        System.out.print(field[i][j].getValue() + " ");
                    else
                        System.out.print("  ");
                } else
                    System.out.print("\u2588 ");

            System.out.println("|");
        }
        System.out.println("+-----------+");
    }

    void checkWin() {
        if (opened == B_SIZE * B_SIZE - 3 && st != Status.Blown)
            st = Status.Won;
    }

    void gameLoop() {
        while (true) {
            input();
            action();
            checkWin();
            if (st == Status.Pass)
                updateUI();
            else if (st == Status.Blown) {
                gameOver();
                break;
            } else if (st == Status.Won) {
                System.out.println("You Won");
                printField();
                break;
            }
        }
    }

    void gameOver() {
        System.out.println("You lost");
        printField();
    }

    void printField() {
        for (int i = 0; i < B_SIZE; i++) {
            for (int j = 0; j < B_SIZE; j++)
                if (field[i][j].getMine() && field[i][j].isflagged())
                    System.out.print("G ");
                else if (field[i][j].isflagged())
                    System.out.print("F ");
                else if (field[i][j].getMine())
                    System.out.print("M ");
                else if (field[i][j].getValue() > 0)
                    System.out.print(field[i][j].getValue() + " ");
                else
                    System.out.print("  ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Minesweeper ms = new Minesweeper();

        ms.printField();
        ms.gameLoop();
    }
}
