package com.foragers.mt.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Circle extends Actor {

	public enum Color {

		BLUE (com.badlogic.gdx.graphics.Color.BLUE),
		RED (com.badlogic.gdx.graphics.Color.RED),
		GREEN (com.badlogic.gdx.graphics.Color.GREEN);

		private com.badlogic.gdx.graphics.Color gdxColor;

		Color(com.badlogic.gdx.graphics.Color gdxColor) {
			this.gdxColor = gdxColor;
		}

		public String toString() {
			return name().toLowerCase();
		}

	}

	private Texture texture;

	private String order;
	private int x;
	private int y;
	private Color color;
	private int diameterMin;
	private int diameterMax;

	public String getOrder() {
		return order;
	}

	public int getCenterX() {
		return x;
	}

	public int getCenterY() {
		return y;
	}

	public int getDiameterMin() {
		return diameterMin;
	}

	public int getCurrentDiameterMax() {
		return diameterMax;
	}

	/*
	 * The amount of time passed since the last reducing action.
	 */
	private float deltaTime = 0;

	/*
	 * The maximum time to pass in order to perform a reducing action.
	 */
	private float maxDeltaTime;

	public Circle(int order, int x, int y, Color color, int diameterMin, int diameterMax, int lifetime) {
		this.order = String.valueOf(order);
		this.x = x;
		this.y = y;
		this.color = color;
		this.diameterMin = diameterMin;
		this.diameterMax = diameterMax;

		maxDeltaTime = (float) lifetime / (diameterMax - diameterMin);
		makeTexture();
		addInputListener();
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY());
	}

	@Override
	public void act(float delta) {
		if (diameterMax > diameterMin) {
			deltaTime += 1000 * delta;

			if (deltaTime >= maxDeltaTime) {
				double nbPixelsToReduce = Math.floor(deltaTime / maxDeltaTime);
				diameterMax -= nbPixelsToReduce;
				deltaTime -= nbPixelsToReduce * maxDeltaTime;

				if (diameterMax < diameterMin) {
					diameterMax = diameterMin;
				}

				makeTexture();
			}
		} else {
			beforeRemove(false);
		}
	}

	private void makeTexture() {
		int size = getLargestPowerOfTwo(isPowerOfTwo(diameterMax) ? diameterMax + 1 : diameterMax);

		Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
		pixmap.setColor(color.gdxColor);
		pixmap.drawCircle(size / 2, size / 2, diameterMax / 2);

		Pixmap order = new Pixmap(Gdx.files.internal("data/" + color.toString() + "-" + this.order + ".png"));
		pixmap.drawPixmap(order, size / 2 - diameterMin / 2, size / 2 - diameterMin / 2);

		texture = new Texture(pixmap);
		texture.draw(pixmap, 0, 0);

		order.dispose();
		pixmap.dispose();

		setBounds(x - texture.getWidth() / 2, y - texture.getHeight() / 2, texture.getWidth(), texture.getHeight());
	}

	private void addInputListener() {
		setTouchable(Touchable.enabled);
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (contains(x, y)) {
					ScoreManager.circleHasTouch(Circle.this);
				}
				return true;
			}
		});

	}

	private void beforeRemove(boolean removeByUser){
		if(!removeByUser){
			ScoreManager.circleHasDiedBeforeTouch(this);
		}
		remove();
	}
	
	private boolean isPowerOfTwo(int x) {
		return x > 0 && (x & (x - 1)) == 0;
	}

	private int getLargestPowerOfTwo(int x) {
		return (int) Math.pow(2, Math.ceil(Math.log(x) / Math.log(2)));
	}

	@Override
	public String toString() {
		return "Circle [x=" + x + ", y=" + y + ", diameterMin=" + diameterMin + ", diameterMax=" + diameterMax + "]";
	}

	boolean contains(float x, float y) {
		float dx = x - texture.getWidth() / 2;
		float dy = y - texture.getHeight() / 2;
		return Math.sqrt(dx * dx + dy * dy) < diameterMin / 2;
	}

}
