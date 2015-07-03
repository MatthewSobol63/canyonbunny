package cpm.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

import cpm.packtpub.libgdx.canyonbunny.util.Constants;

/**
 * Created by Felix_2 on 03.07.2015.
 */
public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public AssetBunny bunny;
    public AssetsRock rock;
    public AssetsGoldCoin goldCoin;
    public AssetsFeather feather;
    public AssetLevelDecoration levelDecoration;
    private AssetManager assetManager;

    private Assets() { }

    public void init(AssetManager assetManager) {
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

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
        //enable texture filtering for pixel smoothing
        for (Texture texture : atlas.getTextures()) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        //create game resource objects
        bunny = new AssetBunny(atlas);
        rock = new AssetsRock(atlas);
        goldCoin = new AssetsGoldCoin(atlas);
        feather = new AssetsFeather(atlas);
        levelDecoration = new AssetLevelDecoration(atlas);
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

    public class AssetLevelDecoration {
        public final TextureAtlas.AtlasRegion cloud01;
        public final TextureAtlas.AtlasRegion cloud02;
        public final TextureAtlas.AtlasRegion cloud03;
        public final TextureAtlas.AtlasRegion mountainLeft;
        public final TextureAtlas.AtlasRegion mountainRigtht;
        public final TextureAtlas.AtlasRegion waterOverlay;

        public AssetLevelDecoration (TextureAtlas atlas) {
            cloud01 = atlas.findRegion("cloud01");
            cloud02 = atlas.findRegion("cloud02");
            cloud03 = atlas.findRegion("cloud03");
            mountainLeft = atlas.findRegion("mountain_left");
            mountainRigtht = atlas.findRegion("mountain_right");
            waterOverlay = atlas.findRegion("water_overlay");
        }
    }
}
