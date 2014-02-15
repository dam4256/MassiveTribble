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

		makeCircles(Color.YELLOW, 22, 64);
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

	private void makeCircles(Color color, int radiusMin, int radiusMax) {
		for (int i = 0; i < 3; i++) {
			int x = radiusMax + 1
					+ (int) (Math.random() * (width - 2 * radiusMax - 1));
			int y = radiusMax + 1
					+ (int) (Math.random() * (height - 2 * radiusMax - 1));
			Circle circle = new Circle(color, x, y, 2 * radiusMin,
					2 * radiusMax, 1000);
			circles.add(circle);
		}

	}

}
