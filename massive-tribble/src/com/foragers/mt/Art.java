package com.foragers.mt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

public class Art {

	private static final int NB_ORDERS = 6;
	
	public static Pixmap[] redCircle;
	public static Pixmap[] greenCircle;
	public static Pixmap[] blueCircle;
	
	public static void load() {
		redCircle = new Pixmap[NB_ORDERS];
		greenCircle = new Pixmap[NB_ORDERS];
		blueCircle = new Pixmap[NB_ORDERS];
		for (int i = 0; i < NB_ORDERS; i++) {
			redCircle[i] = load("data/redcircle" + (i + 1) + ".png");
			greenCircle[i] = load("data/greencircle" + (i + 1) + ".png");
			blueCircle[i] = load("data/bluecircle" + (i + 1) + ".png");
		}
	}
	
	private static Pixmap load(String name) {
		return new Pixmap(Gdx.files.internal(name));
	}

}
