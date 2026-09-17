import java.util.Scanner;
public class UT3 {
    private TicTacToe[][] bboard;
    private char[][] wboard;
    private char player;
    UT3() {
        bboard = new TicTacToe[3][3];
        wboard = new char[3][3];
        player = 'X';
    }

    public void tickMove(int g, int r, int c, int turn) throws Exception {
        if (bboard[(g - 1) / 3][(g - 1) % 3].getBoard()[r][c] == ' ')
        {
            player = (turn % 2 == 1)? 'X' : 'O';
            bboard[(g - 1) / 3][(g - 1) % 3].getBoard()[r][c] = player;
        }
        else
            throw new Exception("Space Occupied");

        //board[r][c] = player;
    }
    
    public boolean checkWin() {
        int i; // A
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
    
    public void showBoard(boolean isWon) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(bboard[i / 3][j / 3].getBoard()[i % 3][j % 3]);
                if(j % 3 < 2)
                    System.out.print("|");
                else if(j / 3 < 2)
                    System.out.print("||");
            }
            if(i % 3 < 2)
                System.out.println("\n---+---+---||---+---+---||---+---+---");
            else if(i / 3 < 2)
            {
                System.out.println("\n-----------||-----------||-----------");
                System.out.println("-----------||-----------||-----------");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        UT3 ut3 = new UT3();

        Player playerX = new User();
        Player playerO = new User();
        int turn = 1;
        ut3.showBoard(false);
        for(int i = 1 ; i <= 9 ; i++)
        {
            while(true)
            {
                System.out.printf("Player player%c : Enter the coordinates : ", (turn % 2 == 1)? 'X' : 'O');
                if(turn % 2 == 1)
                    playerX.chooseCell();
                else
                    playerO.chooseCell();
                try {
                    ut3.tickMove(Player.choseng, Player.chosenr, Player.chosenc, turn);
                    break;
                }
                catch(Exception e)
                {
                    System.out.println("Space Occupied");
                }
            }
            ut3.showBoard(false);
            if (ut3.checkWin()) {
                System.out.println();
                ut3.showBoard(true);
                ut3.displayWin();
            }
            turn++;
        }
        System.out.println("Game Drawn");
    } 
}

abstract class Player {
    static int choseng;
    static int chosenr;
    static int chosenc;
    public abstract void chooseCell();
}

class User extends Player {
    Scanner sc = new Scanner(System.in);
    public void chooseCell() {
        System.out.print("Grid 1 to 9: ");
        choseng = sc.nextInt();
        System.out.print("Row 1 to 3: ");
        chosenr = sc.nextInt() - 1;
        System.out.print("Column 1 to 3: ");
        chosenc = sc.nextInt() - 1;
    }
}