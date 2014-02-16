package com.foragers.mt.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.forager.mt.Assets;
import com.foragers.mt.entities.Circle;

public class GameScreen implements Screen {

	private int width = 800;
	private int height = 480;

	private SpriteBatch batcher = new SpriteBatch();
	private List<Circle> circles = new ArrayList<Circle>();
	
	private com.badlogic.gdx.math.Circle c1;
	Vector3 touchPoint;
	OrthographicCamera guiCam;

	public GameScreen() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		makeCircles(3, Color.YELLOW, 22, 88, 2000);
		makeCircles(2, Color.BLUE, 22, 88, 2000);
		
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		touchPoint = new Vector3();
		System.out.println("c1 => " + circles.get(0).getPost());
		c1 = new com.badlogic.gdx.math.Circle(circles.get(0).getPost(), circles.get(0).getRadius());
	}

	@Override
	public void render(float delta) {
		updatePaused();
		
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

	
	private void updatePaused () {
		if (Gdx.input.justTouched()) {
			System.out.println("c1 => " + circles.get(0).getPost());
			
			//guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			//System.out.println(" >" + Gdx.input.getX() + " " + Gdx.input.getY());
			//if (c1.contains(touchPoint.x, touchPoint.y)) {
			//	Assets.playSound(Assets.clickSound);
				
			//}
			touchPoint.set(Gdx.input.getX(), height - Gdx.input.getY(), 0);
			System.out.println(" >" + Gdx.input.getX() + " " + (height - Gdx.input.getY()));
			if (c1.contains(touchPoint.x, touchPoint.y)) {
			//	Assets.playSound(Assets.clickSound);
				
			}
			if(circles.get(0).contains(touchPoint.x, touchPoint.y)){
				Assets.playSound(Assets.clickSound);
			}
			
		}
	}
}
