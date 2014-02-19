package com.foragers.mt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Media {

	public static Sound click;
	public static Music music;
	
	public static void load() {
		click = loadSound("data/click.wav");
		music = loadMusic("data/music.mp3");
	}
	
	private static Sound loadSound(String name) {
		return Gdx.audio.newSound(Gdx.files.internal(name));
	}
	
	private static Music loadMusic(String name) {
		Music music = Gdx.audio.newMusic(Gdx.files.internal(name));
		music.setLooping(true);
		music.setVolume(0.5f);
		return music;
	}
	
}
