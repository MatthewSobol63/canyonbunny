package cpm.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cpm.packtpub.libgdx.canyonbunny.game.Assets;

/**
 * Created by Felix on 11.07.2015.
 */
public class Feather extends AbstractGameObject {
    public boolean collected;
    private TextureRegion regFeather;

    public Feather() {
        init();
    }

    private void init() {
        dimension.set(0.5f, 0.5f);
        regFeather = Assets.instance.feather.feather;
        // Set bounding box for collision detection
        bounds.set(0, 0, dimension.x, dimension.y);
        collected = false;
    }

    @Override
    public void render (SpriteBatch batch) {
        if (collected)
            return;

        batch.draw(regFeather.getTexture(), position.x, position.y,
                origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, regFeather.getRegionX(), regFeather.getRegionY(),
                regFeather.getRegionWidth(), regFeather.getRegionHeight(), false, false);
    }

    public int getScore() {
        return 250;
    }
}
