    import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
    import java.util.ArrayList;
    
    public class Goblin extends Actor {
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
        private int deathAnimationSpeed = 10;
        private int counter = 0;
        private int speed = 2; // Kecepatan Goblin
        private int attackRange = 15; // Jarak serang
        private int invincibleTime = 25; // Jeda setelah terkena damage (dalam frame)
        private int invincibleCounter = 0;
        
        private int verticalSpeed = 0; // Current vertical speed
        private int gravity = 1; // Force of gravity
        private int groundLevel = 520; // Adjusted ground level
        private boolean onGround = false;
        private boolean isAttacking = false;
        private boolean isDead = false; // Status kematian
        private int health = 100;
        
        public Goblin() {
            loadImages();        
            setImage(idleLeftImages.get(0));
        }
        
        private void loadImages() {
            for (int i = 0; i <= 3; i++) {
                idleRightImages.add(scaleImage(new GreenfootImage("Character/Goblin/Goblin_idle/Goblin_Idle" + i + ".png")));
            }
            for (int i = 0; i <= 7; i++) {
                runRightImages.add(scaleImage(new GreenfootImage("Character/Goblin/Goblin_run/Goblin_Run" + i + ".png")));
            }
            for (int i = 0; i <= 3; i++) {
                hurtRightImages.add(scaleImage(new GreenfootImage("Character/Goblin/Goblin_hurt/Goblin_Hurt" + i + ".png")));
            }
            for (int i = 0; i <= 7; i++) {
                attackRightImages.add(scaleImage(new GreenfootImage("Character/Goblin/Goblin_attack/Goblin_Attack" + i + ".png")));
            }
            for (int i = 0; i <= 3; i++) {
                deathRightImages.add(scaleImage(new GreenfootImage("Character/Goblin/Goblin_death/Goblin_Death" + i + ".png")));
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
        
        private void animateDeath() {
            counter++;
            if (counter % deathAnimationSpeed == 0) {
                if (frameIndex >= deathRightImages.size()) {
                    isDead = true;
                    isAttacking = false;
                    getWorld().removeObject(this);
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
                int goblinX = getX();
                
                // Tentukan arah gerakan
                if (goblinX < nightBorneX) {
                    facingDirection = "right";
                    setLocation(getX() + speed, getY());
                } else if (goblinX > nightBorneX) {
                    facingDirection = "left";
                    setLocation(getX() - speed, getY());
                }
                
                // Cek jarak untuk serangan
                if (Math.abs(goblinX - nightBorneX) < attackRange) {
                    isAttacking = true;
                    frameIndex = 0;
                }
            }
        }
        
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
        
        public void act() {
            if (isDead) {
                animateDeath();
                return;
            }
            
            if (invincibleCounter > 0) {
                invincibleCounter--; 
            }
            
            applyGravity();
            
            NightBorne nightborne = (NightBorne) getOneIntersectingObject(NightBorne.class);
            if (nightborne != null) {
                takeDamage(10);
            }
            
            if (isAttacking) {
                animateAttack();
            } else {
                chaseNightBorne();
                animateRun();
            }
        }
    }