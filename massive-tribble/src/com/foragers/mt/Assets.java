package com.foragers.mt;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture background;
	public static Texture titre;
	public static TextureRegion backgroundRegion;

	public static Texture items;
	public static Animation tribbleAnim;
	public static BitmapFont font;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		background = loadTexture("data/g.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 512, 512);

		items = loadTexture("data/mt3.png");

		tribbleAnim = new Animation(0.2f, new TextureRegion(items, 0, 0, 32, 32), new TextureRegion(items, 32, 0, 32, 32),
			new TextureRegion(items, 0, 0, 32, 32));
	
		titre = loadTexture("data/titre.png");

	}

}
