import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Lv_1 extends World
{
    private GreenfootImage background;
    
    public Lv_1()
    {    
        super(1280, 720, 1); 
        
        background = new GreenfootImage("Background/Stage_1.png");
        background.scale(getWidth(), getHeight());
        setBackground(background);
        prepare();
    }
    
    private void prepare()
    {
        NightBorne nightborne = new NightBorne();
        addObject(nightborne, 33,544);
        Goblin goblin = new Goblin();
        addObject(goblin, 1150, 510);
    }
}
