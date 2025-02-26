import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Exit extends Actor
{
    public Exit(){
        GreenfootImage img = new GreenfootImage("Assets/Quit.png");
        img.scale(img.getWidth() / 2, img.getHeight() / 2);
        setImage(img);
    }
    
    public void act() {
        // Check if the Quit button was clicked
        if (Greenfoot.mouseClicked(this)) {
            // Transition to the MainMenu
            Greenfoot.setWorld(new MainMenu());
        }
    }
}
