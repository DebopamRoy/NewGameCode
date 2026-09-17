class TTTPvP
{
    public static void main(String[] args)
    {
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
                System.out.printf("Player player%c : Enter the coordinates : ", (turn % 2 == 1)? 'X' : 'O');
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
                System.out.println();
                ttt.showBoard(true);
                ttt.displayWin(playerX, playerO);
            }
            turn++;
        }
        System.out.println("Game Drawn");
    }
}