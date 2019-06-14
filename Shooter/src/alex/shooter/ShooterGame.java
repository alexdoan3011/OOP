package alex.shooter;

import alex.shooter.Enemy.Enemy;
import alex.shooter.Enemy.Regular;
import alex.shooter.Enemy.Stalker;
import alex.shooter.Enemy.Tank;
import alex.shooter.Projectile.Projectile;
import alex.shooter.Weapon.AssaultRifle;
import alex.shooter.Weapon.Shotgun;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class ShooterGame extends Application {
    private static final int INITIAL_WIDTH = 1200;
    private static final int INITIAL_HEIGHT = 800;
    private static final double PLAYER_SPEED = 2;
    private static final double SPRINT_MULTIPLIER = 5;
    private static final double PLAYER_RADIUS = 20;
    private static final double WEAPON_LENGTH = 25;
    private static final double STAMINA_MAX = 400;
    private static final double STAMINA_RECOV = 5;
    private static final double HEALTH_RECOV = 1;
    private static final double STAMINA_RECOV_DELAY = 5;
    private static final double HEALTH_RECOV_DELAY = 5;
    private static final double BLOOD_SPEED = 0.1;
    private static final double BLOOD_SPREAD = 3;
    private static final int PLAYER_HEALTH_MAX = 400;
    private static final int SPAWN_PER_MIN_ORIGINAL = 1;
    private static final int LEVEL_INCREASE_RATE = 10;
    private static final double FLASH = 3;
    private static final int ENEMIES_PER_STALKER = 10;
    private static final int ENEMIES_PER_TANK = 10;
    private static int counterToNextLevel;
    private static double shotPerMin;
    private static int playerHealth = 400;
    private static int spawnPerMin = 1;
    private static int kill = 0;
    private static double stamina = 400;
    private static double lastTimeDamaged;
    private static double xAtMouse;
    private static double yAtMouse;
    private static double gameStartedTime;
    private static int gunUsing = 0; //0 - Assault rifle, 1 - Shotgun
    private static ArrayList<Projectile> projectiles = new ArrayList<>();
    private boolean shiftHeldDown = false;
    private boolean directionalHeldDown = false;
    private ArrayList<Projectile> blood = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Double> trailX = new ArrayList<>();
    private ArrayList<Double> trailY = new ArrayList<>();
    private double playerXPos;
    private double playerYPos;
    private double mouseXPos;
    private double mouseYPos;
    private boolean up, down, left, right, isSprinting, shooting;
    private boolean gameStarted = false;
    private boolean gamePaused = false;
    private double lastTimeSprint;
    private double lastTimeShot;
    private double lastTimeSpawnEnemy;
    private boolean playerKilled;
    private boolean themePlaying;

    public static void main(String... args) {
        Application.launch(ShooterGame.class, args);
    }

    public static int getGunUsing() {
        return gunUsing;
    }

    public void start(Stage stage) {
        ResizableCanvas canvas = new ResizableCanvas(INITIAL_WIDTH, INITIAL_HEIGHT);
        canvas.setFocusTraversable(true);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            try {
                run(gc);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }));

        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    up = true;
                    directionalHeldDown = true;
                    break;
                case A:
                    left = true;
                    directionalHeldDown = true;
                    break;
                case S:
                    down = true;
                    directionalHeldDown = true;
                    break;
                case D:
                    right = true;
                    directionalHeldDown = true;
                    break;
                case SHIFT:
                    shiftHeldDown = true;
                    break;
                case SPACE:
                    gamePaused = !gamePaused;
                    break;
			default:
				break;
            }
        });
        canvas.setOnMouseMoved(mouseEvent -> {
                    mouseXPos = mouseEvent.getX();
                    mouseYPos = mouseEvent.getY();
                }
        );
        canvas.setOnMouseDragged(mouseEvent -> {
                    mouseXPos = mouseEvent.getX();
                    mouseYPos = mouseEvent.getY();
                }
        );
        canvas.setOnScroll(scrollEvent -> {
            if (scrollEvent.getDeltaY() > 0) {
                gunUsing = 0;
            } else {
                gunUsing = 1;
            }
        });
        canvas.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W:
                    up = false;
                    if (!(left || down || right)) {
                        directionalHeldDown = false;
                    }
                    break;
                case A:
                    left = false;
                    if (!(up || down || right)) {
                        directionalHeldDown = false;
                    }
                    break;
                case S:
                    down = false;
                    if (!(up || left || right)) {
                        directionalHeldDown = false;
                    }
                    break;
                case D:
                    right = false;
                    if (!(up || left || down)) {
                        directionalHeldDown = false;
                    }
                    break;
                case SHIFT:
                    shiftHeldDown = false;
                    break;
			default:
				break;
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gamePaused) {
                    isSprinting = directionalHeldDown && shiftHeldDown && stamina > 0;
                    if (isSprinting) {
                        lastTimeSprint = 0;
                    } else if (lastTimeSprint == 0) {
                        lastTimeSprint = System.nanoTime();
                    }
                    double diagonal = Math.sqrt(PLAYER_SPEED);
                    int dx = 0, dy = 0;
                    if (up & left) {
                        dy -= diagonal;
                        dx -= diagonal;
                    } else if (up & right) {
                        dy -= diagonal;
                        dx += diagonal;
                    } else if (down & left) {
                        dy += diagonal;
                        dx -= diagonal;
                    } else if (down & right) {
                        dy += diagonal;
                        dx += diagonal;
                    } else if (up) {
                        dy -= PLAYER_SPEED;
                    } else if (down) {
                        dy += PLAYER_SPEED;
                    } else if (left) {
                        dx -= PLAYER_SPEED;
                    } else if (right) {
                        dx += PLAYER_SPEED;
                    }
                    if (isSprinting & stamina > 0) {
                        trailX.add(playerXPos);
                        trailY.add(playerYPos);
                        dx *= SPRINT_MULTIPLIER;
                        dy *= SPRINT_MULTIPLIER;
                        stamina--;
                    }
                    moveCharBy(dx, dy);
                    /*stamina recovery delay control*/
                    double staminaRecovReload = (now - lastTimeSprint) / Math.pow(10, 8) / STAMINA_RECOV_DELAY;
                    if (staminaRecovReload > 1) {
                        staminaRecovReload = 1;
                    }
                    if (gameStarted && staminaRecovReload == 1 && !isSprinting && (!(shiftHeldDown && directionalHeldDown)) && stamina < STAMINA_MAX) {
                        stamina += STAMINA_RECOV;
                    }
                    /*health recovery delay control*/
                    double healthRecovReload = (now - lastTimeDamaged) / Math.pow(10, 8) / HEALTH_RECOV_DELAY;
                    if (healthRecovReload > 1) {
                        healthRecovReload = 1;
                    }
                    if (gameStarted && healthRecovReload == 1 && playerHealth < PLAYER_HEALTH_MAX) {
                        playerHealth += HEALTH_RECOV;
                    }
                    /*fire rate control*/
                    double reloadRate = (now - lastTimeShot) / Math.pow(10, 9) / (60.0 / shotPerMin);
                    if (reloadRate > 1) {
                        reloadRate = 1;
                    }
                    if (gameStarted && reloadRate == 1 && shooting) {
                        if (gunUsing == 0) {
                            /*play assault rifle sound*/
                            Sound.stopSound(1);
                            Sound.playSound(1);
                            createProjectile();
                        } else {
                            /*play shotgun sound*/
                            Sound.stopSound(2);
                            Sound.playSound(2);
                            for (int i = 0; i < Shotgun.PELLETS_PER_SHOT; i++) {
                                createProjectile();
                            }
                        }
                    }
                    /*enemy spawn rate control*/
                    if (gameStarted) {
                        double respawnRate = (now - lastTimeSpawnEnemy) / Math.pow(10, 8) / (60.0 / spawnPerMin);
                        if (respawnRate > 1) {
                            respawnRate = 1;
                        }
                        if (respawnRate == 1) {
                            zombieSpawner(canvas.getWidth(), canvas.getHeight());
                            if ((long) ((System.nanoTime() - gameStartedTime) / Math.pow(10, 9)) > counterToNextLevel) {
                                counterToNextLevel += LEVEL_INCREASE_RATE;
                                spawnPerMin++;
                            }
                        }
                    }
                }
            }
        };
        timer.start();
        canvas.setOnMousePressed(e -> {
            if (gameStarted && e.getButton() == MouseButton.PRIMARY) {
                shooting = true;
            } else if (e.getButton() == MouseButton.SECONDARY) {
                if (gunUsing == 0) {
                    gunUsing = 1;
                } else {
                    gunUsing = 0;
                }
            } else {
                shooting = false;
                gameStarted = true;
                playerXPos = canvas.getWidth() / 2;
                playerYPos = canvas.getHeight() / 2;
            }
        });
        canvas.setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                shooting = false;
            }
        });
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(canvas);
        /* Bind canvas size to stack pane size */
        canvas.widthProperty().bind(
                stackPane.widthProperty());
        canvas.heightProperty().bind(
                stackPane.heightProperty());

        stage.setScene(new Scene(stackPane));
        stage.setTitle("Generic zombie shooter");
        stage.show();
        tl.play();
    }

    private void moveCharBy(double x, double y) {
        playerXPos += x;
        playerYPos += y;
    }

    private void impactSplash(Projectile projectile, double xSplash, double ySplash) {
        for (int i = 1; i < 3; i++) {
            double speedX = (-projectile.getSpeedX() + (new Random().nextBoolean() ? 1 : -1) * (new Random().nextDouble()) * BLOOD_SPREAD) * BLOOD_SPEED;
            double speedY = (-projectile.getSpeedY() + (new Random().nextBoolean() ? 1 : -1) * (new Random().nextDouble()) * BLOOD_SPREAD) * BLOOD_SPEED;
            Projectile blood = new Projectile(speedX, speedY, xSplash, ySplash, 2, 0, BLOOD_SPEED);
            blood.updateHealth();
            this.blood.add(blood);
        }
    }

    private void createProjectile() {
        lastTimeShot = System.nanoTime();
        Projectile projectile;
        double speedX;
        double speedY;
        if (gunUsing == 0) {
            speedX = (xAtMouse + (new Random().nextBoolean() ? 1 : -1) * (new Random().nextDouble()) * AssaultRifle.ASSAULT_RIFLE_SPREAD) * AssaultRifle.ASSAULT_RIFLE_PROJ_SPEED;
            speedY = (yAtMouse + (new Random().nextBoolean() ? 1 : -1) * (new Random().nextDouble()) * AssaultRifle.ASSAULT_RIFLE_SPREAD) * AssaultRifle.ASSAULT_RIFLE_PROJ_SPEED;
            projectile = new Projectile(speedX, speedY, playerXPos + PLAYER_RADIUS + WEAPON_LENGTH * xAtMouse, playerYPos + PLAYER_RADIUS + WEAPON_LENGTH * yAtMouse, 50, AssaultRifle.ASSAULT_RIFLE_PROJECTILE_DAMAGE, AssaultRifle.ASSAULT_RIFLE_PROJ_SPEED);
        } else {
            speedX = (xAtMouse + (new Random().nextBoolean() ? 1 : -1) * (new Random().nextDouble()) * Shotgun.SHOTGUN_SPREAD) * Shotgun.SHOTGUN_PROJ_SPEED;
            speedY = (yAtMouse + (new Random().nextBoolean() ? 1 : -1) * (new Random().nextDouble()) * Shotgun.SHOTGUN_SPREAD) * Shotgun.SHOTGUN_PROJ_SPEED;
            projectile = new Projectile(speedX, speedY, playerXPos + PLAYER_RADIUS + WEAPON_LENGTH * xAtMouse, playerYPos + PLAYER_RADIUS + WEAPON_LENGTH * yAtMouse, new Random().nextDouble() * 3 + 1, Shotgun.SHOTGUN_PROJECTILE_DAMAGE, Shotgun.SHOTGUN_PROJ_SPEED);
        }
        projectiles.add(projectile);
    }

    private void zombieSpawner(double width, double height) {
        Enemy enemy;
        lastTimeSpawnEnemy = System.nanoTime();
        double[] coordinate;
        int randomStalker = new Random().nextInt(ENEMIES_PER_STALKER);
        int randomTank = new Random().nextInt(ENEMIES_PER_TANK);
        if (randomTank != 0) {
            if (randomStalker != 0) {
                coordinate = zombieSpawnPosRandomizer(Regular.getZOMBIE_RADIUS(), width, height);
                enemy = new Regular(coordinate[0], coordinate[1]);
            } else {
                coordinate = zombieSpawnPosRandomizer(Stalker.getZOMBIE_RADIUS(), width, height);
                enemy = new Stalker(coordinate[0], coordinate[1]);
            }
        } else {
            coordinate = zombieSpawnPosRandomizer(Tank.getZOMBIE_RADIUS(), width, height);
            enemy = new Tank(coordinate[0], coordinate[1]);
        }
        enemies.add(enemy);
    }

    private double[] zombieSpawnPosRandomizer(double zombieRadius, double width, double height) {
        double[] coordinate = new double[2];
        int spawnPos = new Random().nextInt(4) + 1;
        if (spawnPos == 1) {
            coordinate[0] = new Random().nextDouble() * width;
            coordinate[1] = 0 - zombieRadius;
        } else if (spawnPos == 2) {
            coordinate[0] = new Random().nextDouble() * width;
            coordinate[1] = height + zombieRadius;
        } else if (spawnPos == 3) {
            coordinate[0] = 0 - zombieRadius;
            coordinate[1] = new Random().nextDouble() * height;
        } else {
            coordinate[0] = width + zombieRadius;
            coordinate[1] = new Random().nextDouble() * width;
        }
        return coordinate;
    }

    private boolean playerDamage() {
        for (Enemy enemy : enemies) {
            if (enemy.getDistance(playerXPos + PLAYER_RADIUS, playerYPos + PLAYER_RADIUS) < (enemy.getZombieRadius() + PLAYER_RADIUS) && enemy.getHealth() > 0) {
                playerHealth--;
                return true;
            }
        }
        return false;
    }

    private void boundaryManager(double width, double height) {
        if (playerXPos < 0) {//when player is trying to leave left boundary
            playerXPos = 0;
            lastTimeSprint = System.nanoTime();
        }
        if (playerXPos > width - 2 * PLAYER_RADIUS) {//when player is trying to leave right boundary
            playerXPos = width - 2 * PLAYER_RADIUS;
            lastTimeSprint = System.nanoTime();
        }
        if (playerYPos < 0) {//when player is trying to leave upper boundary
            playerYPos = 0;
            lastTimeSprint = System.nanoTime();
        }
        if (playerYPos > height - 2 * PLAYER_RADIUS) {//when player is trying to leave lower boundary
            playerYPos = height - 2 * PLAYER_RADIUS;
            lastTimeSprint = System.nanoTime();
        }
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        if (gameStarted && !gamePaused) {
            if (themePlaying) {
                Sound.stopSound(3);
                themePlaying = false;
            }
            gc.setFill(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font(30));
            gc.fillText("press SPACE to pause", gc.getCanvas().getWidth() / 2, 50);
            if (gameStartedTime == 0) {
                gameStartedTime = System.nanoTime();
            }
            blood.forEach(Projectile::update);
            /*flashing yellow when player runs out of stamina*/
            double lastTimeFatigue = 0;
            if (stamina == 0) {
                lastTimeFatigue = System.nanoTime();
            }
            double flashFatigue = (System.nanoTime() - lastTimeFatigue) / Math.pow(10, 8) / FLASH;
            if (flashFatigue > 1) {
                flashFatigue = 1;
            }
            Color colorFatigue = new Color(0.2, 0.2, 0, 1 - flashFatigue);
            gc.setFill(colorFatigue);
            gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            /*flashing red when player takes damage*/
            if (playerDamage()) {
                lastTimeDamaged = System.nanoTime();
            }
            double flashDamage = (System.nanoTime() - lastTimeDamaged) / Math.pow(10, 8) / FLASH;
            if (flashDamage > 1) {
                flashDamage = 1;
            }
            Color colorDamage = new Color(0.2, 0, 0, 1 - flashDamage);
            gc.setFill(colorDamage);
            gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            /*when player dies*/
            if (playerKilled) {
                spawnPerMin = SPAWN_PER_MIN_ORIGINAL;
                enemies.clear();
                playerHealth = PLAYER_HEALTH_MAX;
                stamina = STAMINA_MAX;
                kill = 0;
                playerKilled = false;
            }
            if (playerHealth <= 0) {
                gameStarted = false;
                playerKilled = true;
            }
            /*remove hit or expired projectiles*/
            for (int i = projectiles.size() - 1; i >= 0; i--) {
                if (projectiles.get(i).getHealth() == 0 || projectiles.get(i).isExpired()) {
                    projectiles.remove(i);
                }
            }

            /*remove expired blood*/
            for (int i = blood.size() - 1; i >= 0; i--) {
                if (blood.get(i).isExpired()) {
                    blood.remove(i);
                }
            }

            /*remove expired enemies*/
            for (int j = enemies.size() - 1; j >= 0; j--) {
                if (enemies.get(j).getHealth() == 0 && !enemies.get(j).isCounted()) {
                    enemies.get(j).setCounted();
                    kill++;
                }
                if (enemies.get(j).isExpired()) {
                    enemies.remove(j);
                }
            }
            /*update projectiles and enemies*/
            projectiles.forEach(Projectile::update);
            enemies.forEach(e ->
                    e.update(playerXPos + PLAYER_RADIUS, playerYPos + PLAYER_RADIUS)
            );
            /*limit player's movement*/
            boundaryManager(gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            /*draw top text*/
            gc.setFill(Color.WHITE);
            gc.setTextAlign(TextAlignment.LEFT);
            gc.fillText("KILLS: " + kill + "    Level: " + spawnPerMin, 20, 50);
            gc.setTextAlign(TextAlignment.RIGHT);
            if (gunUsing == 0) {
                gc.fillText("ASSAULT RIFLE", gc.getCanvas().getWidth() - 20, 50);
                shotPerMin = AssaultRifle.SHOT_PER_MIN_ASSAULT_RIFLE;
            } else {
                shotPerMin = Shotgun.SHOT_PER_MIN_SHOTGUN;
                gc.fillText("SHOTGUN", gc.getCanvas().getWidth() - 20, 50);
            }
            /*draw sprint trail*/
            for (int i = trailX.size() - 1; i >= 0; i--) {
                Color colorTrail = new Color(0.5, 0.5, 0.5, (double) i / trailX.size());
                gc.setFill(colorTrail);
                gc.fillOval(trailX.get(i), trailY.get(i), PLAYER_RADIUS * 2, PLAYER_RADIUS * 2);
            }
            /*draw player*/
            gc.setFill(Color.WHITE);
            gc.fillOval(playerXPos, playerYPos, PLAYER_RADIUS * 2, PLAYER_RADIUS * 2);
            /*draw player weapon*/
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(5);
            double c = mouseXPos - (playerXPos + PLAYER_RADIUS);
            double d = mouseYPos - (playerYPos + PLAYER_RADIUS);
            yAtMouse = Math.sqrt(1 / (1 + (c * c / d / d))) * Math.signum(d);
            xAtMouse = yAtMouse * c / d;
            gc.strokeLine(playerXPos + PLAYER_RADIUS, playerYPos + PLAYER_RADIUS, playerXPos + PLAYER_RADIUS + WEAPON_LENGTH * xAtMouse, playerYPos + PLAYER_RADIUS + WEAPON_LENGTH * yAtMouse);
            /*draw enemies*/
            for (Enemy enemy : enemies) {
                if (enemy instanceof Regular) {
                    Color colorRegular = new Color(0, 0.5, 0, 1 - enemy.getDeathRate());
                    gc.setFill(colorRegular);
                    gc.fillOval(enemy.getX() - enemy.getZombieRadius(), enemy.getY() - enemy.getZombieRadius(), enemy.getZombieRadius() * 2, enemy.getZombieRadius() * 2);
                } else if (enemy instanceof Stalker) {
                    Color colorStalker = new Color(0.5, 0, 0, 1 - enemy.getDeathRate());
                    gc.setFill(colorStalker);
                    gc.fillOval(enemy.getX() - enemy.getZombieRadius(), enemy.getY() - enemy.getZombieRadius(), enemy.getZombieRadius() * 2, enemy.getZombieRadius() * 2);
                } else {
                    Color colorTank = new Color(1, 0.5, 0, 1 - enemy.getDeathRate());
                    gc.setFill(colorTank);
                    gc.fillOval(enemy.getX() - enemy.getZombieRadius(), enemy.getY() - enemy.getZombieRadius(), enemy.getZombieRadius() * 2, enemy.getZombieRadius() * 2);
                }
            }
            /*draw projectiles*/
            for (int i = projectiles.size() - 1; i >= 0; i--) {
                gc.setLineWidth(3);
                if (projectiles.get(i).getGun() == 0) {
                    gc.setStroke(Color.CYAN);
                } else {
                    gc.setStroke(Color.YELLOW);
                }
                gc.strokeLine(projectiles.get(i).getX(), projectiles.get(i).getY(), projectiles.get(i).getX() - projectiles.get(i).getSpeedX(), projectiles.get(i).getY() - projectiles.get(i).getSpeedY());
                for (int j = enemies.size() - 1; j >= 0; j--) {
                    if ((Math.sqrt(Math.pow(projectiles.get(i).getX() - enemies.get(j).getX(), 2) + Math.pow(projectiles.get(i).getY() - enemies.get(j).getY(), 2)) < enemies.get(j).getZombieRadius() && enemies.get(j).getDistance(playerXPos + PLAYER_RADIUS, playerYPos + PLAYER_RADIUS) >= PLAYER_RADIUS + enemies.get(j).getZombieRadius() + WEAPON_LENGTH) && enemies.get(j).getHealth() > 0) {
                        projectiles.get(i).updateHealth();
                        enemies.get(j).updateHealth(projectiles.get(i).getDamage());
                        impactSplash(projectiles.get(i), projectiles.get(i).getX(), projectiles.get(i).getY());
                        Sound.stopSound(0);
                        Sound.playSound(0);
//                            if (new Random().nextInt(30) == 0 && soundPlayedPerShot < 3) {
//                                Sound.playSound(0);
//                                soundPlayedPerShot++;
//                                System.out.println(soundPlayedPerShot);
//                            }
//                            if (soundPlayedPerShot == 0) {
//                                Sound.playSound(0);
//                            }
//                        System.out.println(System.getProperty("user.dir"));
//                        System.out.println(Sound.class.getClassLoader().getResource("HITMARKER.wav").toString());
//                        new AudioClip(Sound.HIT_WALL).play();
                        if (enemies.get(j).getHealth() > 1 && !(enemies.get(j) instanceof Tank)) {
                            enemies.get(j).moveX(projectiles.get(i).getSpeedX() * 0.5);
                            enemies.get(j).moveY(projectiles.get(i).getSpeedY() * 0.5);
                        } else if (enemies.get(j).getHealth() <= 1 || ((enemies.get(j) instanceof Tank) && (enemies.get(j).getHealth() <= 10))) {
                            enemies.get(j).moveX(projectiles.get(i).getSpeedX() * 1);
                            enemies.get(j).moveY(projectiles.get(i).getSpeedY() * 1);
                        }
                    }
                }
            }
            /* draw blood */
            for (int i = blood.size() - 1; i >= 0; i--) {
                gc.setFill(Color.RED);
                gc.fillOval(blood.get(i).getX(), blood.get(i).getY(), 2, 2);
            }
            /*draw stamina bar*/
            gc.setLineWidth(2);
            gc.setFill(Color.WHITE);
            gc.setStroke(Color.WHITE);
            gc.strokeRect(20, gc.getCanvas().getHeight() - 20, STAMINA_MAX / 2, 10);
            gc.fillRect(20, gc.getCanvas().getHeight() - 20, stamina / 2, 10);
            gc.strokeRect(20, gc.getCanvas().getHeight() - 20, STAMINA_MAX / 2, 10);
            /*draw health bar*/
            gc.setFill(Color.RED);
            gc.setStroke(Color.WHITE);
            gc.strokeRect(250, gc.getCanvas().getHeight() - 20, PLAYER_HEALTH_MAX / 2, 10);
            gc.fillRect(250, gc.getCanvas().getHeight() - 20, playerHealth / 2, 10);
            /*limit trail size*/
            if (trailX.size() > 10) {
                trailX.remove(0);
                trailY.remove(0);
            }
            if ((!isSprinting || stamina == 0) && trailX.size() > 0) {
                trailX.remove(0);
                trailY.remove(0);
            }
        } else if (!gamePaused) {
            if (!themePlaying) {
                Sound.playSound(3);
                themePlaying = true;
            }
            gameStartedTime = 0;
            trailX.clear();
            trailY.clear();
            shooting = false;
            projectiles.clear();
            gc.setFill(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font(20));
            gc.fillText("hold SHIFT to sprint", gc.getCanvas().getWidth() / 2, 50);
            gc.fillText("RIGHT CLICK / SCROLL to switch gun", gc.getCanvas().getWidth() / 2, 75);
            gc.setFont(Font.font(50));
            if (playerKilled) {
                gc.fillText("CLICK TO RETRY", gc.getCanvas().getWidth() / 2, gc.getCanvas().getHeight() / 2 + 50);
                gc.setFill(Color.ORANGERED);
                gc.fillText("YOU DIED", gc.getCanvas().getWidth() / 2, gc.getCanvas().getHeight() / 2 - 50);
                if (!themePlaying) {
                    Sound.playSound(3);
                    themePlaying = true;
                }
            } else {
                gc.fillText("CLICK TO START", gc.getCanvas().getWidth() / 2, gc.getCanvas().getHeight() / 2);
            }
        } else {
            gc.setFill(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font(50));
            gc.fillText("PAUSE", gc.getCanvas().getWidth() / 2, gc.getCanvas().getHeight() / 2);
        }

    }

    class ResizableCanvas extends Canvas {

        ResizableCanvas(double width, double height) {
            setWidth(width);
            setHeight(height);
        }

        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double prefWidth(double height) {
            return getWidth();
        }

        @Override
        public double prefHeight(double width) {
            return getHeight();
        }
    }
}
