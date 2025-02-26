import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Forest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Forest extends World
{
    private GreenfootImage background;

    /**
     * Constructor for objects of class Forest.
     * 
     */
    public Forest()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1); 
        
        background = new GreenfootImage("Background/Forest.png");
        background.scale(getWidth(), getHeight());
        setBackground(background);
        prepare();
    }
    
    private void prepare(){
        MC mc = new MC();
        addObject(mc, 47,598);
    }
}
