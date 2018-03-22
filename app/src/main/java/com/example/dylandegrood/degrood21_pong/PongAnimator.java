package com.example.dylandegrood.degrood21_pong;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 * PongAnimator generates the simple game of Pong
 *
 * @author Dylan DeGrood
 * @version March 2018
 */

public class PongAnimator implements Animator {

    Random random = new Random(); // Random object created for randomizing numbers

    final int wallSize = 40; // size of the 3 walls
    final int paddleSize = 30; // the width size of the paddle
    final int paddleWidth = 500; // Width of the paddle
    final int ballRadius = 45; // default radius of the ball


    int canvasWidth; // width of the animation surface
    int canvasHeight; // height of the animation surface

    int currentLeftX = 774; // Left most edge of paddle is set to starting position
    // Is updated as the left button and right button is pressed
    int currentRightX = 1274; // Right most edge of paddle is set to starting position
    // Is updated as the left button and right button is pressed

    ArrayList<Ball> balls = new ArrayList<Ball>(); // ArrayList of balls from ball class in order to create many

    boolean pauseClick = false; // true if pause button was pressed, false otherwise
    boolean leftButton = false; // true if the left move button is pressed, false otherwise
    boolean rightButton = false; // true if the right move button is pressed, false otherwise


    /**
     * Cstor that creates the first ball to draw onto animation surface
     */
    public PongAnimator() {

        Ball firstBall = new Ball(0, 0, randomVelocityX(), randomVelocityY());
        balls.add(firstBall);

    }

    /**
     * Creates a random X velocity for each new ball
     *
     * @return float new X Velocity
     */
    public float randomVelocityX() {

        int x = 0;
        int ranX = random.nextInt(2);

        if (ranX == 0) {
            x = -1;
        } else {
            x = 1;
        }

        return x * (random.nextInt(10) + 10); // and + or - 0-19 number

    }

    /**
     * Creates a random Y Velocity for each new ball
     *
     * @return float new Y Velocity
     */
    public float randomVelocityY() {

        int y = 0;
        int ranY = random.nextInt(2);

        if (ranY == 0) {
            y = -1;
        } else {
            y = 1;
        }

        return y * (random.nextInt(10) + 10); // any + or - 0-19 number

    }

    /**
     * Returns the interval between animation frames
     *
     * @return the time interval between frames, in milliseconds
     */
    public int interval() {

        return 5;

    }


    /**
     * The background color: a light sky blue
     *
     * @return the background color onto which we will draw the image.
     */
    public int backgroundColor() {
        return 0xFF8FB8ED;
    }


    /**
     * Action to perform on clock tick
     *
     * @param canvas the graphics object on which to draw
     */

    public void tick(Canvas canvas) {

        canvasWidth = canvas.getWidth(); // sets canvas width for reference
        canvasHeight = canvas.getHeight(); // sets canvas height for reference

        if (pauseClick) { // if pauseClick is true stop balls at center of surface
            for (int i = 0; i < balls.size(); i++) {
                balls.get(i).Xposition = canvasWidth / 2;
                balls.get(i).Yposition = canvasHeight / 2;
            }
        }

        drawWalls(canvas); // draws the walls

        drawPaddle(canvas); // draws the paddle

        drawBalls(canvas); // draws the balls from ArrayList

        // Checks to see if any ball is hitting the top left, or right wall
        // or the paddle or if it is going out of bounds as well
        for (int i = 0; i < balls.size(); i++) {

            // Checks if ball is moving up and hitting the top wall
            if (balls.get(i).Yvelocity < 0 && balls.get(i).Yposition < wallSize + ballRadius) {

                balls.get(i).Yvelocity *= -1.05; // Speeds up ball each time it hits a wall or paddle

            }

            // Checks if ball is moving left and hitting the left wall
            else if (balls.get(i).Xvelocity < 0 && balls.get(i).Xposition < wallSize + ballRadius) {

                balls.get(i).Xvelocity *= -1.05;

            }

            // Checks if ball is moving right and htting the right wall
            else if (balls.get(i).Xvelocity > 0 && balls.get(i).Xposition > canvasWidth - wallSize - ballRadius) {

                balls.get(i).Xvelocity *= -1.05;

            }

            // Checks if ball is moving down and is hitting the paddle
            else if (balls.get(i).Yvelocity > 0
                    && balls.get(i).Yposition > canvasHeight - 2 * paddleSize - ballRadius
                    && balls.get(i).Xposition > currentLeftX
                    && balls.get(i).Xposition < currentRightX) {

                balls.get(i).Yvelocity *= -1.05;

            }

            // Checks to see if ball is going down and is out of bounds
            else if (balls.get(i).Yvelocity > 0 && balls.get(i).Yposition > canvasHeight + ballRadius) {

                if (balls.size() != 1) { // If more than one ball, removes ball and continues
                    balls.remove(i);
                    i--;
                    break;
                } else { // If only one ball left, then redraws the ball
                    balls.get(i).Xposition = randomX();
                    balls.get(i).Yposition = randomY();
                    balls.get(i).Xvelocity = randomVelocityX();
                    balls.get(i).Yvelocity = randomVelocityY();
                }

            }

            balls.get(i).Xposition += balls.get(i).Xvelocity; //Updating x position with new/old velocity x
            balls.get(i).Yposition += balls.get(i).Yvelocity; //Updating y position with new/old velocity y

        }
    }

