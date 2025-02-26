import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Story1 extends World {
    private int textIndex = 0; // Indeks untuk blok teks yang ditampilkan
    private String[] text = {
        "Di balik kabut tebal yang menyelimuti pegunungan, ada sebuah desa yang tersembunyi dari dunia luar. Desa ini, bernama Arvindale, terletak di lembah yang sunyi, jauh dari hiruk-pikuk kehidupan kota besar.",
        "Hanya sedikit yang tahu tentang tempat ini, dan lebih sedikit lagi yang pernah menginjakkan kaki di sana. Namun, suatu hari, seorang pengembara datang melintasi jalan setapak yang terlupakan, mencari tempat untuk beristirahat setelah perjalanan panjang yang melelahkan.",
        "Pengembara itu, yang hanya dikenal sebagai 'Outsider', tidak mengharapkan apa-apa selain ketenangan. Wajahnya yang lelah mencerminkan cerita panjang tentang petualangan dan pelarian.",
        "Namun satu hal yang pasti—kehidupan di desa ini tidak akan pernah sama lagi setelah malam itu."
    };

    private final int MAX_LINE_WIDTH = 800; // Lebar maksimum teks (piksel)
    private final int FONT_SIZE = 30; // Ukuran font teks
    private final Color TEXT_COLOR = Color.WHITE; // Warna teks
    private final Color BG_COLOR = new Color(0, 0, 0, 128); // Latar belakang teks transparan

    private GreenfootImage background;

    /**
     * Constructor for objects of class Story1.
     */
    public Story1() {    
        super(1280, 720, 1);

        // Load and set the initial background image
        background = new GreenfootImage("Background/Prolog.png");
        background.scale(getWidth(), getHeight());
        setBackground(background);

        // Show the first block of text
        updateScreen();
    }

    /**
     * Act - do whatever the Story1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        // Proceed to the next text block when the user clicks
        if (Greenfoot.mouseClicked(null)) {
            textIndex++;
            if (textIndex < text.length) {
                updateScreen();
            } else {
                // Show loading message and transition to another world
                showLoadingText();
                Greenfoot.delay(50); // Delay to let user see the loading message
                Greenfoot.setWorld(new Village()); // Replace with your next world
            }
        }
    }

    /**
     * Updates the screen with the current text block.
     */
    private void updateScreen() {
        // Reset the background to clear old text
        GreenfootImage updatedBackground = new GreenfootImage(background);
        
        // Wrap and format the current text block
        String wrappedText = wrapText(text[textIndex], MAX_LINE_WIDTH, FONT_SIZE);
        
        // Create a GreenfootImage for the text
        GreenfootImage textImage = new GreenfootImage(wrappedText, FONT_SIZE, TEXT_COLOR, BG_COLOR);
        
        // Calculate the position to center the text
        int x = (getWidth() - textImage.getWidth()) / 2;
        int y = (getHeight() - textImage.getHeight()) / 2;
        
        // Draw the text onto the updated background
        updatedBackground.drawImage(textImage, x, y);

        // Set the new background
        setBackground(updatedBackground);
    }

    /**
     * Displays a loading text on the screen.
     */
    private void showLoadingText() {
        // Clear the screen with the original background
        GreenfootImage updatedBackground = new GreenfootImage(background);

        // Create a GreenfootImage for the loading text
        GreenfootImage loadingText = new GreenfootImage("Loading...", FONT_SIZE, TEXT_COLOR, BG_COLOR);

        // Calculate the position to center the loading text
        int x = (getWidth() - loadingText.getWidth()) / 2;
        int y = (getHeight() - loadingText.getHeight()) / 2;

        // Draw the loading text onto the updated background
        updatedBackground.drawImage(loadingText, x, y);

        // Set the new background
        setBackground(updatedBackground);
    }

    /**
     * Wraps text to fit within a specified width using GreenfootImage.
     */
    private String wrapText(String text, int maxWidth, int fontSize) {
        String[] words = text.split(" ");
        StringBuilder wrappedText = new StringBuilder();
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line.toString().isEmpty() ? word : line + " " + word;
            GreenfootImage testImage = new GreenfootImage(testLine, fontSize, Color.WHITE, null);

            if (testImage.getWidth() > maxWidth) {
                wrappedText.append(line).append("\n");
                line = new StringBuilder(word);
            } else {
                line.append(line.length() == 0 ? word : " " + word);
            }
        }

        wrappedText.append(line);
        return wrappedText.toString();
    }
}