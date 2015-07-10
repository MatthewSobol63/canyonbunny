package cpm.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import cpm.packtpub.libgdx.canyonbunny.game.objects.AbstractGameObject;
import cpm.packtpub.libgdx.canyonbunny.game.objects.Clouds;
import cpm.packtpub.libgdx.canyonbunny.game.objects.Mountains;
import cpm.packtpub.libgdx.canyonbunny.game.objects.Rock;
import cpm.packtpub.libgdx.canyonbunny.game.objects.WaterOverlay;

/**
 * Created by Felix on 04.07.2015.
 */
public class Level {
    private static final String TAG = Level.class.getName();
    private static final int BLOCK_EMPTY = 0x000000FF; //black
    private static final int BLOCK_ROCK = 0x00FF00FF; //green
    private static final int BLOCK_PLAYER_SPAWNPOINT = 0xFFFFFFFF; //white
    private static final int BLOCK_ITEM_FEATHER = 0xFF00FFFF; //purple
    private static final int BLOCK_ITEM_GOLD_COIN = 0xFFFF00FF; //yellow

    // objects
    public Array<Rock> rocks;
    // decoration
    public Clouds clouds;
    public Mountains mountains;
    public WaterOverlay waterOverlay;

    public Level (String filename) {
        init(filename);
    }
    private void init (String filename) {
        // objects
        rocks = new Array<Rock>();
        // load image file that represents the level data
        Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
        // scan pixels from top-left to bottom-right
        int lastPixel = -1;
        for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
            for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
                AbstractGameObject obj = null;
                float offsetHeight = 0;
                // height grows from bottom to top
                float baseHeight = pixmap.getHeight() - pixelY;
                // get color of current pixel as 32-bit RGBA value
                int currentPixel = pixmap.getPixel(pixelX, pixelY);
                // find matching color value to identify block type at (x,y)
                // point and create the corresponding game object if there is
                // a match
                // empty space

                switch (currentPixel) {
                    case Level.BLOCK_EMPTY:
                        break;
                    case Level.BLOCK_ROCK:
                        if (lastPixel != currentPixel) {
                            obj = new Rock();
                            float heightIncreaseFactor = 0.25f;
                            offsetHeight = -2.5f;
                            obj.position.set(pixelX, baseHeight * obj.dimension.y
                                    * heightIncreaseFactor + offsetHeight);
                            rocks.add((Rock)obj);
                        } else {
                            rocks.get(rocks.size - 1).increaseLength(1);
                        }
                        break;
                    case Level.BLOCK_PLAYER_SPAWNPOINT:
                        break;
                    case Level.BLOCK_ITEM_FEATHER:
                        break;
                    case Level.BLOCK_ITEM_GOLD_COIN:
                        break;
                    default:
                        int red = 0xff & (currentPixel >>> 24); //red color channel
                        int green = 0xff & (currentPixel >>> 16); //green color channel
                        int blue = 0xff & (currentPixel >>> 8); //blue color channel
                        int alpha = 0xff & currentPixel; //alpha channel
                        Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<"
                                + pixelY + ">: r<" + red + "> g<" + green + "> b<" + blue + "> a<" + alpha + ">");
                        break;
                }
                lastPixel = currentPixel;
            }
        }

        // decoration
        clouds = new Clouds(pixmap.getWidth());
        clouds.position.set(0, 2);
        mountains = new Mountains(pixmap.getWidth());
        mountains.position.set(-1, -1);
        waterOverlay = new WaterOverlay(pixmap.getWidth());
        waterOverlay.position.set(0, -3.75f);
        // free memory
        pixmap.dispose();
        Gdx.app.debug(TAG, "level '" + filename + "' loaded");
    }

    public void render (SpriteBatch batch) {
        mountains.render(batch);
        for (Rock rock : rocks) {
            rock.render(batch);
        }
        waterOverlay.render(batch);
        clouds.render(batch);
    }
}
