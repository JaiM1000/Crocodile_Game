import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Crocodile {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public Rectangle rec;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public boolean isCrashing;


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Crocodile(int pXpos, int pYpos) {
        xpos = 10;
        ypos = 500;
        dx = 0;
        dy = 0;
        width = 90;
        height = 90;
        isAlive = true;

        rec = new Rectangle(xpos,ypos,height,width);
        isCrashing = false;

    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        rec = new Rectangle(xpos,ypos,height,width);

        xpos = xpos + dx;
        ypos = ypos + dy;

        if(xpos >= 940){
            xpos -= 10;
        }
        if(xpos < 0){
            xpos += 10;
        }
        if(ypos < 20){
            ypos += 10;
        }
        if(ypos >= 650){
            ypos -= 10;
        }
    }
}