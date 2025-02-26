import greenfoot.*;

public class Prompt extends Actor {
    private boolean isVisible = true;  // Initially visible

    public Prompt() {
        updatePrompt();
    }

    // Update the appearance of the prompt (?)
    private void updatePrompt() {
        GreenfootImage image = new GreenfootImage(100, 50); // Set size for the prompt
        image.setColor(Color.WHITE);
        image.setFont(new Font("Arial", 24));

        image.fillRect(0, 0, image.getWidth(), image.getHeight());
        image.setColor(Color.BLACK);
        image.drawString("Enter", 10, 30); // Text to show
        setImage(image);
    }

    // Act method is only called to control the visibility, but no movement
    public void act() {
        if (isVisible) {
            // The prompt stays fixed, no movement, it stays in place above OldMan
            // No movement code here!
        }
    }

    // Set visibility for the prompt, so it can be shown or hidden
    public void setVisible(boolean visible) {
        this.isVisible = visible;
        if (!visible && getWorld() != null) {
            getWorld().removeObject(this);  // Remove the prompt when not visible
        }
    }
}
