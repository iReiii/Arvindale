import greenfoot.*;
import java.util.ArrayList;
import java.util.HashMap;

public class NightBorne extends Actor {
    private ArrayList<GreenfootImage> runLeftImages = new ArrayList<>();
    private ArrayList<GreenfootImage> runRightImages = new ArrayList<>();
    private ArrayList<GreenfootImage> idleLeftImages = new ArrayList<>();
    private ArrayList<GreenfootImage> idleRightImages = new ArrayList<>();
    private ArrayList<GreenfootImage> hurtLeftImages = new ArrayList<>();
    private ArrayList<GreenfootImage> hurtRightImages = new ArrayList<>();
    private ArrayList<GreenfootImage> attackLeftImages = new ArrayList<>();
    private ArrayList<GreenfootImage> attackRightImages = new ArrayList<>();
    private ArrayList<GreenfootImage> deathLeftImages = new ArrayList<>();
    private ArrayList<GreenfootImage> deathRightImages = new ArrayList<>();

    private String facingDirection = "right"; 
    private int frameIndex = 0;
    private int animationSpeed = 5; 
    private int attackAnimationSpeed = 3; 
    private int deathAnimationSpeed = 10; // Animasi kematian lebih lambat
    private int counter = 0;
    private int speed = 4;
    private int attackRange = 20; // Jarak serang
    private int invincibleTime = 65; // Jeda setelah terkena damage (dalam frame)
    private int invincibleCounter = 0;

    private int verticalSpeed = 0; 
    private int gravity = 1; 
    private int groundLevel = 510; 
    private boolean onGround = false;
    private boolean isAttacking = false;
    private boolean isDead = false; // Status kematian
    
    private HashMap<String, int[]> transitionPoints = new HashMap<>();
    
    private int health = 100; // Variabel health
    
    public NightBorne() {
        loadImages();        
        setImage(idleRightImages.get(0));
        transitionPoints.put("Lv_1ToLv_2", new int[]{1236,549});
    }
    
    private void loadImages() {
        for (int i = 0; i <= 8; i++) {
            idleRightImages.add(scaleImage(new GreenfootImage("Character/NightBorne/NightBorne_idle/NightBorne_Idle" + String.format("%02d", i) + ".png")));
        }
        for (int i = 0; i <= 5; i++) {
            runRightImages.add(scaleImage(new GreenfootImage("Character/NightBorne/NightBorne_run/NightBorne_Run" + String.format("%02d", i) + ".png")));
        }
        for (int i = 0; i <= 4; i++) {
            hurtRightImages.add(scaleImage(new GreenfootImage("Character/NightBorne/NightBorne_hurt/NightBorne_Hurt" + String.format("%02d", i) + ".png")));
        }
        for (int i = 0; i <= 11; i++) {
            attackRightImages.add(scaleImage(new GreenfootImage("Character/NightBorne/NightBorne_attack/NightBorne_Attack" + String.format("%02d", i) + ".png")));
        }
        for (int i = 0; i <= 22; i++) {
            deathRightImages.add(scaleImage(new GreenfootImage("Character/NightBorne/NightBorne_death/NightBorne_Death" + String.format("%02d", i) + ".png")));
        }
        mirrorImages();
    }
    
    private void mirrorImages() {
        mirrorAndAdd(idleRightImages, idleLeftImages);
        mirrorAndAdd(runRightImages, runLeftImages);
        mirrorAndAdd(hurtRightImages, hurtLeftImages);
        mirrorAndAdd(attackRightImages, attackLeftImages);
        mirrorAndAdd(deathRightImages, deathLeftImages);
    }
    
    private void mirrorAndAdd(ArrayList<GreenfootImage> source, ArrayList<GreenfootImage> target) {
        for (GreenfootImage image : source) {
            GreenfootImage mirroredImage = new GreenfootImage(image);
            mirroredImage.mirrorHorizontally();
            target.add(mirroredImage);
        }
    }

    private GreenfootImage scaleImage(GreenfootImage image) {
        image.scale(image.getWidth() * 3, image.getHeight() * 3);
        return image;
    }
    
