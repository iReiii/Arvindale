import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class MainMenu extends World
{
    public MainMenu()
    {    
        super(1280, 720, 1);  // Ukuran dunia 1280x720
        prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        // Load the background image
        GreenfootImage background = new GreenfootImage("Background/Menu.png");  // Ganti dengan nama gambar latar belakang yang sesuai
        background.scale(getWidth(), getHeight());  // Skala gambar untuk memenuhi layar
        setBackground(background);  // Setel gambar latar belakang

        // Create and add menu buttons and other objects
        Quit quit = new Quit();
        addObject(quit,1227,654);
        quit.setLocation(798,680);
        Question question = new Question();
        addObject(question,555,620);
        question.setLocation(480,664);
        Start start = new Start();
        addObject(start,722,508);
        start.setLocation(656,509);
        Title title = new Title();
        addObject(title,687,194);
        title.setLocation(695,153);
        start.setLocation(706,471);
        quit.setLocation(816,657);
        question.setLocation(528,652);
        quit.setLocation(805,664);
        start.setLocation(746,497);
        start.setLocation(664,471);
        start.setLocation(729,440);
        start.setLocation(644,463);
        start.setLocation(737,487);
        start.setLocation(674,481);
        title.setLocation(712,168);
        title.setLocation(735,155);
        title.setLocation(687,151);
    }
}
