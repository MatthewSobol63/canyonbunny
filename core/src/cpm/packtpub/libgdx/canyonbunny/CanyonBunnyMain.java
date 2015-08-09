package cpm.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

import cpm.packtpub.libgdx.canyonbunny.game.Assets;
import cpm.packtpub.libgdx.canyonbunny.screens.MenuScreen;

/**
 * Created by Felix on 27.06.2015.
 */
public class CanyonBunnyMain extends Game {
	@Override
	public void create () {
	// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		// Start game at menu screen
		setScreen(new MenuScreen(this));
	}
}
