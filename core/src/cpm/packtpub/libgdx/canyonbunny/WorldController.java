package cpm.packtpub.libgdx.canyonbunny;

/**
 * Created by Felix_2 on 29.06.2015.
 */

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;

import cpm.packtpub.libgdx.canyonbunny.game.Level;
import cpm.packtpub.libgdx.canyonbunny.game.objects.AbstractGameObject;
import cpm.packtpub.libgdx.canyonbunny.game.objects.BunnyHead;
import cpm.packtpub.libgdx.canyonbunny.game.objects.Feather;
import cpm.packtpub.libgdx.canyonbunny.game.objects.GoldCoin;
import cpm.packtpub.libgdx.canyonbunny.game.objects.Rock;
import cpm.packtpub.libgdx.canyonbunny.screens.MenuScreen;
import cpm.packtpub.libgdx.canyonbunny.util.CameraHelper;
import cpm.packtpub.libgdx.canyonbunny.util.Constants;

/**
 * Created by Felix on 27.06.2015.
 */
public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();

    public CameraHelper cameraHelper;
    public Level level;
    public int lives;
    public int score;

    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();
    private float timeLeftGameOverDelay;
    private Game game;

    public WorldController(Game game) {
        this.game = game;
        init();
    }

    private void backToMenu () {
    // switch to menu screen
        game.setScreen(new MenuScreen(game));
    }

    private void init () {
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        timeLeftGameOverDelay = 0;
        initLevel();
    }

    private void initLevel() {
        score = 0;
        level = new Level(Constants.LEVEL_01);
        cameraHelper.setTarget(level.bunnyHead);
    }

    public boolean isGameOver() {
        return (lives < 0);
    }

    public boolean isPlayerInWater() {
        return (level.bunnyHead.position.y < -5);
    }

    public void update (float deltaTime) {
        handleDebugInput(deltaTime);
        if (isGameOver()) {
            timeLeftGameOverDelay -= deltaTime;
            if (timeLeftGameOverDelay < 0) {
                backToMenu();
                return;
            }
        } else {
            handleGameInput();
        }
        level.update(deltaTime);
        testCollisions();
        cameraHelper.update(deltaTime);
        if (!isGameOver() && isPlayerInWater()) {
            lives--;
            if (isGameOver()) {
                timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
            } else {
                initLevel();
            }
        }
    }

    private void handleDebugInput (float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop)
            return;
        if (cameraHelper.hasTarget(level.bunnyHead))
            return;

        // Camera Controls (move)
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            camMoveSpeed *= camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            cameraHelper.addPosition(-camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            cameraHelper.addPosition(camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            cameraHelper.addPosition(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            cameraHelper.addPosition(0, -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE))
            cameraHelper.setPosition(0, 0);
        // Camera Controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            camZoomSpeed *= camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA))
            cameraHelper.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD))
            cameraHelper.addZoom( -camZoomSpeed);
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH))
            cameraHelper.setZoom(1);
    }

    private void handleGameInput() {
        BunnyHead bunny = level.bunnyHead;
        if (cameraHelper.hasTarget(bunny)) {
            //player movement
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                bunny.velocity.x = -bunny.terminalVelocity.x;
            } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                bunny.velocity.x = bunny.terminalVelocity.x;
            } else {
                //execute auto-forward movement for non-desktop platform
                if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
                    bunny.velocity.x  = bunny.terminalVelocity.x;
                }
            }

            //bunny jump
            boolean jumping = (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE));
            bunny.setJumping(jumping);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.R:
                Gdx.app.debug(TAG, "game world resetted");
                init();
                return true;
            case Input.Keys.ENTER:
                cameraHelper.setTarget(cameraHelper.hasTarget() ? null : level.bunnyHead);
                Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
                return true;
            case Input.Keys.ESCAPE:
            case Input.Keys.BACK:
                backToMenu();
                return true;
        }
        return false;
    }

    private void testCollisions() {
        setRectangle(r1, level.bunnyHead);

        //test collisions bunndyHead <--> rocks
        for (Rock rock : level.rocks) {
            setRectangle(r2, rock);
            if (!r1.overlaps(r2))
                continue;

            onCollisionBunnyHeadWithRock(rock);
            //IMPORTANT: must do all collesions for valid egde testing on rocks
        }
        //test collisions bunndyHead <--> gold coins
        for (GoldCoin coin : level.goldCoins) {
            if (coin.collected) continue;
            setRectangle(r2, coin);
            if (!r1.overlaps(r2)) continue;

            onCollisionBunnyHeadWithGoldCoin(coin);
            break;
        }
        //test collections bunnyHead <--> feathers
        for (Feather feather : level.feathers) {
            if (feather.collected) continue;
            setRectangle(r2, feather);
            if (!r1.overlaps(r2)) continue;

            onCollisionBunnyHeadWithFeather(feather);
            break;
        }
    }

    private void setRectangle(Rectangle rectangle, AbstractGameObject object) {
        rectangle.set(object.position.x, object.position.y,
                object.bounds.width, object.bounds.height);
    }

    private void onCollisionBunnyHeadWithRock(Rock rock) {
        BunnyHead bunny = level.bunnyHead;
        float heightDifference =
                Math.abs(bunny.position.y - (rock.position.y + rock.bounds.height));
        if (heightDifference > 0.25f) {
            boolean hitRightEdge = bunny.position.x > (rock.position.x + rock.bounds.width / 2.0f);
            if (hitRightEdge) {
                bunny.position.x = rock.position.x + rock.bounds.width;
            } else {
                bunny.position.x = rock.position.x - bunny.bounds.width;
            }
            return;
        }

        switch (bunny.jumpState) {
            case GROUNDED:
                break;
            case FALLING:
            case JUMP_FALLING:
                bunny.position.y = rock.position.y + bunny.bounds.height + bunny.origin.y;
                bunny.jumpState = BunnyHead.JumpState.GROUNDED;
                break;
            case JUMP_RISING:
                bunny.position.y = rock.position.y + bunny.bounds.height + bunny.origin.y;
                break;
        }
    }

    private void onCollisionBunnyHeadWithGoldCoin(GoldCoin goldCoin) {
        goldCoin.collected = true;
        score += goldCoin.getScore();
        Gdx.app.debug(TAG, "Gold coin collected");
    }

    private void onCollisionBunnyHeadWithFeather(Feather feather) {
        feather.collected = true;
        score += feather.getScore();
        level.bunnyHead.setFeatherPowerup(true);
        Gdx.app.debug(TAG, "Feather collected");
    }
}
