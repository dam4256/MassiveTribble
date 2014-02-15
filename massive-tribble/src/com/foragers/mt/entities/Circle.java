package com.foragers.mt.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Circle {

	private Texture texture;
	private Pixmap pixmap;
	private Vector2 position;

	private int diameterMin;
	private int diameterMax;
	private int lifetime;

	public Circle(Color color, int x, int y, int diameterMin, int diameterMax,
			int lifetime) {
		this.diameterMin = diameterMin;
		this.diameterMax = diameterMax;
		this.lifetime = lifetime;

		make(color, x, y);
	}

	public void render(SpriteBatch batcher) {
		batcher.draw(texture, position.x, position.y);
	}

	public void dispose() {
		texture.dispose();
		pixmap.dispose();
	}

	private void make(Color color, int x, int y) {
		int size = getLargestPowerOfTwo(isPowerOfTwo(diameterMax) ? diameterMax + 1 : diameterMax);

		pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.drawCircle(size / 2, size / 2, diameterMax / 2);
		
		texture = new Texture(pixmap);
		texture.draw(pixmap, 0, 0);
		texture.bind();
		
		position = new Vector2(x - size / 2, y - size / 2);
	}

	private boolean isPowerOfTwo(int x) {
		return x > 0 && (x & (x - 1)) == 0;
	}
	
	private int getLargestPowerOfTwo(int x) {
		return (int) Math.pow(2, Math.ceil(Math.log(x) / Math.log(2)));
	}
}