    private void handleInput() {
        if (isDead) return; // Jika sudah mati, hentikan input

        if (!isAttacking && Greenfoot.isKeyDown("enter")) {
            isAttacking = true;
            frameIndex = 0;
        }

        if (isAttacking) {
            animateAttack();
            return;
        }

        boolean moving = false;
    
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
            animateRun();
        } else {
            animateIdle();
        }
    }

    private void moveLeft() {
        setLocation(getX() - speed, getY());
    }

    private void moveRight() {
        setLocation(getX() + speed, getY());
    }

    private void animateRun() {
        counter++;
        if (counter % animationSpeed == 0) {
            frameIndex = (frameIndex + 1) % runRightImages.size();
    
            switch (facingDirection) {
                case "left":
                    setImage(runLeftImages.get(frameIndex));
                    break;
                case "right":
                    setImage(runRightImages.get(frameIndex));
                    break;
            }
        }
    }

    private void animateIdle() {
        counter++;
        if (counter % 10 == 0) {
            frameIndex = (frameIndex + 1) % idleRightImages.size();

            switch (facingDirection) {
                case "left":
                    setImage(idleLeftImages.get(frameIndex));
                    break;
                case "right":
                    setImage(idleRightImages.get(frameIndex));
                    break;
            }
        }
    }
    
    private void animateAttack() {
        counter++;
        if (counter % attackAnimationSpeed == 0) {
            if (frameIndex >= attackRightImages.size()) {
                isAttacking = false;
                return;
            }

            switch (facingDirection) {
                case "left":
                    setImage(attackLeftImages.get(frameIndex));
                    break;
                case "right":
                    setImage(attackRightImages.get(frameIndex));
                    break;
            }
            frameIndex++;
        }
    }
    
    private void animateDeath() {
        counter++;
        if (counter % deathAnimationSpeed == 0) {
            if (frameIndex >= deathRightImages.size()) {
                // Animasi kematian selesai, tampilkan "Game Over"
                showGameOver();
                Greenfoot.stop(); // Hentikan permainan
                return;
            }

            switch (facingDirection) {
                case "left":
                    setImage(deathLeftImages.get(frameIndex));
                    break;
                case "right":
                    setImage(deathRightImages.get(frameIndex));
                    break;
            }
            frameIndex++;
        }
    }
    
    private void showGameOver() {
        World world = getWorld();
        if (world == null) return;
        
        // Buat teks "Game Over"
        GreenfootImage gameOverText = new GreenfootImage("Game Over", 48, Color.RED, new Color(0, 0, 0, 0));
        
        // Tampilkan teks di tengah layar
        int x = (world.getWidth() - gameOverText.getWidth()) / 2;
        int y = (world.getHeight() - gameOverText.getHeight()) / 2;
        world.getBackground().drawImage(gameOverText, x, y);
    }
    
    private void applyGravity() {
        if (!onGround) {
            verticalSpeed += gravity;
            setLocation(getX(), getY() + verticalSpeed);
        }
    
        if (getY() >= groundLevel) {
            setLocation(getX(), groundLevel);
            verticalSpeed = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }
    
    // Metode untuk mengurangi health
    public void takeDamage(int damage) {
        if (isDead || invincibleCounter > 0) return; // Jika sudah mati atau masih dalam cooldown, abaikan damage
    
        health -= damage;
        invincibleCounter = invincibleTime; // Aktifkan cooldown
    
        if (health <= 0) {
            health = 0;
            isDead = true;
            frameIndex = 0;
        }
    }

    
    // Metode untuk menampilkan health bar
    private void drawHealthBar() {
        World world = getWorld();
        if (world == null) return;
        
        // Buat teks health
        GreenfootImage healthBar = new GreenfootImage("Health: " + health, 24, Color.WHITE, new Color(0, 0, 0, 0));
        
        // Tampilkan health bar di pojok kiri atas
        world.getBackground().drawImage(healthBar, 10, 10);
    }
    
    private void checkWorldTransition() {
        if (getWorld() instanceof Lv_1) {
            int[] lv_1ToLv_2 = transitionPoints.get("Lv_1ToLv_2");
            
            // Longgarkan kondisi perpindahan
            if (Math.abs(getX() - lv_1ToLv_2[0]) <= 50 && Math.abs(getY() - lv_1ToLv_2[1]) <= 50) {
                Greenfoot.setWorld(new Lv_2());
            }
        }
    }
    
    public void act() {
        if (isDead) {
            animateDeath();
            return;
        }
    
        if (invincibleCounter > 0) {
            invincibleCounter--; // Kurangi cooldown jika masih ada
        }
    
        handleInput();
        applyGravity();
        drawHealthBar();
        checkWorldTransition();
    
        // Deteksi tabrakan dengan Goblin
        Goblin goblin = (Goblin) getOneIntersectingObject(Goblin.class);
        if (goblin != null) {
            takeDamage(10);
        }
    }
}