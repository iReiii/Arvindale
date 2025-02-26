import greenfoot.*;
import java.util.ArrayList;

public class OldMan_Death extends Actor {
    private ArrayList<GreenfootImage> idleImages = new ArrayList<>();
    private int frameIndex = 0; // Current frame index
    private int animationSpeed = 10; // Animation speed
    private int counter = 0; // Counter for animation timing

    public OldMan_Death() {
        GreenfootImage image = new GreenfootImage("Character/OldMan_Death/OldMan_Death.png");

        // Scale the image to make it bigger

        setImage(image);
    }
}
