package com.crepes.butter.peanut;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crepes.butter.peanut.scenes.GameScene;
import com.crepes.butter.peanut.scenes.MainMenuScene;
import com.crepes.butter.peanut.scenes.Scene;

public class WallPipe extends ApplicationAdapter {

	public static BitmapFont font;

	final float DISPLAY_WIDTH = 17;
	final float DISPLAY_HEIGHT = 14;
	final float DISPLAY_SCALE = 32;

	Camera camera;
	Viewport viewport;

	public TiledMap currentBackground;
	public TmxMapLoader backgroundLoader;
	public OrthogonalTiledMapRenderer backgroundRenderer;

	Scene currentScene;
	MainMenuScene menuScene;
	GameScene gameScene;

	@Override
	public void create() {

		if (WallPipe.font == null)
			WallPipe.font = new BitmapFont(Gdx.files.internal("assets\\WallPipeFont.fnt"));

		camera = new OrthographicCamera();
		viewport = new StretchViewport(DISPLAY_WIDTH * DISPLAY_SCALE, DISPLAY_HEIGHT * DISPLAY_SCALE, camera);
		viewport.apply();

		gameScene = new GameScene(viewport);
		menuScene = new MainMenuScene(viewport);

		backgroundLoader = new TmxMapLoader();
		backgroundRenderer = new OrthogonalTiledMapRenderer(null);

		changeScene(menuScene);

		SoundGenerator.initSoundSystem();
	}

	@Override
	public void resize(int width, int height) {

		viewport.update(width, height);
	}

	@Override
	public void render() {

		Gdx.gl.glClear(GL20.GL_ALPHA_BITS);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		if (currentScene.readyToSwitch) {

			switch (currentScene.switchSceneType) {

			case GAME:
				changeScene(gameScene);
				break;

			case MAIN_MENU:
				changeScene(menuScene);
				break;

			default:
				break;

			}
		}

		backgroundRenderer.setView((OrthographicCamera) camera);
		backgroundRenderer.render();

		currentScene.act();
		currentScene.draw();
	}

	@Override
	public void dispose() {

	}

	public void changeScene(Scene scene) {

		currentScene = scene;

		currentBackground = backgroundLoader.load("assets\\" + currentScene.backgroundName + ".tmx");
		backgroundRenderer.setMap(currentBackground);

		// TODO separate the input processing from the Scene
		Gdx.input.setInputProcessor(currentScene);
	}
}
