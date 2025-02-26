import greenfoot.*;

public class House extends World {

    public House() {
        super(1280, 720, 1);

        // Set the background image and scale it to fit the world
        GreenfootImage background = new GreenfootImage("Background/Home_Night.png");
        background.scale(getWidth(), getHeight());
        setBackground(background);

        // Add the MC actor to the world at coordinates (813, 608)
        MC mc = new MC();
        addObject(mc, 813, 608);

        // Add OldMan actor to the world at coordinates (552, 333)
        OldMan oldMan = new OldMan();
        addObject(oldMan, 552, 333);
    }
}
