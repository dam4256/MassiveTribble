package com.foragers.mt.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.forager.mt.Assets;
import com.foragers.mt.entities.Circle;

public class GameScreen implements Screen {

	private int width = 800;
	private int height = 480;

	private SpriteBatch batcher = new SpriteBatch();
	private List<Circle> circles = new ArrayList<Circle>();

	public GameScreen() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		makeCircles(3, Color.YELLOW, 22, 88, 2000);
		makeCircles(2, Color.BLUE, 22, 88, 2000);
	}

	@Override
	public void render(float delta) {
		updateTouching();

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batcher.begin();
		for (Circle circle : circles) {
			circle.render(batcher);
		}
		batcher.end();

		for (Circle circle : circles) {
			circle.reduce(delta);
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
		for (Circle circle : circles) {
			circle.dispose();
		}
		batcher.dispose();
	}

	private void makeCircles(int nb, Color color, int radiusMin, int radiusMax, int lifetime) {
		for (int i = 0; i < nb; i++) {
			int x = radiusMax + 1 + (int) (Math.random() * (width - 2 * radiusMax - 1));
			int y = radiusMax + 1 + (int) (Math.random() * (height - 2 * radiusMax - 1));
			circles.add(new Circle(x, y, color, 2 * radiusMin, 2 * radiusMax, lifetime));
		}
	}


	private void updateTouching () {
		if (Gdx.input.justTouched()) {
			Vector3 touchPoint = new Vector3();
			for(Circle circle : circles){
				System.out.println("post crcl => " + circle.toString());

				touchPoint.set(Gdx.input.getX(), height - Gdx.input.getY(), 0);
				System.out.println("toutch pts >" + Gdx.input.getX() + " " + (height - Gdx.input.getY()));
				if(circle.contains(touchPoint.x, touchPoint.y)){
					Assets.playSound(Assets.clickSound);
				}
			}

		}
	}
}
