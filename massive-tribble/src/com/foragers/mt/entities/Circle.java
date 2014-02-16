package com.foragers.mt.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Circle extends Actor {

	private Texture texture;

	private int x;
	private int y;
	private Color color;
	private int diameterMin;
	private int diameterMax;

	/*
	 * The amount of time passed since the last reducing action.
	 */
	private float deltaTime = 0;

	/*
	 * The maximum time to pass in order to perform a reducing action.
	 */
	private float maxDeltaTime;

	public Circle(int x, int y, Color color, int diameterMin, int diameterMax, int lifetime) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.diameterMin = diameterMin;
		this.diameterMax = diameterMax;

		maxDeltaTime = (float) lifetime / (diameterMax - diameterMin);

		makeTexture();
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(texture, x - texture.getWidth() / 2, y - texture.getHeight() / 2);
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
		}
	}

	private void makeTexture() {
		int size = getLargestPowerOfTwo(isPowerOfTwo(diameterMax) ? diameterMax + 1 : diameterMax);

		Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.drawCircle(size / 2, size / 2, diameterMax / 2);
		pixmap.drawCircle(size / 2, size / 2, diameterMin / 2);
		
		texture = new Texture(pixmap);
		texture.draw(pixmap, 0, 0);

		pixmap.dispose();
	}

	private boolean isPowerOfTwo(int x) {
		return x > 0 && (x & (x - 1)) == 0;
	}

	private int getLargestPowerOfTwo(int x) {
		return (int) Math.pow(2, Math.ceil(Math.log(x) / Math.log(2)));
	}
	
	public boolean contains(float x, float y) {
		x = this.x - x;
		y = this.y - y;
		return x * x + y * y <= (diameterMax/2)*(diameterMax/2);
	}

	@Override
	public String toString() {
		return "Circle [x=" + x + ", y=" + y + ", diameterMin=" + diameterMin + ", diameterMax=" + diameterMax + "]";
	}
	
}
