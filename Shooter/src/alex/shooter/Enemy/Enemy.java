package alex.shooter.Enemy;

public abstract class Enemy {
    static final double REMAIN_TIME = 10;
    double zombieRadius;
    boolean expired = false;
    double timeOfDeath;
    double x;
    double y;
    private boolean counted = false;

    public double getDistance(double playerCenterXPos, double playerCenterYPos) {
        double c = playerCenterXPos - x;
        double d = playerCenterYPos - y;
        return Math.sqrt(c * c + d * d);
    }

    public double getDeathRate() {
        double deathRate;
        if (getHealth() > 0) {
            deathRate = 0;
        } else {
            deathRate = (System.nanoTime() - timeOfDeath) / Math.pow(10, 8) / REMAIN_TIME;
        }
        if (deathRate <= 1) {
            return deathRate;
        } else {
            expired = true;
            return 1;
        }
    }

    public double getZombieRadius() {
        return zombieRadius;
    }

    public double getX() {
        return x;
    }

    public void moveX(double x) {
        this.x += x;
    }

    public double getY() {
        return y;
    }

    public void moveY(double y) {
        this.y += y;
    }

    public abstract int getHealth();

    public abstract void updateHealth(int damage);

    public abstract void update(double playerCenterXPos, double playerCenterYPos);

    public void setCounted() {
        counted = true;
    }

    public boolean isCounted() {
        return counted;
    }

    public boolean isExpired() {
        return expired;
    }
}
