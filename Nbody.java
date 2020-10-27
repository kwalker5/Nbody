//Kate Walker
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.awt.Color;

public class Nbody extends JPanel implements ActionListener{
    private String name;//string to store the name of the body
    private double mass;//double to store the mass of the body 
    private int xcord;//int to store the starting x coordinate
    private int ycord;//int to store the starting y coordinate
    private double xVelocity;//double to store the velocity of x
    private double yVelocity;//double to store thr velocity of y
    private String size;//string to store the size
    private  Color col;//variable to store the color of the body
    private double xForce;//double to store the force of x
    private double yForce;//double to store the force of y
    public Nbody(String name, String mass, String xc, String yc, String xv, String yv, String size){//constructor for the bodies class
        this.name = name;//set name to given name
        this.mass = Double.parseDouble(mass);//set mass to given mass
        this.xcord = Integer.parseInt(xc);//set x coordinate to the given value
        this.ycord = Integer.parseInt(yc);//set y coordinate to the given value
        this.xVelocity = Double.parseDouble(xv);//set x velocity to the given value
        this.yVelocity = Double.parseDouble(yv);//set y velocity to the given value
        this.size = size.substring(1);//set size to the first value of the given size
        //random variable to generate a random int
        Random rand = new Random();//make rand a new random
        col = new Color (rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)); //create a new, random color for each body
    }
    public double getMass(){//function to get the mass
        return mass;//return mass
    }
    public int getXcord(){//function to get the X coordinate
        return xcord;//return the x coordinate
    }
    public int getYcord(){//function to get the Y coordinate
        return ycord;//return the y coordinate
    }
    public double getxVelocity(){//function to get the velocity of x
        return xVelocity;//return the velocity of x
    }
    public double getyVelocity(){//function to get the velocity of y
        return yVelocity;//return the velocity of y
    }
    public int Size(){//function to get the size
        return Integer.parseInt(size);//return size
    }

    public Color getColor(){//function to get the color
        return col;//return the color
    }
    public void force(Nbody two, double scale){//function to calculate the force
        Nbody one = this;//set one to this
        double x1 = two.xcord - one.xcord;//make a double that stores the second x coordinate minus the first x coordinate
        double y1 = two.ycord - one.ycord;//make a double that stores the second y coordinate minus the first y coordinate
        double dist = Math.sqrt(x1 * x1 + y1 * y1);//make a double to store the distance calculated bu taking the square root of the difference of the xs squared plus the ys squared
        double fr = (6.673e-11 * one.mass * two.mass / ((dist * dist) / scale));//double to store the final force based on the given equation
        one.xForce += fr * x1 / dist;//add the xforce of the first plus the force times the difference of the x coordinates
        one.yForce += fr * y1 / dist;//add the yforce of the first plus the force times the difference of the y coordinates

    }
    public void resetForce(){//function to reset the values
        xForce = 0.0;//set x force to 0.0
        yForce = 0.0;//set y force to 0.0
    }

    public void updatePos(){//function to update the postion of the bodies
        xVelocity += xForce / mass;//set x velocity equal to x velocity plus the x force divided by the mass
        yVelocity += yForce / mass;//set y velocity equal to y velocity plus the y force divided by the mass
        xcord += xVelocity;//set x coordinate to x coordinate plus the x velocity
        ycord += yVelocity;//set y coordinate to y coordinate plus the y velocity
    }
    private MyLists<Nbody> list;//initiate a list called bodies
    private double scale;//initiate a double to store the scale
    public Nbody(MyLists<Nbody> newList, double newScale){
        list = newList;//set the list to equal the new, given list
        scale = newScale;//set the scale to equal the new, given scale
    }

    public void paintComponent(Graphics g){//function to paint the bodies
        super.paintComponent(g);//call super.paint component on g
        Timer t = new Timer(400, this);//set a timer

        for (int i = 0; i < list.getSize(); i++){ //loop to create the bodies
            g.setColor(list.get(i).getColor());//set color to random color
            g.fillOval(list.get(i).getXcord(), list.get(i).getYcord(), list.get(i).Size(), list.get(i).Size());//make the circle based on the values in the ArrayList or LinkedList
        }

        t.start();//start the timer
    }

    public void actionPerformed(ActionEvent a){//function for when the action is preformed
        updateShapes();//call update function
        repaint();//repaint the shapes
    }

    public void updateShapes(){//function to update the shapes
        int i;//initiate an int i
        for(i = 0; i < list.getSize() - 1; i++){//loop through the list
            list.get(i).force(list.get(i + 1), scale);//calculate the force
            list.get(i).updatePos();//update the position by calling updatePos function
            list.get(i).resetForce();//reset the value
        }
        if(list.getSize() > 1){ //if the list size is greater than one
            list.get(i).force(list.get(i - 1), scale);//calculate the force
            list.get(i).updatePos();//update the position by calling updatePos function
            list.get(i).resetForce();//reset the value
        }
    }

    public static void main(String[] args) {

        MyLists<Nbody> temp = null;//make a temp list and set it to null
        double tempS = 0;//make a double to store the temp scale and set it to 0

        File input = new File(args[0]);//Takes the file inputted at the commandline
        try{//try to read from file
            Scanner sc = new Scanner(input);//make object of the scanner class
            String listType = sc.nextLine();//make string to store the first line of the file
            if(listType.equals("ArrayList")){ //if the first line is ArrayList
                temp = new MyArrayList<>();//creates an Arraylist to store the values
            }
            else if(listType.equals("LinkedList")){ // if the first line is LinkedList
                temp = new MyLinkedList<>();//creates a LinkedList
            }
            else{//if the first line isn't ArrayList or LinkedList
                System.out.println("Invalid type of List");//print invalid type of list
            }

            tempS = Double.parseDouble(sc.nextLine());//set the temp scale the information on the next line of the file
            sc.useDelimiter(",");// split the values at the comma
            while (sc.hasNext()){ //loop to add values to the list
                assert temp != null;//when the temp is not null
                temp.add(new Nbody(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.nextLine()));//add each value to the list
            }
            sc.close();//close the file
        }
        catch (FileNotFoundException e){//catch the file not found exception
            System.out.println("File not found");//print file not found
        }

        Nbody nBody = new Nbody(temp, tempS);//make an object of NBodies class based on the temp list and the temp scale
        JFrame jf = new JFrame();//make an object of JFrame
        jf.setTitle("NBody");//Title the JFrame
        jf.setSize(768,768); //set the window size
        jf.add(nBody); //add nBody to the JFrame
        jf.setVisible(true);//set visible to true
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
