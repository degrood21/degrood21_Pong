package com.example.dylandegrood.degrood21_pong;

/**
 * Ball Object class
 *
 * Helps to create a Ball object to be used in Pong game
 *
 * @author Dylan DeGrood
 * @version March 2018
 */
public class Ball {

    float Xposition, Yposition, Xvelocity, Yvelocity;


    /**
     * Cstor to creat Ball object
     *
     * @param Xpos X position of ball
     * @param Ypos Y position of ball
     * @param Xvel X velocity of ball
     * @param Yvel Y velocity of ball
     */
    public Ball(float Xpos, float Ypos, float Xvel, float Yvel){

        Xposition = Xpos;
        Yposition = Ypos;
        Xvelocity = Xvel;
        Yvelocity = Yvel;

    }

}
