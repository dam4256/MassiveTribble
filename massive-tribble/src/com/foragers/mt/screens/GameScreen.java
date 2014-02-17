package com.foragers.mt.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.foragers.mt.entities.Circle.Color;
import com.foragers.mt.screens.CircleFactory.LIFE_TIME_STRATEGY;

public class GameScreen implements Screen {

	private int width = 800;
	private int height = 480;

	private Stage stage;

	public GameScreen() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		makeCircles(3, Color.GREEN, 22, 88, 2000);
		makeCircles(2, Color.BLUE, 22, 88, 2000);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
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
		try {
			int delayToDraw = 500; // TODO
			CircleFactory factory = new CircleFactory(nb, LIFE_TIME_STRATEGY.STATIC, this.height, this.width, radiusMax);
			factory.setLifeTime(lifetime);
			factory.setPixMargin(10);
			for(int i=0; i< nb ; i++){
				Actor actor = factory.makeCircle(i+1 ,color, radiusMin);
				stage.addActor(actor);
				Thread.sleep(delayToDraw);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
