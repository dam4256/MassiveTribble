package com.foragers.mt;

import com.badlogic.gdx.Gdx;

public class Sound {

	public static com.badlogic.gdx.audio.Sound click;

	public static void load() {
		click = load("data/click.wav");
	}
	
	private static com.badlogic.gdx.audio.Sound load(String name) {
		return Gdx.audio.newSound(Gdx.files.internal(name));
	}
	
}
