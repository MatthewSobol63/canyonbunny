package cpm.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import cpm.packtpub.libgdx.canyonbunny.game.Assets;

/**
 * Created by Felix on 04.07.2015.
 */
public class WaterOverlay extends AbstractGameObject {
    private TextureRegion regOverlay;
    private int length;

    public WaterOverlay(int length) {
        this.length = length;
        init();
    }

    private void init() {
        dimension.set(length * 10, 3);
        regOverlay = Assets.instance.levelDecoration.waterOverlay;
        origin.x = -dimension.x / 2;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(regOverlay.getTexture(), position.x + origin.x, position.y + origin.y,
                origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, regOverlay.getRegionX(), regOverlay.getRegionY(),
                regOverlay.getRegionWidth(), regOverlay.getRegionHeight(), false, false);
    }
}
