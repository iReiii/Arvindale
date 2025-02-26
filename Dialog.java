import greenfoot.*;

public class Dialog extends Actor {
    private String[] lines; // Array of dialog lines
    private int currentLineIndex = 0; // Index of the current dialog line
    private GreenfootImage dialogBox; // The dialog box image
    private int displayTime = 300; // Number of frames to display each line (5 seconds at 60 FPS)
    private int frameCounter = 0; // Counter for the timer
    private boolean enterPressed = false; // Tracks if "Enter" has been pressed

    public Dialog(String[] lines) {
        this.lines = lines;
    }

    @Override
    protected void addedToWorld(World world) {
        int width = world.getWidth(); // Get the world's width
        dialogBox = new GreenfootImage(width, 150); // Create the dialog box
        updateDialogBox();
    }

    private void updateDialogBox() {
        dialogBox.clear(); // Clear previous text
        dialogBox.setColor(Color.BLACK);
        dialogBox.fillRect(0, 0, dialogBox.getWidth(), dialogBox.getHeight());
        dialogBox.setColor(Color.WHITE);
        dialogBox.setFont(new Font("Arial", 24)); // Set font and size

        if (currentLineIndex < lines.length) {
            dialogBox.drawString(lines[currentLineIndex], 20, 75); // Display the current line
        }

        setImage(dialogBox); // Set as the actor's image
    }

    public void act() {
        frameCounter++;

        // Advance the dialog when Enter is pressed or after 5 seconds
        if (Greenfoot.isKeyDown("enter") && !enterPressed) {
            enterPressed = true;
            nextLine();
        }

        if (frameCounter >= displayTime) {
            nextLine();
        }

        // Reset the flag to detect a single Enter press
        if (!Greenfoot.isKeyDown("enter")) {
            enterPressed = false;
        }
    }

    private void nextLine() {
        frameCounter = 0; // Reset the timer
        currentLineIndex++;

        if (currentLineIndex < lines.length) {
            updateDialogBox();
        } else {
            getWorld().removeObject(this); // Remove dialog box after the last line
        }
    }
}
