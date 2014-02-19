package com.foragers.mt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundAndMusic {

	public static com.badlogic.gdx.audio.Sound click;
	public static Music music;
	

	public static void load() {
		click = load("data/click.wav");
	}
	
	private static com.badlogic.gdx.audio.Sound load(String name) {
		return Gdx.audio.newSound(Gdx.files.internal(name));
	}
	
	
	public static void loadMusic(){
		music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
	}
	
	public static void playMusic(){
		music.play();
	}

	
	
}
