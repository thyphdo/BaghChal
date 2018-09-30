package baghcal.com.bagchal;

import java.util.ArrayList;
        import java.util.Random;

public class Tiger {

    public int GOAT = 2;
    public int TIGER = 1;
    public int SIZE = 5;
    public ArrayList<Integer> possMove = new ArrayList<>();



    public int MarvinMover (int[][] board42){
        possMove.clear();
        int[][] boardy = new int[SIZE][SIZE];
        Random random = new Random(10);

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                boardy[i][j] = board42[i][j];

        int moveNumber = -1;
        String move;

        CanHeJump(boardy);
        if (!possMove.isEmpty())
            moveNumber = possMove.get(random.nextInt(possMove.size()));

        NoJumpSoMoveAgressively(boardy);
        if (moveNumber < 0 && !possMove.isEmpty())
            moveNumber = possMove.get(random.nextInt(possMove.size()));

        NoJumpSoMove(boardy);
        if (moveNumber < 0)
            moveNumber = possMove.get(random.nextInt(possMove.size()));


        System.out.println(moveNumber);

        int last = moveNumber % 10;
        int third = (moveNumber - last)%100;
        int second = (moveNumber - third - last)%1000;
        int first = moveNumber - second - third - last;

        last += 1;
        third += 10;
        second += 100;
        first += 1000;

        char four = (char)(last + 96);
        System.out.println((char)(last + 96));

        char two = (char)(second/100 + 96);
        System.out.println((char)(second/100 + 96));

        System.out.println(first+second+third+last);

        String mTest;

        StringBuilder sb = new StringBuilder();

        sb.append(two+ "" + (first/1000) + " " + four +""+ (third/10));

        String marvinMove = sb.toString();


        System.out.println(marvinMove);
        return moveNumber;

    }

    //Returns 10*rowNum + colNum if there is an available jump;  returns zero if there is no available jump.
    public void CanHeJump(int[][] board) {
        //    System.out.println("Hello2");

        int[][] boardNew = board;

        for (int x = 0; x < SIZE; x ++)
            for (int y = 0; y < SIZE; y++){
                if (boardNew[x][y] == TIGER){
                    if (x + 2 < SIZE && boardNew[x+2][y] == 0 && boardNew[x+1][y] == GOAT){
                        possMove.add( x*1000 + y*100 + 10*(x+2) + y);

                    }
                    if (x - 2 >= 0 && boardNew[x-2][y] == 0 && boardNew[x-1][y] == GOAT){
                        possMove.add(x*1000 + y*100 + 10*(x-2) + y);
                    }
                    if (y + 2 < SIZE && boardNew[x][y+2] == 0 && boardNew[x][y+1] == GOAT){
                        possMove.add(x*1000 + y*100 + 10*(x) + y+2);
                    }
                    if (y - 2 >= 0 && boardNew[x][y-2] == 0 && boardNew[x][y - 1] == GOAT){
                        possMove.add(x*1000 + y*100 + 10*x + y-2);
                    }
                    if ((x+y) % 2 == 0 && x+2 < SIZE && y+2 < SIZE && boardNew[x+2][y+2] == 0 && boardNew[x+1][y+1] == GOAT){
                        possMove.add(x*1000 + y*100 + 10*(x+2) + y+2);
                    }
                    if ((x+y) % 2 == 0 && x-2 >= 0 && y-2 >= 0 && boardNew[x-2][y-2] == 0 && boardNew[x-1][y-1] == GOAT){
                        possMove.add(x*1000 + y*100 + 10*(x-2) + y-2);
                    }
                    if ((x+y) % 2 == 0 && x+2 < SIZE && y-2 >= 0 && boardNew[x+2][y-2] == 0 && boardNew[x+1][y-1] == GOAT){
                        possMove.add(x*1000 + y*100 + 10*(x+2) + y-2);
                    }
                    if ((x+y) % 2 == 0 && x-2 >= 0 && y+2 < SIZE && boardNew[x-2][y+2] == 0 && boardNew[x-1][y+1] == GOAT){
                        possMove.add(x*1000 + y*100 + 10*(x-2) + y+2);
                    }
                }
            }
    }


