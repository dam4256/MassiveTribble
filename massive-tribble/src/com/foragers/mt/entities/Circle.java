package com.foragers.mt.entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.foragers.mt.Art;
import com.foragers.mt.Media;

public class Circle extends Actor {

	public enum Color {
		
		RED (com.badlogic.gdx.graphics.Color.RED, Art.redCircle),
		GREEN (com.badlogic.gdx.graphics.Color.GREEN, Art.greenCircle),
		BLUE (com.badlogic.gdx.graphics.Color.BLUE, Art.blueCircle);
		
		private com.badlogic.gdx.graphics.Color gdxColor;
		private Pixmap[] pixmaps;
		
		Color(com.badlogic.gdx.graphics.Color gdxColor, Pixmap[] pixmaps) {
			this.gdxColor = gdxColor;
			this.pixmaps = pixmaps;
		}
		
		public static Color getRandom() {
	        return values()[(int) (Math.random() * values().length)];
	    }
		
	}

	private Texture texture;

	private int order;
	private int x;
	private int y;
	private Color color;
	private int diameterMin;
	private int diameterMax;
	private int diameter;
	
	private float showTime = 0;
	private float deltaTime = 0;
	private float maxDeltaTime;

	public Circle(int order, int x, int y, Color color, int diameterMin, int diameterMax, int lifetime) {
		this.order = order;
		this.x = x;
		this.y = y;
		this.color = color;
		this.diameterMin = diameterMin;
		this.diameterMax = diameterMax;
		diameter = diameterMax;

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
		setVisible(showTime >= (order - 1) * 850);
		if (isVisible()) {
			if (diameter > diameterMin) {
				deltaTime += 1000 * delta;
	
				if (deltaTime >= maxDeltaTime) {
					double nbPixelsToReduce = Math.floor(deltaTime / maxDeltaTime);
					diameter -= nbPixelsToReduce;
					deltaTime -= nbPixelsToReduce * maxDeltaTime;
					
					if (diameter < diameterMin) {
						diameter = diameterMin;
					}
	
					makeTexture();
				}
			} else {
				remove();
			}
		} else {
			showTime += 1000 * delta;
		}
	}

	private void makeTexture() {
		int size = getLargestPowerOfTwo(isPowerOfTwo(diameter) ? diameter + 1 : diameter);

		Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
		pixmap.setColor(color.gdxColor);
		pixmap.drawCircle(size / 2, size / 2, diameter / 2);
		pixmap.drawPixmap(color.pixmaps[this.order - 1], size / 2 - diameterMin / 2, size / 2 - diameterMin / 2);
		
		texture = new Texture(pixmap);
		texture.draw(pixmap, 0, 0);
		
		pixmap.dispose();
		
		setBounds(x - texture.getWidth() / 2, y - texture.getHeight() / 2, texture.getWidth(), texture.getHeight());
	}

	private void addInputListener() {
		setTouchable(Touchable.enabled);
		addListener(new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (contains(x, y)) {
					Media.click.play();
					Score.update(diameterMin, diameterMax, diameter);
					remove();
				}
				return true;
			}
			
		});

	}
	
	private boolean isPowerOfTwo(int x) {
		return x > 0 && (x & (x - 1)) == 0;
	}

	private int getLargestPowerOfTwo(int x) {
		return (int) Math.pow(2, Math.ceil(Math.log(x) / Math.log(2)));
	}

	private boolean contains(float x, float y) {
		float dx = x - texture.getWidth() / 2;
		float dy = y - texture.getHeight() / 2;
		return Math.sqrt(dx * dx + dy * dy) < diameterMin / 2;
	}

}
