class Computer extends Player {
    private char[][] board;
    private int turn;
    private int difficulty; // 1 : Easy     2 : Medium      3 : Hard
    private int player; // 1 : 'X'      -1 : 'O'
    private String name;
    public Computer(int difficulty, int player, String name) {
        board = new char[3][3];
        turn = 0;
        this.difficulty = difficulty;
        this.player = player;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void getBoard(char[][] board) {
        this.board = board;
    } 

    public void getTurn(int turn) {
        this.turn = turn;
    }

    public void chooseCell() {
        int[] rating = new int[9];
        int[] pos = new int[9];
        for(int i = 0 ; i < 9 ; i++)
        {
            rating[i] = Integer.MIN_VALUE;
            pos[i] = i;
        }
        for(int i = 0; i < 3 ; i++)
            for(int j = 0 ; j < 3 ; j++)
                if(board[i][j] == ' '){
                    board[i][j] = (turn % 2 == 1)? 'X' : 'O';
                    rating[i * 3 + j] = checkPossibility(board, (turn % 2 == 1)? -1 : 1, 9 - turn + 1);
                    board[i][j] = ' ';
                }
        int t = 0;
        for(int i = 0 ; i < 8 ; i++)
            for(int j = 0 ; j < 8 - i ; j++)
                if(rating[j + 1] > rating[j])
                {
                    t = rating[j+1];
                    rating[j+1] = rating[j];
                    rating[j] = t;

                    t = pos[j+1];
                    pos[j+1] = pos[j];
                    pos[j] = t;
                }
        if(difficulty == 3)
        {
            chosenr = pos[0] / 3;
            chosenc = pos[0] % 3;
        }
        else if(difficulty == 2)
        {
            chosenr = pos[(9 - turn + 1) / 2] / 3;
            chosenc = pos[(9 - turn + 1) / 2] % 3;
        }
        else if(difficulty == 1)
        {
            chosenr = pos[(9 - turn + 1) / 3] / 3;
            chosenc = pos[(9 - turn + 1) / 3] % 3;
        }
    }

    /*
    Algorithm modified from an averaging algorithm to a maximin algorithm. Oof got very close to figuring it out myself!!
    Averaging algorithm simply does not distinguish good and bad lines. So many good lines and one really bad line may get chosen and vice-versa.
    Maximin/Minimax always chooses the best choices for the ai expecting best choices made by opponent.
     */
    public int checkPossibility(char board[][], int player, int cells) {
        int winner = checkWin(board);
        if(winner < 2)
            return (int)(winner * Math.pow(4, cells));
        else
        {
            int sum = (this.player == player)? Integer.MIN_VALUE : Integer.MAX_VALUE;
            // int sum = 0;
            for(int i = 0 ; i < 3 ; i++)
                for(int j = 0 ; j < 3 ; j++)
                    if(board[i][j] == ' ')
                    {
                        board[i][j] = (player == 1)? 'X' : 'O';
                        if(this.player == player)
                            sum = Math.max(sum, checkPossibility(board, 0 - player, cells - 1));
                        else
                            sum = Math.min(sum, checkPossibility(board, 0 - player, cells - 1));
                        // sum += checkPossibility(board, 0 - player, cells - 1)
                        board[i][j] = ' ';
                    }
            return sum; 
        }
    }

    public int checkWin(char board[][]) {
        char p = (player == 1)? 'X' : 'O';
        for(int i = 0 ; i < 3 ; i++) {
            if(board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != ' ')
                return (board[i][0] == p)? 1:-1;
            if(board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != ' ')
                return (board[0][i] == p)? 1:-1;
            //return (board[0][i] == player)? (player == this.player)? 1:-1 : (player != this.player)? -1:1;
        }
        if(board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != ' ')
            return (board[0][0] == p)? 1:-1;
        //return (board[0][0] == player)? (player == this.player)? 1:-1 : (player != this.player)? -1:1;
        if(board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] != ' ')
            return (board[0][2] == p)? 1:-1;
        //return (board[0][2] == player)? (player == this.player)? 1:-1 : (player != this.player)? -1:1;
        for(int i = 0 ; i < 3 ; i++)
            for(int j = 0 ; j < 3 ; j++)
                if(board[i][j] == ' ')
                    return 2;
        return 0;
    }

}