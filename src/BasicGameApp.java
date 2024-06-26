//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;
	public int backgroundX = 0;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image crocPic;
	public Image frogPic;
	public Image background;
	public Image fishPic;
	public Image sharkPic;
	public Image loser;
	public Image winner;
	public Image rules;
	public Fish[] fishes;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Crocodile croc;
	private Frog frog1;
	private Shark shark1;
	public int score = 0;
	boolean startGame = false;

	// Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();
       
      //variable and objects
      //create (construct) the objects needed for the game and load up

		crocPic = Toolkit.getDefaultToolkit().getImage("crocodile.png"); //load the picture
		croc = new Crocodile(10,100);

		frogPic = Toolkit.getDefaultToolkit().getImage("frog.png"); //load the picture
		frog1 = new Frog(0,0);

		fishPic = Toolkit.getDefaultToolkit().getImage("fish.png"); //load the picture
		fishes = new Fish[3];

		sharkPic = Toolkit.getDefaultToolkit().getImage("shark.png"); //load the picture
		shark1 = new Shark(100,100);

		for(int i = 0; i < fishes.length; i++) {
			fishes[i] = new Fish((int)(Math.random()*1000), (int)(Math.random()*650));
		}
		
		background = Toolkit.getDefaultToolkit().getImage("RiverBackground.jpg"); //load the picture
		loser = Toolkit.getDefaultToolkit().getImage("loser.png");
		winner = Toolkit.getDefaultToolkit().getImage("winner.png");
		rules = Toolkit.getDefaultToolkit().getImage("rules.png");
	}

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {
         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		if(-5 < score && score < 25) {
			croc.move();
			frog1.move();
			shark1.move();
			for(int i = 0; i < fishes.length; i++) {
				fishes[i].move();
			}
			checkIntersections();
		}
	}

	public void checkIntersections() {
		if(croc.rec.intersects(shark1.rec) && croc.isCrashing == false) {
			shark1.isAlive = false;
			score -= 1;
			System.out.println("Score: " + score);
			shark1.xpos = (int)(Math.random()*1000);
			shark1.ypos = (int)(Math.random()*650);
			croc.isCrashing = true;
		}
		if(croc.rec.intersects(shark1.rec) == false) {
			croc.isCrashing = false;
		}


		for(int i = 0; i < fishes.length; i++) {
			if (croc.rec.intersects(fishes[i].rec)) {
				shark1.isAlive = false;
				score += 2;
				System.out.println("Score: " + score);
				fishes[i].xpos = (int) (Math.random() * WIDTH);
				fishes[i].ypos = 0;
			}

			if(fishes[i].ypos <= 0) {
				score -= 1;
				System.out.println("Score: " + score);
			}
		}



		if(croc.rec.intersects(frog1.rec) && croc.isCrashing == false) {
			frog1.isAlive = false;
			score += 3;
			System.out.println("Score: " + score);
			frog1.xpos = (int)(Math.random()*950 + 50);
			frog1.ypos = (int)(Math.random()*600 + 50);
			croc.isCrashing = true;
		}
		if(croc.rec.intersects(frog1.rec) == false) {
			croc.isCrashing = false;
		}
	}
	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
	  canvas.addKeyListener(this);
      canvas.setIgnoreRepaint(true);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);

		if(backgroundX > -1000) {
			backgroundX = backgroundX-5;
		} else {
			backgroundX = 0;
		}

      //draw the image of the astronaut
		g.drawImage(background,backgroundX, 0, WIDTH, HEIGHT, null);
		g.drawImage(background,backgroundX+1000, 0, WIDTH, HEIGHT, null);

		g.drawImage(crocPic, croc.xpos, croc.ypos, croc.width, croc.height, null);

		g.drawImage(frogPic, frog1.xpos, frog1.ypos, frog1.width, frog1.height, null);
		g.drawImage(sharkPic, shark1.xpos, shark1.ypos, shark1.width, shark1.height, null);

		//g.setColor(Color.black);
		//g.fillRect(147, 22, 70, 50);
		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, 50, 50);

		for(int i = 0; i < fishes.length; i++) {
			g.drawImage(fishPic, fishes[i].xpos, fishes[i].ypos, fishes[i].width, fishes[i].height, null);
		}

		if(score <= -5) {
			g.drawImage(loser,0, 0, WIDTH, HEIGHT, null);

			score = -5;

			croc.dx = 0;
			croc.dy = 0;

			shark1.dx = 0;
			shark1.dy = 0;

			frog1.dx = 0;
			frog1.dy = 0;

			for(int i = 0; i < fishes.length; i++) {
				fishes[i].dx = 0;
				fishes[i].dy = 0;
			}
		}

		else if(score >= 25) {
			g.drawImage(winner,0, 0, WIDTH, HEIGHT, null);

			score = 25;

			croc.dx = 0;
			croc.dy = 0;

			shark1.dx = 0;
			shark1.dy = 0;

			frog1.dx = 0;
			frog1.dy = 0;

			for(int i = 0; i < fishes.length; i++) {
				fishes[i].dx = 0;
				fishes[i].dy = 0;
			}
		}

		if(startGame == false) {
			score = 0;
			g.drawImage(rules,0, 0, WIDTH, HEIGHT, null);
		}

		g.dispose();

		bufferStrategy.show();
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		if(e.getKeyCode() == 38) { // up
			croc.dx = 0;
			croc.dy = -7;
		}

		if(e.getKeyCode() == 40) { // down
			croc.dx = 0;
			croc.dy = 7;
		}

		if(e.getKeyCode() == 37) { // left
			croc.dx = -7;
			croc.dy = 0;
		}

		if(e.getKeyCode() == 39) { // right
			croc.dx = 7;
			croc.dy = 0;
		}

		if(e.getKeyCode() == 10) {
			startGame = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 38) { // up
			croc.dx = 0;
			croc.dy = 0;
		}

		if(e.getKeyCode() == 40) { // down
			croc.dx = 0;
			croc.dy = 0;
		}

		if(e.getKeyCode() == 37) { // left
			croc.dx = 0;
			croc.dy = 0;
		}

		if(e.getKeyCode() == 39) { // right
			croc.dx = 0;
			croc.dy = 0;
		}
	}
}