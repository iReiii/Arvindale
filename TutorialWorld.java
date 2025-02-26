import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class TutorialWorld extends World {
    public TutorialWorld() {    
        // Set up the world with a specific size and a grid size of 1 pixel
        super(1280, 720, 1);  
        
        GreenfootImage background = new GreenfootImage("Background/Tutorial.jpg");  // Ganti dengan nama gambar latar belakang yang sesuai
        background.scale(getWidth(), getHeight());  // Skala gambar untuk memenuhi layar
        setBackground(background);
        
        addExitButton();
    }
    
    private void addExitButton() {
        Exit exitButton = new Exit();  // Menggunakan kelas Exit
        int x = getWidth() - exitButton.getImage().getWidth() / 2; // Posisi x di ujung kanan
        int y = getHeight() - exitButton.getImage().getHeight() / 2; // Posisi y di ujung bawah
        addObject(exitButton, x, y);  // Tambahkan objek Exit ke dunia
    }
}
