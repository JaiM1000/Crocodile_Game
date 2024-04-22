import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Frog {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Frog(int pXpos, int pYpos) {
        xpos = 350;
        ypos = 200;
        dx = 8;
        dy = 8;
        width = 35;
        height = 35;
        isAlive = true;

    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if(xpos >= 940){
            dx = -dx;
        }
        if(xpos < 0){
            dx = -dx;
        }
        if(ypos < 20){
            dy = -dy;
        }
        if(ypos >= 650){
            dy = -dy;
        }
    }
}