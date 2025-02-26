import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Title extends Actor
{
    public Title() {
        // Load the image for the Title actor
        GreenfootImage titleImage = new GreenfootImage("Assets/Title.png"); // Replace with your image filename
        
        // Scale the image to fit the width of the world (1280 pixels)
        int newWidth = 800;
        int newHeight = (titleImage.getHeight() * newWidth) / titleImage.getWidth(); // Maintain aspect ratio
        
        titleImage.scale(newWidth, newHeight);
        
        // Set the image for the actor
        setImage(titleImage);
    }

    /**
     * Act - do whatever the Title wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add any actions you want for the Title actor here
    }
}
