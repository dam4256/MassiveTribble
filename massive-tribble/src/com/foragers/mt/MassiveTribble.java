package com.foragers.mt;

import com.badlogic.gdx.Game;
import com.forager.mt.Assets;
import com.foragers.mt.screens.GameScreen;

public class MassiveTribble extends Game {

	@Override
	public void create() {
		Assets.load();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		getScreen().dispose();
	}

}
