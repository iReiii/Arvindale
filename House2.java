import greenfoot.*;

public class House2 extends World {

    public House2() {
        super(1280, 720, 1);

        // Set the background image and scale it to fit the world
        GreenfootImage background = new GreenfootImage("Background/Home_Night.png");
        background.scale(getWidth(), getHeight());
        setBackground(background);

        // Create an instance of the OldMan_Death class and add it to the world
        OldMan_Death oldManDeath = new OldMan_Death(); // Use OldMan_Death class
        addObject(oldManDeath, 552, 333);
    }
}
