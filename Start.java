import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Start class that handles transition to "Story 1" world with an enhanced loading animation.
 */
public class Start extends Actor {
    public Start() {
        // Load the image "Start.png" from the Assets folder
        GreenfootImage startImage = new GreenfootImage("Assets/Start.png");
        // Resize the image to make it smaller (e.g., 50% of the original size)
        startImage.scale(startImage.getWidth() / 2, startImage.getHeight() / 2);
        setImage(startImage); // Set the resized image as the actor's image
    }

    /**
     * Act - Handles click interactions and starts loading.
     */
    public void act() {
        // Check if the actor is clicked
        if (Greenfoot.mouseClicked(this)) {
            loadingScreen();
        }
    }

    /**
     * Transition to the Story1 world.
     */
    private void loadingScreen() {
        Greenfoot.setWorld(new Story1());
    }
}
