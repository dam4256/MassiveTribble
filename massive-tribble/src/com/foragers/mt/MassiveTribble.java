package com.foragers.mt;

import com.badlogic.gdx.Game;
import com.foragers.mt.screens.GameScreen;

public class MassiveTribble extends Game {

	@Override
	public void create() {
		Sound.load();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		getScreen().dispose();
	}

}
