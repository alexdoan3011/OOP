package alex.shooter.Projectile;

import alex.shooter.ShooterGame;

public class Projectile {
    double projectileSpeed;
    double remainTime;
    boolean expired = false;
    double speedX;
    double speedY;
    double x;
    double y;
    double timeOfShot;
    int gun;
    int damage;
    private int health = 1;

    public Projectile(double speedX, double speedY, double x, double y, double remainTime, int damage, double projectileSpeed) {
        this.speedX = speedX;
        this.speedY = speedY;
        this.remainTime = remainTime;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.projectileSpeed = projectileSpeed;
        timeOfShot = System.nanoTime();
        gun = ShooterGame.getGunUsing();
    }

    public double getProjectileSpeed() {
        return projectileSpeed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getGun() {
        return gun;
    }

    public int getDamage() {
        return damage;
    }

    public void updateHealth() {
        health--;
    }

    public int getHealth() {
        return health;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public boolean isExpired() {
        return expired;
    }

    public void update() {
        x += speedX;
        y += speedY;
        if ((System.nanoTime() - timeOfShot) / Math.pow(10, 8) > remainTime) {
            expired = true;
        }
    }

}
