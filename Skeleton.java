import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class Skeleton extends Actor{
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
    
    private String facingDirection = "left"; // Default direction
    private int frameIndex = 0;
    private int animationSpeed = 5; // Speed of animation
    private int attackAnimationSpeed = 6; // Faster attack animation
    private int counter = 0;
    private int speed = 2; // Kecepatan Goblin
    private int attackRange = 50; // Jarak serang
    
    private int verticalSpeed = 0; // Current vertical speed
    private int gravity = 1; // Force of gravity
    private int groundLevel = 520; // Adjusted ground level
    private boolean onGround = false;
    private boolean isAttacking = false;
    
    public Skeleton() {
        loadImages();        
        setImage(idleLeftImages.get(0));
    }
    
    private void loadImages() {
        for (int i = 0; i <= 3; i++) {
            idleRightImages.add(scaleImage(new GreenfootImage("Character/Skeleton/Skeleton_idle/Skeleton_Idle" + i + ".png")));
        }
        for (int i = 0; i <= 3; i++) {
            runRightImages.add(scaleImage(new GreenfootImage("Character/Skeleton/Skeleton_run/Skeleton_Run" + i + ".png")));
        }
        for (int i = 0; i <= 3; i++) {
            hurtRightImages.add(scaleImage(new GreenfootImage("Character/Skeleton/Skeleton_hurt/Skeleton_Hurt" + i + ".png")));
        }
        for (int i = 0; i <= 7; i++) {
            attackRightImages.add(scaleImage(new GreenfootImage("Character/Skeleton/Skeleton_attack/Skeleton_Attack" + i + ".png")));
        }
        for (int i = 0; i <= 3; i++) {
            deathRightImages.add(scaleImage(new GreenfootImage("Character/Skeleton/Skeleton_death/Skeleton_Death" + i + ".png")));
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
        image.scale(image.getWidth() * 2, image.getHeight() * 2);
        return image;
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
    
    private void animateHurt() {
        counter++;
        if (counter % animationSpeed == 0) {
            frameIndex = (frameIndex + 1) % hurtRightImages.size();

            switch (facingDirection) {
                case "left":
                    setImage(hurtLeftImages.get(frameIndex));
                    break;
                case "right":
                    setImage(hurtRightImages.get(frameIndex));
                    break;
            }
        }
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
    
    private void chaseNightBorne() {
        // Cari NightBorne di dunia
        NightBorne nightBorne = (NightBorne) getWorld().getObjects(NightBorne.class).get(0);
        
        if (nightBorne != null) {
            int nightBorneX = nightBorne.getX();
            int skeletonX = getX();
            
            // Tentukan arah gerakan
            if (skeletonX < nightBorneX) {
                facingDirection = "right";
                setLocation(getX() + speed, getY());
            } else if (skeletonX > nightBorneX) {
                facingDirection = "left";
                setLocation(getX() - speed, getY());
            }
            
            // Cek jarak untuk serangan
            if (Math.abs(skeletonX - nightBorneX) < attackRange) {
                isAttacking = true;
                frameIndex = 0;
            }
        }
    }
    
    public void act() {
        applyGravity();
        
        if (isAttacking) {
            animateAttack();
        } else {
            chaseNightBorne();
            animateRun();
        }
    }
}
