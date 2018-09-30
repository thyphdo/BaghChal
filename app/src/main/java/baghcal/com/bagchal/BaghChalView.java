package baghcal.com.bagchal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

import static android.graphics.Color.argb;

public class BaghChalView extends View {
    int numRows = 5, numCols = 5;
    int previousX = 0;//To know the position of previous  click
    int previousY = 0;
    int goatCount = 1;
    int tigerCount = 1;//Just a count variable
    public static BaghChalState board = new BaghChalState();

    int oldWidth = 0;
    int oldHeight = 0;
    int[][] state = new int[numRows][numCols]; //from 1 to 5
    public final Drawable[] drawables = new Drawable[3];
    public final Bitmap[] bitmaps = new Bitmap[3];
    Rect srcRect;
    Rect[][] destRects = new Rect[numRows + 1][numCols + 1]; // grid destination rectangles, from 1 to 5
    Resources res = null;


    static final Paint whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    static final Paint blackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    static final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    static final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    static final Paint specialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public final static Integer[] imageResIds = new Integer[]{0, R.drawable.tiger, R.drawable.goat};

    static {
        // background color
        blackPaint.setStyle(Paint.Style.STROKE);

        // grid lines
        whitePaint.setColor(argb(255, 208, 211, 212));
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setStrokeWidth(12);

        // text color
        textPaint.setColor(argb(255, 212, 172, 13));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(60);

        // special paint
        specialPaint.setColor(argb(255, 247, 220, 111));
        specialPaint.setStyle(Paint.Style.FILL);
        specialPaint.setTextSize(70);

    }


