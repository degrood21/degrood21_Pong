package com.example.dylandegrood.degrood21_pong;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.MotionEvent;
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
 * Part A Enhancements:
 *
 * - Allowed arbitrary number of balls to be played but tapping surface [10%]
 *
 * - When ball leaves field it does not return until user taps screen again [5%]
 *
 */

/**
 External Citation
 Date:     21 March 2018
 Problem:  Did not know how to long press button
 Resource:
 https://stackoverflow.com/questions/10511423/
 android-repeat-action-on-pressing-and-holding-a-button
 Solution: I used the solution code from above post
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
        final PongAnimator animator = new PongAnimator();
        mySurface.setAnimator(animator);

        // Creates and connects the leftButton to listener
        Button leftButton = (Button) this.findViewById(R.id.leftButton);
        leftButton.setOnTouchListener(new View.OnTouchListener() {

            private Handler mHandler;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 50);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    animator.leftButton = !animator.leftButton;
                    mHandler.postDelayed(this, 50);
                }
            };

        });

        // Creates and connects the rightButton to listener
        Button rightButton = (Button) this.findViewById(R.id.rightButton);
        rightButton.setOnTouchListener(new View.OnTouchListener() {

            private Handler mHandler;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 50);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    animator.rightButton = !animator.rightButton;
                    mHandler.postDelayed(this, 50);
                }
            };

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
