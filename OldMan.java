import greenfoot.*;
import java.util.ArrayList;

public class OldMan extends Actor {
    private ArrayList<GreenfootImage> idleImages = new ArrayList<>();
    private int frameIndex = 0; // Current frame index
    private int animationSpeed = 10; // Animation speed
    private int counter = 0; // Counter for animation timing
    
    public OldMan() {
        loadImages();
        setImage(idleImages.get(0)); // Set the initial image
    }

    private void loadImages() {
        for (int i = 0; i <= 3; i++) {
            GreenfootImage idle = new GreenfootImage("Character/OldMan/OldMan" + i + ".png");
            idle.scale(idle.getWidth() * 5, idle.getHeight() * 5); 
            idleImages.add(idle);
        }
    }

    private void animateIdle() {
        counter++;
        if (counter % animationSpeed == 0) {
            frameIndex = (frameIndex + 1) % idleImages.size();
            setImage(idleImages.get(frameIndex));
        }
    }

    public void act() {
        animateIdle();
    }
}
