package baghcal.com.bagchal;

public class BaghChalState implements Cloneable {


    public static int GOATS_EATEN = 0;
    public static int GoatsLeft;
    public static int GoatsLeftStore;
    public static final int SIZE = 5;
    public static int board [][] = new int [SIZE][SIZE];
    public static final int GOAT = 2;
    public static final int TIGER = 1;
    public static int CurrentPlayer = 2;

    public BaghChalState(){

        board[0][0] = 1; // bottom left
        board[SIZE - 1][0] = 1; // top left
        board[SIZE - 1][SIZE - 1] = 1; // top right
        board[0][SIZE - 1] = 1; // bottom right
    }

    public int getCurrentPlayer(){
        return CurrentPlayer;
    }

    public void reset() {
        GoatsLeft = GoatsLeftStore;
        GOATS_EATEN = 0;
        CurrentPlayer = 2;
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                board[i][j] = 0;
        board[0][0] = 1; // bottom left
        board[SIZE - 1][0] = 1; // top left
        board[SIZE - 1][SIZE - 1] = 1; // top right
        board[0][SIZE - 1] = 1;

    }

    public int getPiece(int x, int y) {
        return board[x][y];
    }

    public boolean withinArrayBounds(int fromRow, int fromCol, int toRow, int toCol){
        if (fromRow<SIZE && fromCol< SIZE && toRow<SIZE&& toCol<SIZE &&
                fromRow>=0 && fromCol>=0&& toRow>=0 && toCol>=0 &&
                board[toRow][toCol]==0 ){
            return true;
        }
        return false;
    }

    public boolean move(int fromRow, int fromCol, int toRow, int toCol ){ // execute the diagonal move for tigers and goats as long as it is within bounds DONE
        if (checkDiagonal(fromRow, fromCol))
            if (fromRow!=toRow && fromCol!=toCol)
                return false;


        if (withinArrayBounds(fromRow, fromCol, toRow, toCol) && Math.max(Math.abs(fromRow-toRow),(Math.abs(fromCol-toCol)))==1){
            board[toRow][toCol]=CurrentPlayer;
            board[fromRow][fromCol] = 0;
            Changeturn();
            return true;

        }

        return false;
    }

    public boolean isSameType(int fromRow, int fromCol, int toRow, int toCol ) { // can only jump if the source and dest are same type
        if ((fromRow + fromCol)%2 == 1 && (toRow + toCol)%2 == 1)
            return true;
        else if ((fromRow + fromCol)%2 == 0 && (toRow + toCol)%2 == 0)
            return true;
        else {
            return false;
        }
    }

    public boolean jump(int fromRow, int fromCol, int toRow, int toCol ){// this method executes the diagonal jump and removes the goat as long as it is within bounds
        if (checkDiagonal(fromRow, fromCol) && isSameType(fromRow,fromCol,toRow,toCol) == false) {
            if (Math.max(Math.abs(fromRow - toRow),Math.abs(fromCol - toCol)) == 2) {
                return false;
            }
        }
        if (CurrentPlayer == TIGER && withinArrayBounds(fromRow, fromCol, toRow, toCol) &&
                Math.max(Math.abs(fromRow - toRow), Math.abs(fromCol - toCol)) ==2 &&
                board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] == GOAT) {
            board[toRow][toCol] = TIGER;
            board[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = 0;
            board[fromRow][fromCol] = 0;
            Changeturn();
            GOATS_EATEN++;
            BaghChalState.GoatsLeft--;
            return true;
        }

        return false;
    }

    public boolean checkDiagonal(int fromRow, int fromCol ){
        if((fromRow+fromCol)%2 == 1)
            return true;
        else
            return false;

    }

    public boolean isItTrapped(int x, int y){
        if (x + 1 < SIZE && board[x+1][y] == 0)
            return false;
        if (x - 1 >= 0 && board[x-1][y] == 0)
            return false;
        if (y + 1 < SIZE && board[x][y+1] == 0)
            return false;
        if (y - 1 >= 0 && board[x][y-1] == 0)
            return false;
        if ((x+y) % 2 == 0 && x+1 < SIZE && y+1 < SIZE && board[x+1][y+1] == 0)
            return false;
        if ((x+y) % 2 == 0 && x-1 > 0 && y-1 >= 0 && board[x-1][y-1] == 0)
            return false;
        if ((x+y) % 2 == 0 && x+1 < SIZE && y-1 >= 0 && board[x+1][y-1] == 0)
            return false;
        if ((x+y) % 2 == 0 && x-1 >= 0 && y+1 < SIZE && board[x-1][y+1] == 0)
            return false;
        if (x + 2 < SIZE && board[x+2][y] == 0)
            return false;
        if (x - 2 > 0 && board[x-2][y] == 0)
            return false;
        if (y + 2 < SIZE && board[x][y+2] == 0)
            return false;
        if (y - 2 > 0 && board[x][y-2] == 0)
            return false;
        if ((x+y) % 2 == 0 && x+2 < SIZE && y+2 < SIZE && board[x+2][y+2] == 0 && board[x+1][y+1]!=TIGER)
            return false;
        if ((x+y) % 2 == 0 && x-2 >= 0 && y-2 >= 0 && board[x-2][y-2] == 0 && board[x-1][y-1]!=TIGER)
            return false;
        if ((x+y) % 2 == 0 && x+2 < SIZE && y-2 >= 0 && board[x+2][y-2] == 0 && board[x+1][y-1]!=TIGER)
            return false;
        if ((x+y) % 2 == 0 && x-2 >= 0 && y+2 < SIZE && board[x-2][y+2] == 0 && board[x-1][y+1]!=TIGER)
            return false;

        return true;
    }

    public int tigersTrapped(){ // will return the integer value of the tigers trapped to be evaluated by is game over DO LATER
        int tigersTrapped = 0;
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++)
                if (board[x][y] == TIGER){
                    if (isItTrapped(x,y))
                        tigersTrapped++;
                }

        return tigersTrapped;

    }

    public boolean placeGoats(int toRow, int toCol){ //will place 1 of the initial 20 goats on the board                                                                        DONE
        if(CurrentPlayer==2 &&
                GoatsLeft>0 &&
                board[toRow][toCol]==0){

            board[toRow][toCol]=2;
            GoatsLeft--;
            Changeturn();// updates the number of goats left
            return true;
        }
        return false;
    }

    public void Changeturn(){// will switch turns between either player or player and computer when implemented                                                                   DONE
        if (CurrentPlayer == 2)
            CurrentPlayer = 1;
        else
            CurrentPlayer =  2;

    }

    public boolean isGameOver(){
        if(GOATS_EATEN == 5 || tigersTrapped() ==4)

            return true;
        return false;

    }


}
