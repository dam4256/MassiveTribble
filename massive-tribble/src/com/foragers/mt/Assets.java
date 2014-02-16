package com.foragers.mt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Assets {

	public static Sound clickSound;

	public static void load() {
		clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
	}

	public static void playSound(Sound sound) {
		sound.play(1);
	}
	
}
