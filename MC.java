import greenfoot.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MC extends Actor {
    private ArrayList<GreenfootImage> walkUpImages = new ArrayList<>();
    private ArrayList<GreenfootImage> walkDownImages = new ArrayList<>();
    private ArrayList<GreenfootImage> walkLeftImages = new ArrayList<>();
    private ArrayList<GreenfootImage> walkRightImages = new ArrayList<>();
    private ArrayList<GreenfootImage> idleUpImages = new ArrayList<>();
    private ArrayList<GreenfootImage> idleDownImages = new ArrayList<>();
    private ArrayList<GreenfootImage> idleLeftImages = new ArrayList<>();
    private ArrayList<GreenfootImage> idleRightImages = new ArrayList<>();

    private String facingDirection = "down"; // Default direction
    private int frameIndex = 0;
    private int animationSpeed = 5; // Speed of animation
    private int counter = 0;
    private int speed = 4;

    // Gravity-related properties
    private int verticalSpeed = 0; // Current vertical speed
    private int gravity = 1; // Force of gravity
    private int groundLevel = 598; // Adjusted ground level
    private boolean onGround = false; // Is the character on the ground?

    private Prompt currentPrompt; // Keep track of the current prompt
    private boolean dialogTriggered = false;
    private boolean dialogCompleted = false; // Track if dialog is completed
    private boolean waitingForDialog = false; // Untuk menunggu dialog selesai sebelum berpindah dunia

    // Define transition points
    private HashMap<String, int[]> transitionPoints = new HashMap<>();

    public MC() {
        // Load animations
        loadImages();
    
        // Set initial image
        setImage(idleDownImages.get(0));
    
        // Define transition points
        transitionPoints.put("HouseToVillage", new int[]{810, 632});
        transitionPoints.put("VillageToHouse", new int[]{314, 283});
        transitionPoints.put("VillageToForest", new int[]{1224, 64});
        transitionPoints.put("ForestToForest_2", new int[]{1234, 605});
        transitionPoints.put("House2ToVillage", new int[]{500, 500});
        transitionPoints.put("Forest_2ToLv_1", new int[]{1236,628});
    }


    private void loadImages() {
        for (int i = 0; i <= 5; i++) {
            // Walking animations
            walkDownImages.add(scaleImage("Character/MC/Walk/walk_down/walk_down" + i + ".png"));
            walkUpImages.add(scaleImage("Character/MC/Walk/walk_up/walk_up" + i + ".png"));
            walkLeftImages.add(scaleImage("Character/MC/Walk/walk_left/walk_left" + i + ".png"));
            walkRightImages.add(scaleImage("Character/MC/Walk/walk_right/walk_right" + i + ".png"));
        }

        for (int i = 0; i <= 4; i++) {
            // Idle animations
            idleDownImages.add(scaleImage("Character/MC/Idle/idle_down/idle_down" + i + ".png"));
            idleUpImages.add(scaleImage("Character/MC/Idle/idle_up/idle_up" + i + ".png"));
            idleLeftImages.add(scaleImage("Character/MC/Idle/idle_left/idle_left" + i + ".png"));
            idleRightImages.add(scaleImage("Character/MC/Idle/idle_right/idle_right" + i + ".png"));
        }
    }

    private GreenfootImage scaleImage(String path) {
        GreenfootImage image = new GreenfootImage(path);
        image.scale(image.getWidth() * 2, image.getHeight() * 2); // Scale factor
        return image;
    }

    private void applyGravity() {
        // Check if the current world is the "Forest" world
        if (getWorld() instanceof Forest) {
            // Apply gravity logic
            if (!onGround) {
                verticalSpeed += gravity; // Increase speed due to gravity
                setLocation(getX(), getY() + verticalSpeed); // Apply vertical speed
            }
    
            // Check if the character hits the ground
            if (getY() >= groundLevel) {
                setLocation(getX(), groundLevel); // Snap to the ground level
                verticalSpeed = 0; // Reset vertical speed
                onGround = true; // Set onGround to true
            } else {
                onGround = false; // Character is mid-air
            }
        } else if (getWorld() instanceof Forest_2) {
            // Set a deeper ground level for Forest_2
            groundLevel = 630; // Adjusted ground level for Forest_2
            
            // Apply gravity logic
            if (!onGround) {
                verticalSpeed += gravity; // Increase speed due to gravity
                setLocation(getX(), getY() + verticalSpeed); // Apply vertical speed
            }
    
            // Check if the character hits the ground
            if (getY() >= groundLevel) {
                setLocation(getX(), groundLevel); // Snap to the ground level
                verticalSpeed = 0; // Reset vertical speed
                onGround = true; // Set onGround to true
            } else {
                onGround = false; // Character is mid-air
            }
        } else {
            // Reset vertical speed and onGround when not in Forest or Forest_2
            verticalSpeed = 0;
            onGround = true;
        }
    }

    
    private void handleInput() {
        boolean moving = false;
    
        // Check if the MC is in the Forest world
        boolean inForest = getWorld() instanceof Forest || getWorld() instanceof Forest_2;
    
        if (!inForest && (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up"))) {
            facingDirection = "up";
            moveUp();
            moving = true;
        }
        if (!inForest && (Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down"))) {
            facingDirection = "down";
            moveDown();
            moving = true;
        }
        if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left")) {
            facingDirection = "left";
            moveLeft();
            moving = true;
        }
        if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right")) {
            facingDirection = "right";
            moveRight();
            moving = true;
        }
    
        if (moving) {
            animateWalking();
        } else {
            animateIdle();
        }
    
        checkWorldTransition();
        handleDialog();
    }


    private void moveUp() {
        setLocation(getX(), getY() - speed);
    }

    private void moveDown() {
        setLocation(getX(), getY() + speed);
    }

    private void moveLeft() {
        setLocation(getX() - speed, getY());
    }

    private void moveRight() {
        setLocation(getX() + speed, getY());
    }

    private void animateWalking() {
        counter++;
        if (counter % animationSpeed == 0) { // Update frame based on animation speed
            frameIndex = (frameIndex + 1) % walkUpImages.size();
    
            switch (facingDirection) {
                case "up":
                    setImage(walkUpImages.get(frameIndex));
                    break;
                case "down":
                    setImage(walkDownImages.get(frameIndex));
                    break;
                case "left":
                    setImage(walkLeftImages.get(frameIndex));
                    break;
                case "right":
                    setImage(walkRightImages.get(frameIndex));
                    break;
            }
        }
    }


    private void animateIdle() {
        counter++;
        if (counter % 10 == 0) {
            frameIndex = (frameIndex + 1) % idleDownImages.size();

            switch (facingDirection) {
                case "up":
                    setImage(idleUpImages.get(frameIndex));
                    break;
                case "down":
                    setImage(idleDownImages.get(frameIndex));
                    break;
                case "left":
                    setImage(idleLeftImages.get(frameIndex));
                    break;
                case "right":
                    setImage(idleRightImages.get(frameIndex));
                    break;
            }
        }
    }

    private void checkWorldTransition() {
        if (getWorld() instanceof House || getWorld() instanceof House2) {
            int[] houseToVillage = transitionPoints.get("HouseToVillage");
            if (Math.abs(getX() - houseToVillage[0]) <= 50 && Math.abs(getY() - houseToVillage[1]) <= 10) {
                Greenfoot.setWorld(new Village(312, 347));
            }
        }
    
        if (getWorld() instanceof Village) {
            int[] villageToHouse = transitionPoints.get("VillageToHouse");
            if (Math.abs(getX() - villageToHouse[0]) <= 30 && Math.abs(getY() - villageToHouse[1]) <= 10) {
                Greenfoot.setWorld(new House());
            }
    
            int[] villageToForest = transitionPoints.get("VillageToForest");
            if (Math.abs(getX() - villageToForest[0]) <= 30 && Math.abs(getY() - villageToForest[1]) <= 30) {
                Greenfoot.setWorld(new Forest());
            }
        }
    
        if (getWorld() instanceof Forest) {
            int[] forestToForest_2 = transitionPoints.get("ForestToForest_2");
            if (Math.abs(getX() - forestToForest_2[0]) <= 30 && Math.abs(getY() - forestToForest_2[1]) <= 10) {
                Greenfoot.setWorld(new Forest_2());
            }
        }
        
        if (getWorld() instanceof Forest_2) {
            int[] forest_2ToLv_1 = transitionPoints.get("Forest_2ToLv_1");
            if (Math.abs(getX() - forest_2ToLv_1[0]) <= 30 && Math.abs(getY() - forest_2ToLv_1[1]) <= 10) {
                Greenfoot.setWorld(new Lv_1());
            }
        }
    }

     private void handleDialog() {
        // Check if this actor is still in the world before proceeding
        if (getWorld() == null) return;

        OldMan oldMan = (OldMan) getOneIntersectingObject(OldMan.class);
        OldMan_Death oldManDeath = (OldMan_Death) getOneIntersectingObject(OldMan_Death.class);
        Moon moon = (Moon) getOneIntersectingObject(Moon.class);
        // Handling dialog with OldMan
        if (oldMan != null && !dialogTriggered) {
            if (currentPrompt == null) {
                currentPrompt = new Prompt();
                getWorld().addObject(currentPrompt, oldMan.getX(), oldMan.getY() - 50);
            }

            if (Greenfoot.isKeyDown("enter") && !dialogTriggered) {
                dialogTriggered = true;
                if (currentPrompt != null) {
                    currentPrompt.setVisible(false);
                    getWorld().removeObject(currentPrompt);
                    currentPrompt = null;
                }

                String[] dialogLines = {
                    "",
                    "Outsider: Permisi...",
                    "Kakek: Hey nak tumben ada orang yang berkunjung ke desa ini",
                    "Outsider: Saya berkelana dan kebetulan lewat kek.",
                    "Kakek: Bagus, bagus. Kamu sudah punya tempat untuk bermalam?",
                    "Outsider: Belum, Kek. Rencananya saya cari tempat nanti sore.",
                    "Kakek: Bagaimana kalau menumpang di rumah saya?",
                    "Outsider: Yang bener kek?? Kalau begitu saya izin menginap semalam ya kek.",
                    "Outsider: Maaf lancang, di liat-liat kakek sendiri pada kemana ya??.",
                    "Kakek: ......",
                    "Outsider: Tidak apa-apa kek kalau tidak mau menjawab.",
                    "Kakek: Jadi begini, saat kakek keluar desa untuk berjualan di kota kake tidak bisa pulang karena hujan badai.",
                    "Kakek: Kakek pulang keesokan harinya saat malam hari",
                    "Kakek: Kakek kaget saat melihat keluarga kakek sudah berlumuran darah",
                    "Outsider: .....",
                    "Outsider: Turut Prihatin",
                    "Kakek: Tidak apa-apa nak, rumah jadi tidak terlalu sepi.",
                    "Kakek: Ya sudah sana tidur sudah sore.",
                    "Outsider: Okey kek, maaf sekali lagi."
                };
                Dialog dialog = new Dialog(dialogLines);
                getWorld().addObject(dialog, getWorld().getWidth() / 2, getWorld().getHeight() - 50);
            }
        } else {
            if (currentPrompt != null) {
                currentPrompt.setVisible(false);
                getWorld().removeObject(currentPrompt);
                currentPrompt = null;
            }
        }

        // After dialog is finished, allow interaction at coordinate (355, 334)
        if (dialogTriggered) {
            dialogCompleted = true;
        }

        // Special interaction at (355, 334) only if dialog is completed
        if (getWorld() instanceof House) {
            if (Math.abs(getX() - 355) <= 30 && Math.abs(getY() - 334) <= 50 && dialogCompleted) {
                if (currentPrompt == null) {
                    currentPrompt = new Prompt();
                    getWorld().addObject(currentPrompt, 355, 334 - 50);
                }

                if (Greenfoot.isKeyDown("enter")) {
                    dialogCompleted = false; // Reset dialog status
                    dialogTriggered = false; // Reset dialog trigger

                    // Remove the prompt after Enter is pressed
                    if (currentPrompt != null) {
                        currentPrompt.setVisible(false);
                        getWorld().removeObject(currentPrompt);
                        currentPrompt = null;
                    }

                    String[] dialogLines = {
                        "Outsider: huff.",
                        "Outsider: Aku lelah sekali...",
                        "Outsider: Aku akan tidur lalu melanjutkan perjalanan besok."
                    };
                    Dialog dialog = new Dialog(dialogLines);
                    getWorld().addObject(dialog, getWorld().getWidth() / 2, getWorld().getHeight() - 50);

                    // Transition to "House2" world and place MC at the correct position
                    House2 newWorld = new House2(); 
                    Greenfoot.setWorld(newWorld);

                    // Teleport MC to coordinates (500, 500)
                    MC mc = new MC();
                    newWorld.addObject(mc, 355, 334); 

                    // Remove the current MC from the old world
                    if (getWorld() != null) {
                        getWorld().removeObject(this);
                    }
                }
            } else {
                if (currentPrompt != null && currentPrompt.getX() == 355) {
                    currentPrompt.setVisible(false);
                    getWorld().removeObject(currentPrompt);
                    currentPrompt = null;
                }
            }
        }

        // Handling dialog with OldMan_Death
        if (oldManDeath != null && !dialogTriggered) {
            if (currentPrompt == null) {
                currentPrompt = new Prompt();
                getWorld().addObject(currentPrompt, oldManDeath.getX(), oldManDeath.getY() - 50);
            }

            if (Greenfoot.isKeyDown("enter") && !dialogTriggered) {
                dialogTriggered = true;

                // Hide the prompt and remove it
                if (currentPrompt != null) {
                    currentPrompt.setVisible(false);
                    getWorld().removeObject(currentPrompt);
                    currentPrompt = null;
                }

                // Set the dialog lines for OldMan_Death
                String[] dialogLines = {
                    "",
                    "Outsider: Kakek...",
                    "Outsider: Siapa yang tega melakukan ini kepada kakek",
                    "Outsider: Aku akan membalaskan dendam mu kek",
                    "Outsider: SIALANNNN!!!!!!",
                    "Outsider: Aku akan mencari ke hutan"
                };

                // Create a new dialog instance and add it to the world
                Dialog dialog = new Dialog(dialogLines);
                getWorld().addObject(dialog, getWorld().getWidth() / 2, getWorld().getHeight() - 50);
            }
        }
        
        if (moon != null && !dialogTriggered) {
            if (currentPrompt == null) {
                currentPrompt = new Prompt();
                getWorld().addObject(currentPrompt, moon.getX(), moon.getY() - 50);
            }

        if (Greenfoot.isKeyDown("enter") && !dialogTriggered) {
            dialogTriggered = true;
            if (currentPrompt != null) {
                currentPrompt.setVisible(false);
                getWorld().removeObject(currentPrompt);
                currentPrompt = null;
            }

            String[] dialogLines = {
                "",
                "Outsider: (mengangkat kepala, bingung) \"Siapa... siapa itu? Siapa yang memanggilku?\"",
                "Bulan: (suara lembut dan menggema) \"Outsider... aku di sini. Aku adalah Bulan,\"",
                "Bulan: \"temanmu yang selalu menyaksikanmu dari jauh.\"",
                "Outsider: (mata terbelalak) \"B-Bulan? Kau bisa bicara? Apa ini lelucon?\"",
                "Bulan: (suara tenang) \"Ini bukan lelucon, Outsider. Aku telah mengamati dirimu sejak lama.\"",
                "Bulan: \"Kau selalu merasa terasing, bukan? Aku ingin memberimu kekuatan untuk mengubah hidupmu.\"",
                "Outsider: (merenung) \"Kekuatan? Apa maksudmu?\"",
                "Bulan: \"Aku akan memberimu kekuatan untuk melampaui batas manusia.\"",
                "Bulan: \"Kau akan mampu melakukan hal-hal yang tak terbayangkan. Tapi, ada harganya.\"",
                "Outsider: (penasaran) \"Harga? Apa itu?\"",
                "Bulan: \"Setiap kali kau menggunakan kekuatan ini, umurmu akan berkurang.\"",
                "Bulan: \"Kau akan menua lebih cepat, dan waktumu di dunia ini akan semakin singkat.\"",
                "Outsider: (diam sejenak, berpikir) \"Jadi... aku harus mempertaruhkan umurku untuk kekuatan ini?\"",
                "Bulan: \"Benar. Tapi ingat, Outsider, kekuatan ini bisa mengubah segalanya.\"",
                "Bulan: \"Kau bisa menjadi pahlawan, atau bahkan penguasa. Tapi pilihan ada di tanganmu.\"",
                "Outsider: (menatap bulan, tekad menguat) \"Aku... aku menerimanya. Aku lelah merasa lemah dan tak berdaya.\"",
                "Outsider: \"Jika ini caranya untuk membuat hidupku berarti, aku rela.\"",
                "Bulan: (suara penuh arti) \"Baiklah, Outsider. Mulai malam ini, kekuatan itu akan menjadi milikmu.\"",
                "Bulan: \"Tapi ingat, setiap kali kau menggunakannya, waktumu akan berkurang. Gunakanlah dengan bijak.\"",
                "[Outsider merasakan energi aneh mengalir di tubuhnya. Dia mengepalkan tangan, merasakan kekuatan baru yang luar biasa.]",
                "Outsider: (tersenyum kecil) \"Terima kasih, Bulan. Aku akan menggunakan ini untuk mengubah segalanya.\"",
                "Bulan: (suara semakin lembut) \"Semoga kau menemukan apa yang kau cari, Outsider.\"",
                "Bulan: \"Tapi jangan lupa, waktu adalah hadiah yang paling berharga.\""
                };


                Dialog dialog = new Dialog(dialogLines);
                getWorld().addObject(dialog, getWorld().getWidth() / 2, getWorld().getHeight() - 50);
            }
        }
    }

    public void act() {
        handleInput();
        applyGravity();
    }
}