    public void NoJumpSoMoveAgressively(int[][]boardGrid) {

        int[][] board13 = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board13[i][j] = boardGrid[i][j];
        int nx = 0;
        int ny = 0;

        for (int x = 0; x < SIZE; x ++)
            for (int y = 0; y < SIZE; y++)
                if (board13[x][y] == TIGER) {
                    if (x + 1 < SIZE && board13[x + 1][y] == 0) {
                        nx = x + 1;
                        ny = y;
                        for (int j = -1; j <= 1; j++)
                            for (int k = -1; k <= 1; k++) {
                                if (nx + 2 * j < SIZE && nx + j * 2 >= 0 && ny + 2 * k < SIZE && ny + k * 2 >= 0
                                        && board13[nx + j][ny + k] == GOAT && board13[nx + j * 2][ny + k * 2] == 0) {
                                    if (j == 0 || k == 0)
                                        possMove.add((x + 1) * 10 + y + x * 1000 + y * 100);
                                    else {
                                        if ((nx + ny) % 2 == 0)
                                            possMove.add((x + 1) * 10 + y + x * 1000 + y * 100);
                                    }
                                }
                            }
                    }

                    if (x - 1  >= 0 && board13[x-1][y] == 0){

                        nx = x - 1;
                        ny = y;
                        for (int j = -1; j <= 1; j++)
                            for (int k = -1; k <= 1; k++) {
                                if (nx + 2 * j < SIZE && nx + j * 2 >= 0 && ny + 2 * k < SIZE && ny + k * 2 >= 0
                                        && board13[nx + j][ny + k] == GOAT && board13[nx + j * 2][ny + k * 2] == 0) {
                                    if (j == 0 || k == 0)
                                        possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    else {
                                        if ((nx + ny) % 2 == 0)
                                            possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    }
                                }
                            }

                    }
                    if (y + 1  < SIZE && board13[x][y + 1] == 0){


                        nx = x;
                        ny = y + 1;
                        for (int j = -1; j <= 1; j++)
                            for (int k = -1; k <= 1; k++) {
                                if (nx + 2 * j < SIZE && nx + j * 2 >= 0 && ny + 2 * k < SIZE && ny + k * 2 >= 0
                                        && board13[nx + j][ny + k] == GOAT && board13[nx + j * 2][ny + k * 2] == 0) {
                                    if (j == 0 || k == 0)
                                        possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    else {
                                        if ((nx + ny) % 2 == 0)
                                            possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    }
                                }
                            }

                    }
                    if (y - 1  >= 0 && board13[x][y-1] == 0){
                        nx = x;
                        ny = y - 1;
                        for (int j = -1; j <= 1; j++)
                            for (int k = -1; k <= 1; k++) {
                                if (nx + 2 * j < SIZE && nx + j * 2 >= 0 && ny + 2 * k < SIZE && ny + k * 2 >= 0
                                        && board13[nx + j][ny + k] == GOAT && board13[nx + j * 2][ny + k * 2] == 0) {
                                    if (j == 0 || k == 0)
                                        possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    else {
                                        if ((nx + ny) % 2 == 0)
                                            possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    }
                                }
                            }
                    }
                    if ((x+y) % 2 == 0 && x+1  < SIZE && y+1  < SIZE && board13[x+1][y+1] == 0){
                        nx = x + 1;
                        ny = y + 1;
                        for (int j = -1; j <= 1; j++)
                            for (int k = -1; k <= 1; k++) {
                                if (nx + 2 * j < SIZE && nx + j * 2 >= 0 && ny + 2 * k < SIZE && ny + k * 2 >= 0
                                        && board13[nx + j][ny + k] == GOAT && board13[nx + j * 2][ny + k * 2] == 0) {
                                    if (j == 0 || k == 0)
                                        possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    else {
                                        if ((nx + ny) % 2 == 0)
                                            possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    }
                                }
                            }
                    }
                    if ((x+y) % 2 == 0 && x-1  >= 0 && y-1  >= 0 && board13[x-1 ][y-1 ] == 0){
                        nx = x - 1;
                        ny = y - 1;
                        for (int j = -1; j <= 1; j++)
                            for (int k = -1; k <= 1; k++) {
                                if (nx + 2 * j < SIZE && nx + j * 2 >= 0 && ny + 2 * k < SIZE && ny + k * 2 >= 0
                                        && board13[nx + j][ny + k] == GOAT && board13[nx + j * 2][ny + k * 2] == 0) {
                                    if (j == 0 || k == 0)
                                        possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    else {
                                        if ((nx + ny) % 2 == 0)
                                            possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    }
                                }
                            }
                    }
                    if ((x+y) % 2 == 0 && x+1 < SIZE && y-1  >= 0 && board13[x+1 ][y-1 ] == 0){
                        nx = x + 1;
                        ny = y - 1;
                        for (int j = -1; j <= 1; j++)
                            for (int k = -1; k <= 1; k++) {
                                if (nx + 2 * j < SIZE && nx + j * 2 >= 0 && ny + 2 * k < SIZE && ny + k * 2 >= 0
                                        && board13[nx + j][ny + k] == GOAT && board13[nx + j * 2][ny + k * 2] == 0) {
                                    if (j == 0 || k == 0)
                                        possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    else {
                                        if ((nx + ny) % 2 == 0)
                                            possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    }
                                }
                            }
                    }
                    if ((x+y) % 2 == 0 && x-1 >= 0 && y+1  < SIZE && board13[x-1 ][y+1 ] == 0){
                        nx = x - 1;
                        ny = y + 1;
                        for (int j = -1; j <= 1; j++)
                            for (int k = -1; k <= 1; k++) {
                                if (nx + 2 * j < SIZE && nx + j * 2 >= 0 && ny + 2 * k < SIZE && ny + k * 2 >= 0
                                        && board13[nx + j][ny + k] == GOAT && board13[nx + j * 2][ny + k * 2] == 0) {
                                    if (j == 0 || k == 0)
                                        possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    else {
                                        if ((nx + ny) % 2 == 0)
                                            possMove.add((nx )*10 + ny + x*1000 + y*100);
                                    }
                                }
                            }
                    }
                }
    }





