package cpm.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cpm.packtpub.libgdx.canyonbunny.game.Assets;

/**
 * Created by Felix on 04.07.2015.
 */
public class Mountains extends AbstractGameObject {
    private TextureRegion regLeft;
    private TextureRegion regRight;
    private int length;

    public Mountains(int length) {
        this.length = length;
        init();
    }

    private void init() {
        dimension.set(10, 2);

        regLeft = Assets.instance.levelDecoration.mountainLeft;
        regRight = Assets.instance.levelDecoration.mountainRigtht;

        // shift mountain and extend length
        origin.x = -dimension.x * 2;
        length += dimension.x * 2;
    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
