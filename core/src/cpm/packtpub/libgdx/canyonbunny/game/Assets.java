package cpm.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

import cpm.packtpub.libgdx.canyonbunny.util.Constants;

/**
 * Created by Felix_2 on 03.07.2015.
 */
public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    private static Assets _instance;

    private AssetManager assetManager;

    public static Assets getInstance() {
        if (_instance == null) {
            _instance = new Assets();
        }
        return _instance;
    }

    private Assets() { }

    public Assets init(AssetManager assetManager) {
        this.assetManager = assetManager;
        //set assetManager error handler
        assetManager.setErrorListener(this);
        //load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        //start loading assets and wait until finished
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String name : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "asset: " + name);
        }
        return Assets.this;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: '" + asset.fileName + "'", throwable);
    }

    public class AssetBunny {
        public final TextureAtlas.AtlasRegion head;

        public AssetBunny (TextureAtlas atlas) {
            head = atlas.findRegion("bunny_head");
        }
    }

    public class AssetsRock {
        public final TextureAtlas.AtlasRegion edge;
        public final TextureAtlas.AtlasRegion middle;

        public AssetsRock (TextureAtlas atlas) {
            edge = atlas.findRegion("rock_edge");
            middle = atlas.findRegion("rock_middle");
        }
    }

    public class AssetsGoldCoin {
        public final TextureAtlas.AtlasRegion goldCoin;

        public AssetsGoldCoin (TextureAtlas atlas) {
            goldCoin = atlas.findRegion("item_gold_coin");
        }
    }

    public class AssetsFeather {
        public final TextureAtlas.AtlasRegion feather;

        public AssetsFeather (TextureAtlas atlas) {
            feather = atlas.findRegion("item_feather");
        }
    }
}
