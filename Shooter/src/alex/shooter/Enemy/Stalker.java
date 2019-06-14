package alex.shooter.Enemy;

public class Stalker extends Enemy {
    private static final double ZOMBIE_RADIUS = 20;
    private static final double ZOMBIE_SPEED = 3;
    private int health = 5;

    public Stalker(double x, double y) {
        this.x = x;
        this.y = y;
        zombieRadius = ZOMBIE_RADIUS;
    }

    public static double getZOMBIE_RADIUS() {
        return ZOMBIE_RADIUS;
    }

    public int getHealth() {
        return health;
    }

    public void updateHealth(int damage) {
        health -= damage;
    }

    public void update(double playerCenterXPos, double playerCenterYPos) {
        if (health > 0) {
            double c = playerCenterXPos - x;
            double d = playerCenterYPos - y;
            double yAtPlayer = Math.sqrt(1 / (1 + (c * c / d / d))) * Math.signum(d);
            double xAtPlayer = yAtPlayer * c / d;
            x += xAtPlayer * ZOMBIE_SPEED;
            y += yAtPlayer * ZOMBIE_SPEED;
        } else {
            if (timeOfDeath == 0) {
                timeOfDeath = System.nanoTime();
            }
        }
    }
}
