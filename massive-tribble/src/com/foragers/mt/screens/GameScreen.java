package com.foragers.mt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.foragers.mt.Media;
import com.foragers.mt.entities.Circle.Color;
import com.foragers.mt.entities.CircleFactory;
import com.foragers.mt.entities.ScoreManager;

public class GameScreen implements Screen {
	
	private int width = 800;
	private int height = 480;

	private Stage stage;
	
	public GameScreen() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		ScoreManager.initScoreManager(22, 110);
		
		makeCircles(3, Color.GREEN, 22, 110, 2000);
		//makeCircles(2, Color.BLUE, 22, 110, 2000);
		
		Media.music.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();

		SpriteBatch spriteBatch;
		BitmapFont font;
		spriteBatch = new SpriteBatch();
		CharSequence str = "Score : " + ScoreManager.getScore();
		font = new BitmapFont(Gdx.files.internal("data/wellbutrin.fnt"),
				Gdx.files.internal("data/wellbutrin.png"), false);
		spriteBatch.begin();
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
