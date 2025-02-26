import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Lv_2 extends World
{
    private GreenfootImage background;
    
    public Lv_2()
    {    
        super(1280, 720, 1); 
        
        background = new GreenfootImage("Background/Stage_2.png");
        background.scale(getWidth(), getHeight());
        setBackground(background);
    }
}
