package com.foragers.mt.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {

	private static final FileHandle FONT = Gdx.files.internal("data/wellbutrin.fnt");
	private static final FileHandle GLYPH = Gdx.files.internal("data/wellbutrin.png");
	
	private static BitmapFont font = new BitmapFont(FONT, GLYPH, false);
	private static int points = 0;
	
	public static void update(int diameterMin, int diameterMax, int diameter) {
		float percentage = (float) 100 * (diameterMax - diameter) / (diameterMax - diameterMin);
		points += percentage > 89 ? 500 : percentage > 74 ? 300 : percentage > 49 ? 100 : 50;
	}
	
	public static void reset() {
		points = 0;
	}
	
	public static void render(SpriteBatch batcher, int x, int y) {
		batcher.begin();
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batcher, "Score: " + points, x, y);
		batcher.end();
	}
	
}
