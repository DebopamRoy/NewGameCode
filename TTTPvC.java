class TTTPvC
{
    public static void main(String[] args)
    {
        TicTacToe ttt = new TicTacToe();
        //ttt.gameTick();
        Player playerX = null, playerO = null;
        Computer bot = null;
        int player = 0;
        //if((int)(Math.random() * 2) == 0)
        if(true)
        {
            playerX = new User("Player X");
            playerO = new Computer(3, -1, "Computer");
            bot = (Computer)playerO;
            player = 1;
        }
        else
        {
            playerO = new User("Player O");
            playerX = new Computer(3, 1, "Computer");
            bot = (Computer)playerX;
            player = 0;
        }

        int turn = 1;
        ttt.showBoard(false);
        for(int i = 1 ; i <= 9 ; i++)
        {
            while(true)
            {
                if(player == turn % 2)
                    System.out.printf("Player player%c : Enter the coordinates : ", (turn % 2 == 1)? 'X' : 'O');
                else
                    System.out.println("Computer plays its move:");
                bot.getBoard(ttt.getBoard());
                bot.getTurn(turn);
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
                ttt.displayWin(playerX, playerO);
            }
            turn++;
        }
        System.out.println("Game Drawn");
    }
}