    public void NoJumpSoMove(int[][]boardGrid) {

        int[][] board13 = new int[SIZE][SIZE];
        Random random = new Random();
        boolean plusMinus = random.nextBoolean();


        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board13[i][j] = boardGrid[i][j];

        for (int x = 0; x < SIZE; x ++)
            for (int y = 0; y < SIZE; y++)
                if (board13[x][y] == TIGER){
                    if (x + 1  < SIZE && board13[x+1 ][y] == 0){
                        possMove.add((x+1 )*10 + y + x*1000 + y*100);
                    }
                    if (x - 1  > 0 && board13[x-1 ][y] == 0){
                        possMove.add((x-1 )*10 + y + x*1000 + y*100);
                    }
                    if (y + 1  < SIZE && board13[x][y+1 ] == 0){
                        possMove.add((x)*10 + (y+1)  + x*1000 + y*100);
                    }
                    if (y - 1  > 0 && board13[x][y-1 ] == 0){
                        possMove.add((x)*10 + (y - 1)  + x*1000 + y*100);
                    }
                    if ((x+y) % 2 == 0 && x+1  < SIZE && y+1  < SIZE && board13[x+1 ][y+1 ] == 0){
                        possMove.add((x+1 )*10 + y + 1  + x*1000 + y*100);
                    }
                    if ((x+y) % 2 == 0 && x-1  > 0 && y-1  > 0 && board13[x-1 ][y-1 ] == 0){
                        possMove.add((x-1)*10 + y - 1 + x*1000 + y*100);
                    }
                    if ((x+y) % 2 == 0 && x+1 < SIZE && y-1  > 0 && board13[x+1 ][y-1 ] == 0){
                        possMove.add((x+1)*10 + y - 1  + x*1000 + y*100);
                    }
                    if ((x+y) % 2 == 0 && x-1  > 0 && y+1  < SIZE && board13[x-1 ][y+1 ] == 0){
                        possMove.add((x-1 )*10 + y + 1  + x*1000 + y*100);
                    }
                }
    }
}
