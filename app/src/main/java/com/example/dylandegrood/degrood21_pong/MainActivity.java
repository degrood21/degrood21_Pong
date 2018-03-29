package com.example.dylandegrood.degrood21_pong;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

/**
 * PongMainActivity
 *
 * This is the activity for the Pong game. It attaches a PongAnimator to
 * an AnimationSurface.
 *
 * @author Andrew Nuxoll
 * @author Steven R. Vegdahl
 * @author Dylan DeGrood
 * @version March 2018
 */

/**
 * Part B Enhancements:
 *
 * - Increases velocity as the ball hits any wall/paddle [5%]
 *
 * - Keeps a running score that player can see (+2 for hit) (-5 for miss) [5%]
 *
 * - Game ends once player loses last ball. Balls remaining is displayed [5%]
 *
 */

/**
 * Part A Enhancements:
 *
 * - Allowed arbitrary number of balls to be played but tapping surface [10%]
 *
 * - When ball leaves field it does not return until user taps screen again [5%]
 *
 */

public class MainActivity extends Activity {

    /**
     * creates an AnimationSurface containing a PongAnimator
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect the animation surface with the animator
        AnimationSurface mySurface = (AnimationSurface) this
                .findViewById(R.id.animationSurface);
        final PongAnimator animator = new PongAnimator(this);
        mySurface.setAnimator(animator);

        // Creates and connects the leftButton to listener
        Button addBallButton = (Button) this.findViewById(R.id.addBallButton);
        addBallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(animator.isGameOver){
                    animator.pauseClick = false;
                    animator.isGameOver = false;
                    animator.ballsInPlay++;
                    animator.playerScore = 0;
                }
                else {
                    Ball newBall = new Ball(animator.randomX(), animator.randomY(), animator.randomVelocityX(), animator.randomVelocityY());
                    animator.balls.add(newBall);
                    animator.ballsInPlay += 1;
                }
            }
        });

        // Creates and connects the pauseButton to listener
        Button pauseButton = (Button) this.findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Changes pauseClick boolean variable in PongAnimator animator
                animator.pauseClick = !animator.pauseClick;

            }
        });

    }
}