    /**
     * Helps to redraw the paddle as it is being moved
     *
     * @param leftX  Updated left most side of paddle
     * @param rightX Updated right most side of paddle
     * @param c      used to draw paddle on same animation surface
     * @param p      current paint of paddle
     */
    public void drawRect(float leftX, float rightX, Canvas c, Paint p) {

        c.drawRect(leftX, canvasHeight - 2 * paddleSize, rightX, canvasHeight - (paddleSize - paddleSize / 4), p);

    }

    /**
     * Creates a random X position
     *
     * @return a random x position to be used for generating new balls
     */
    public int randomX(){
        int ranX = random.nextInt(canvasWidth-(2*wallSize)) + wallSize;
        return ranX;
    }

    /**
     * Creates a random Y position
     *
     * @return a random y position to be used for generating new balls
     */
    public int randomY(){
        int ranY = random.nextInt(canvasHeight-(2*wallSize)) + wallSize;
        return ranY;
    }

    /**
     * Draws the paddle to use in game
     *
     * @param canvas Animation Surface
     */
    public void drawPaddle(Canvas canvas){

        // Draws the paddle used in game
        Paint paddlePaint = new Paint();
        int paddleColor = 0xFFFF94E0; // paddle color: pink
        paddlePaint.setColor(paddleColor);
        // if leftButton is being pressed then move paddle left without it going past the wall
        if (leftButton && currentLeftX > wallSize) {
            currentLeftX -= 75;
            currentRightX -= 75;
            leftButton = !leftButton; // resets leftButton to false
        }
        // if right button is being pressed then move paddle right without it going past the wall
        else if (rightButton && currentRightX < canvasWidth - wallSize) {
            currentLeftX += 75;
            currentRightX += 75;
            rightButton = !rightButton; // resets rightButton to false
        }
        drawRect(currentLeftX, currentRightX, canvas, paddlePaint); // actually draws paddle

    }

    /**
     * Draws the walls that are visible in game
     *
     * @param canvas Animation Surface
     */
    public void drawWalls(Canvas canvas){

        // Drawing the 3 walls: top, left, and right
        Paint wallPaint = new Paint();
        int wallColor = 0xFF7165F2; // wall color: blueish purple
        wallPaint.setColor(wallColor);
        canvas.drawRect(0, 0, wallSize, canvasHeight, wallPaint); //left wall
        canvas.drawRect(canvasWidth - wallSize, 0, canvasWidth, canvasHeight, wallPaint); //right wall
        canvas.drawRect(0, 0, canvasWidth, wallSize, wallPaint); //top wall

    }

    /**
     * Draws the balls to be generated for game
     *
     * @param canvas Animation Surface
     */
    public void drawBalls(Canvas canvas){

        // Draws each ball that is in the balls ArrayList with updated positions
        Paint ballPaint = new Paint();
        int ballColor = 0xFFF1E3F3; // ball color: off white beige
        ballPaint.setColor(ballColor);
        for (int i = 0; i < balls.size(); i++) {
            canvas.drawCircle(balls.get(i).Xposition, balls.get(i).Yposition, ballRadius, ballPaint);
        }

    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    public boolean doPause() {
        return false;
    }


    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    public boolean doQuit() {
        return false;
    }

    /**
     * If animation surface is touched, then add a new ball for play
     *
     * @param event a MotionEvent describing the touch
     */
    public void onTouch(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Ball newBall = new Ball(randomX(), randomY(), randomVelocityX(), randomVelocityY());
            balls.add(newBall);
        }
    }
}
