import greenfoot.*;  

/**
 * Village is the world where MC will be spawned.
 */
public class Village extends World {
    private GreenfootImage background;

    /**
     * Constructor for objects of class Village.
     * This constructor allows specifying the MC's spawn location.
     * 
     * @param spawnX The x-coordinate where MC will spawn.
     * @param spawnY The y-coordinate where MC will spawn.
     */
    public Village(int spawnX, int spawnY) {    
        // Create a new world with 1280x720 pixels.
        super(1280, 720, 1);

        // Set the background image and scale it to fit the world
        background = new GreenfootImage("Background/Village_Night.png");
        background.scale(getWidth(), getHeight());
        setBackground(background);

        // Add the MC actor to the specified spawn location
        MC mc = new MC();
        addObject(mc, spawnX, spawnY);
    }

    /**
     * Default constructor for the Village world.
     * Spawns MC at the bottom-right corner.
     */
    public Village() {
        this(1280 - 60, 720 - 60); // Default spawn point (adjust values if needed)
    }

    /**
     * This method is called by the MC class when the teleport condition is met.
     * Transitions to AnotherWorld2.
     */
    public void goToOtherWorld() {
        Greenfoot.setWorld(new House()); // Transition to AnotherWorld2
    }
}
