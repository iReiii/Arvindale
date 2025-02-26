import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Forest_2 extends World
{
    private GreenfootImage background;
    
    public Forest_2()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1); 
        
        background = new GreenfootImage("Background/Forest_2.png");
        background.scale(getWidth(), getHeight());
        setBackground(background);
        prepare();
    }
    
    private void prepare(){
        MC mc = new MC();
        addObject(mc, 29,621);
        Moon moon = new Moon();
        addObject(moon, 832,641);
    }
}
