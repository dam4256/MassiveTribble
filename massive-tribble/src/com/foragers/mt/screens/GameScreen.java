package com.foragers.mt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.foragers.mt.Media;
import com.foragers.mt.entities.Circle.Color;
import com.foragers.mt.entities.CircleFactory;
import com.foragers.mt.entities.Score;

public class GameScreen implements Screen {
	
	private int width;
	private int height;

	private SpriteBatch batcher;
	private Stage stage;
	
	public GameScreen() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		batcher = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		makeCircles(22, 110, 2000);
		
		Media.music.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
		if (stage.getActors().size == 0) {
			makeCircles(22, 110, 2000);
		}

		Score.render(batcher, 0, height);
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
		batcher.dispose();
		stage.dispose();
	}

	private void makeCircles(int radiusMin, int radiusMax, int lifetime) {
		int nb = 3 + (int) (Math.random() * 4);
		Color color = Color.getRandom();
		
		CircleFactory factory = new CircleFactory(nb, height, width, radiusMin, radiusMax);
		factory.setLifeTime(lifetime);
		factory.setPixMargin(10);

		for (int i = nb; i > 0; i--) {
			stage.addActor(factory.makeCircle(i, color));
		}
	}

}