    public BaghChalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        res = context.getResources();
        init();
    }


    private void init() { // initialize the SemaphoreView
        state = new int[numRows + 1][numCols + 1];
        for (int i = 1; i <= 2; i++) { //storing images
            drawables[i] = res.getDrawable(imageResIds[i]);
            bitmaps[i] = BitmapFactory.decodeResource(res, imageResIds[i]);
        }

        srcRect = new Rect(0, 0, bitmaps[1].getWidth() - 1, bitmaps[1].getHeight() - 1);
        computeNewDestRects();
    }


    private void computeNewDestRects() { // recompute bounding rectangles for grid images

        int width = getWidth();
        int height = getHeight();
        int cellSize = Math.min(width / numCols, height / numRows); // size of grid cells
        int ulx = (width - numCols * cellSize) / 5; // upper-left x of grid
        int uly = (height - numRows * cellSize) / 5; // upper-left y of grid


        for (int r = 1; r <= numRows; r++)
            for (int c = 1; c <= numCols; c++)
                destRects[r][c] = new Rect(ulx + cellSize * (c - 1),
                        uly + cellSize * (r - 1),
                        ulx + cellSize * c,
                        uly + cellSize * r);
    }


    public void reset() { // reset the game to the initial state
        for (int r = 1; r <= numRows; r++)
            for (int c = 1; c <= numCols; c++)
                state[r][c] = 0;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.i("onDraw", "Start"); // See log messages like these in the Eclipse LogCat window.  See http://developer.android.com/tools/debugging/debugging-log.html
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width != oldWidth || height != oldHeight) {
            computeNewDestRects(); // recompute bounding rectangles for grid images
            oldWidth = width;
            oldHeight = height;
        }


        int cellSize = Math.min(width / numCols, height / numRows); // size of grid cells
        int ulx = (width - numCols * cellSize) / 5; // upper-left x of grid
        int uly = (height - numRows * cellSize) / 5; // upper-left y of grid


        // draw background
        canvas.drawRect(0, 0, width, height, blackPaint);

        // draw grid lines
        for (int r = 1; r <= 9; r = r + 2)
            canvas.drawLine(ulx + cellSize / 2, uly + cellSize * r / 2, ulx + cellSize * 9 / 2, uly + cellSize * r / 2, whitePaint);
        for (int c = 1; c <= 9; c = c + 2)
            canvas.drawLine(ulx + cellSize * c / 2, uly + cellSize / 2, ulx + cellSize * c / 2, uly + cellSize * 9 / 2, whitePaint);

        //draw diagonal lines
        canvas.drawLine(ulx + cellSize / 2, uly + cellSize / 2, ulx + cellSize * 9 / 2, uly + cellSize * 9 / 2, whitePaint);
        canvas.drawLine(ulx + cellSize / 2, uly + cellSize * 9 / 2, ulx + cellSize * 9 / 2, uly + cellSize / 2, whitePaint);
        canvas.drawLine(ulx + cellSize / 2, uly + cellSize * 5 / 2, ulx + cellSize * 5 / 2, uly + cellSize / 2, whitePaint);
        canvas.drawLine(ulx + cellSize * 5 / 2, uly + cellSize / 2, ulx + cellSize * 9 / 2, uly + cellSize * 5 / 2, whitePaint);
        canvas.drawLine(ulx + cellSize * 9 / 2, uly + cellSize * 5 / 2, ulx + cellSize * 5 / 2, uly + cellSize * 9 / 2, whitePaint);
        canvas.drawLine(ulx + cellSize * 5 / 2, uly + cellSize * 9 / 2, ulx + cellSize / 2, uly + cellSize * 5 / 2, whitePaint);


        // draw pieces
        for (int r = 1; r <= numRows; r++)
            for (int c = 1; c <= numCols; c++) {
                if (board.getPiece(r - 1, c - 1) != 0) {
                    canvas.drawBitmap(bitmaps[board.getPiece(r - 1, c - 1)], srcRect, destRects[r][c], paint);
                }
            }
        if (!board.isGameOver() ) {
            //draw current scores
            canvas.drawText("Goats eaten : " + board.GOATS_EATEN, ulx + cellSize / 2, uly + cellSize * 11 / 2, textPaint);
            canvas.drawText("Tiger trapped : " + board.tigersTrapped(), ulx + cellSize * 5 / 2, uly + cellSize * 11 / 2, textPaint);
            //draw current player
            canvas.drawText("Current player : " + (board.getCurrentPlayer() == 1 ? "Tiger" : "Goat"), ulx + cellSize / 2, uly + cellSize * 12 / 2, textPaint);


        }
        else {
            canvas.drawText("Game is Over!!!", ulx + cellSize / 2, uly + cellSize * 10 / 2, specialPaint);
            canvas.drawText("The winner is " + ((board.getCurrentPlayer() == board.TIGER) ? "GOAT" : "TIGER"), ulx + cellSize/ 2, uly + cellSize * 11 / 2, specialPaint);
            canvas.drawText("(Press anywhere to restart)", ulx + cellSize/2, uly + cellSize * 12 / 2, specialPaint);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i("onTouchEvent", "start");
        // If the game's not over and it's a DOWN event...
        if (board.isGameOver() && (event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)) {
            board.reset();
            reset();
            return true;
        }
        else {

            if (event.getActionMasked() == MotionEvent.ACTION_DOWN || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                // ... compute the cell row and col ...
                int width = getWidth();
                int height = getHeight();

                int cellSize = Math.min(width / numCols, height / numRows); // size of grid cells
                int ulx = (width - numCols * cellSize) / 5; // upper-left x of grid
                int uly = (height - numRows * cellSize) / 5; // upper-left y of grid
                int pressRow = (int) Math.ceil((event.getY() - uly) / cellSize);
                int pressCol = (int) Math.ceil((event.getX() - ulx) / cellSize);
                int newPressRow = pressRow - 1;//The presRow and pressCol works in 1 index form
                int newPressCol = pressCol - 1;

                Log.i("onTouchEvent", String.format("press at [%d][%d]", pressRow, pressCol));

                if (board.getCurrentPlayer() == 2 && board.GoatsLeft > 0) {//This means its a goat turn ///  Later,Add the part where the goat runs out
                    if (board.placeGoats(newPressRow, newPressCol))
                        invalidate();


                } else if (board.getCurrentPlayer() == 2 && board.GoatsLeft == 0 && goatCount != 2 && newPressRow >= 0 && newPressCol >= 0 && newPressRow < 5 && newPressCol < 5 && board.getPiece(newPressRow, newPressCol) != 0) {
                    previousX = newPressRow;//Storing the value of previous row and col
                    previousY = newPressCol;
                    goatCount = goatCount + 1;

                } else if (board.getCurrentPlayer() == 2 && board.GoatsLeft == 0 && goatCount == 2 && newPressRow >= 0 && newPressCol >= 0 && newPressRow < 5 && newPressCol < 5 && board.getPiece(newPressRow, newPressCol) == 0) { //Its a tigers turn and user has clicked twice

                    if (board.move(previousX, previousY, newPressRow, newPressCol)) {
                        invalidate();
                        goatCount = 1;
                    }


                } else if (board.getCurrentPlayer() == 1 && tigerCount != 2 && newPressRow >= 0 && newPressCol >= 0 && newPressRow < 5 && newPressCol < 5 && board.getPiece(newPressRow, newPressCol) != 0) {
                    previousX = newPressRow;//Storing the value of previous row and col
                    previousY = newPressCol;
                    tigerCount = tigerCount + 1;

                } else if (board.getCurrentPlayer() == 1 && tigerCount == 2 && newPressRow >= 0 && newPressCol >= 0 && newPressRow < 5 && newPressCol < 5 && board.getPiece(newPressRow, newPressCol) == 0) { //Its a tigers turn and user has clicked twice
                    if (Math.max(Math.abs(previousX - newPressRow), (Math.abs(previousY - newPressCol))) == 2) {
                        //Log.i("board.getCurrentPlayer==", String.format("%d", board.getCurrentPlayer()));
                        if (board.jump(previousX, previousY, newPressRow, newPressCol)) {
                            invalidate();
                            tigerCount = 1;
                        }


                    } else {

                        if (board.move(previousX, previousY, newPressRow, newPressCol)) {
                            invalidate();
                            tigerCount = 1;
                        }
                    }


                }

            }
            return true;
        }
    }
}