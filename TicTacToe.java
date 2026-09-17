  
import java.util.Scanner;

public class TicTacToe {
    Scanner sc;
    private char[][] board;
    private int r, c;
    private char player;
    private int[] winCells;

    TicTacToe() {
        sc = new Scanner(System.in);
        board = new char[3][3];
        for(int i = 0 ; i < 3 ; i++)
            for(int j = 0 ; j < 3 ; j++)
                board[i][j] = ' ';
        r = 0;
        c = 0;
        player = 'X';
        winCells = new int[3];
    }

    public void tickMove(int r, int c, int turn) throws Exception {
        if (r == -1 && c == -1)
        {
            System.out.println("Quitting Game...");
            System.exit(0);
        }
        else if (board[r][c] == ' ')
        {
            player = (turn % 2 == 1)? 'X' : 'O';
            board[r][c] = player;
            this.r = r;
            this.c = c;
        }
        else
            throw new Exception("Space Occupied");

        //board[r][c] = player;
    }

    public boolean checkWin() {
        int i;
        for (i = 0; i < 3; winCells[i] = r * 3 + i, i++)
            if (board[r][i] != player)
                break;
        if (i == 3)
            return true;
        for (i = 0; i < 3; winCells[i] = i * 3 + c, i++)
            if (board[i][c] != player)
                break;
        if (i == 3)
            return true;
        if (r == c) {
            for (i = 0; i < 3; winCells[i] = i * 3 + i, i++)
                if (board[i][i] != player)
                    break;
            if (i == 3)
                return true;
        }
        if (r + c == 2) {
            for (i = 0; i < 3; winCells[i] = i * 3 + 2 - i, i++)
                if (board[i][2 - i] != player)
                    break;
            if (i == 3)
                return true;
        }
        return false;
    }

    // public void gameTick() {
    // for (int i = 1; i <= 9; i++) {
    // input();
    // showBoard(false);
    // if (checkWin()) {
    // showBoard(true);
    // displayWin();
    // }
    // player = player == 'X' ? 'O' : 'X';
    // }
    // System.out.println("Game Drawn");
    // }

    public void showBoard(boolean isWon) {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boolean found = false;
                if (isWon)
                    for (int f = 0; f < 3; f++)
                        if (winCells[f] == i * 3 + j) {
                            found = true;
                            break;
                        }
                System.out.print(found ? "[" : " ");

                System.out.print(board[i][j]);

                System.out.print((found ? "]" : " "));
                if (j < 2)
                    System.out.print("|");
            }
            if (i < 2)
                System.out.println("\n---+---+---");
        }
        System.out.println();
    }

    public void displayWin(Player playerX, Player playerO) {
        System.out.printf("%s Won!", player == 1? playerX.getName() : playerO.getName());
        System.exit(0);
    }

    public char[][] getBoard() {
        return this.board;
    }

    public static void main(String[] args) {
        TicTacToe ttt = new TicTacToe();
        //ttt.gameTick();
        Player playerX = new User("Player X");
        Player playerO = new User("Player O");
        int turn = 1;
        ttt.showBoard(false);
        for(int i = 1 ; i <= 9 ; i++)
        {
            while(true)
            {
                System.out.printf("Player playerX : Enter the coordinates : \n");
                if(turn % 2 == 1)
                    playerX.chooseCell();
                else
                    playerO.chooseCell();
                try {
                    ttt.tickMove(Player.chosenr, Player.chosenc, turn);
                    break;
                }
                catch(Exception e)
                {
                    System.out.println("Space Occupied");
                }
            }
            ttt.showBoard(false);
            if (ttt.checkWin()) {
                ttt.showBoard(true);
                System.exit(0);
               // ttt.displayWin();
            }
            turn++;
        }
        System.out.println("Game Drawn");
    }
}
