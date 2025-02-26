import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Quit extends Actor
{
    public Quit(){
        GreenfootImage img = new GreenfootImage("Assets/Quit.png");
        img.scale(img.getWidth() / 2, img.getHeight() / 2);
        setImage(img);
    }
    
    public void act()
    {
        // Check if the Quit button was clicked
        if (Greenfoot.mouseClicked(this))
        {
            // Get the current world
            World world = getWorld();
            
            // Stop the world or reset it. Here we stop it:
            Greenfoot.stop();
            
            // Alternatively, you can reset by creating a new instance of MainMenu:
            // Greenfoot.setWorld(new MainMenu());  // This resets the game to the main menu
        }
    }
}
