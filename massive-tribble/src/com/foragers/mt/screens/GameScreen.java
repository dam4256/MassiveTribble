package com.foragers.mt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.foragers.mt.Assets;
import com.foragers.mt.SoundAndMusic;
import com.foragers.mt.entities.Circle.Color;
import com.foragers.mt.entities.CircleFactory;
import com.foragers.mt.entities.ScoreManager;
import com.foragers.mt.entities.Tribble;

public class GameScreen implements Screen {

	private int width = 800;
	private int height = 480;

	private Stage stage;

	private float deltaCumul=0;

	public GameScreen() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);


		ScoreManager.initScoreManager(22, 110);
		SoundAndMusic.loadMusic();
		stage.addActor(new Tribble(width, height));
		stage.addActor(new Tribble(width , height));
		makeCircles(3, Color.GREEN, 22, 110, 2000);
		//makeCircles(2, Color.BLUE, 22, 110, 2000);
		SoundAndMusic.playMusic();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		deltaCumul+=delta;
		SpriteBatch spriteBatch;

		if(deltaCumul<2){
			spriteBatch = new SpriteBatch();
			spriteBatch.begin();
			spriteBatch.draw(Assets.titre, 0, 0);
			spriteBatch.end();
		}else{
			BitmapFont font;
			spriteBatch = new SpriteBatch();
			CharSequence str = "Score : " + ScoreManager.getScore();
			font = new BitmapFont(Gdx.files.internal("data/wellbutrin.fnt"),
					Gdx.files.internal("data/wellbutrin.png"), false);
			spriteBatch.begin();
			spriteBatch.draw(Assets.backgroundRegion, 0, -20, 512, 512);
			font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			font.draw(spriteBatch, str, 5, height);
			spriteBatch.end();

			str = ScoreManager.getMsg();
			if( !str.equals("") && str != null){
				spriteBatch = new SpriteBatch();
				font = new BitmapFont(Gdx.files.internal("data/wellbutrin.fnt"),
						Gdx.files.internal("data/wellbutrin.png"), false);
				spriteBatch.begin();
				font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
				font.draw(spriteBatch, str, 5, height-37);
				spriteBatch.end();
			}
			stage.act(delta);
			stage.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	private void makeCircles(int nb, Color color, int radiusMin, int radiusMax, int lifetime) {
		CircleFactory factory = new CircleFactory(nb, height, width, radiusMin, radiusMax);
		factory.setLifeTime(lifetime);
		factory.setPixMargin(10);
		for (int i = 0; i < nb; i++) {
			Actor actor = factory.makeCircle(i + 1, color);
			stage.addActor(actor);
		}
	}

}
