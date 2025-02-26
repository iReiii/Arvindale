import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Question extends Actor
{
    public Question()
    {
        GreenfootImage questionImage = new GreenfootImage("Assets/Question.png");
        questionImage.scale(questionImage.getWidth() / 5, questionImage.getHeight() / 5);
        setImage(questionImage);
    }

    /**
     * Act - do whatever the Start wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Check if the Question actor is clicked
        if (Greenfoot.mouseClicked(this))
        {
            // Switch to the TutorialWorld when clicked
            Greenfoot.setWorld(new TutorialWorld());
        }
    }
}
