package com.foragers.mt.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.foragers.mt.entities.Circle;

public class GameScreen implements Screen {

	private int width = 800;
	private int height = 480;

	private SpriteBatch batcher = new SpriteBatch();
	private List<Circle> circles = new ArrayList<Circle>();

	public GameScreen() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		for (int i = 0; i < 1; i++) {
			circles.add(new Circle(Color.YELLOW, width / 2, height / 2, 64, 64, 1000));
		}
	}

	@Override
	public void render(float delta) {
		batcher.begin();
		for (Circle circle : circles) {
			circle.render(batcher);
		}
		batcher.end();
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
		for (Circle circle : circles) {
			circle.dispose();
		}
		batcher.dispose();
	}

}
