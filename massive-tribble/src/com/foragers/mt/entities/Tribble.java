package com.foragers.mt.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.foragers.mt.Animation;
import com.foragers.mt.Assets;

public class Tribble extends Actor{

	private int x;
	private int y;


	private float stateTime;
	private float coefX;
	private float coefY;
	private int xInit;
	private int yInit;
	private int width;
	private int height;

	public Tribble(int width, int height) {
		initXY();
		coefX = newCoef();
		coefY = newCoef();
		this.width = width;
		this.height = height;
	}

	private static float newCoef(){
		float tmpCoef = 20 + ((float) Math.random())*30 ;
		if((((int) (Math.random()*10)) %2)==0){
			tmpCoef *= -1;
		}
		return tmpCoef;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		TextureRegion keyFrame = Assets.tribbleAnim.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
		batch.draw(keyFrame, x , y , 32, 32);
		System.out.println("ici " + stateTime + " " + x + " " + coefX + " " + ((int) (Math.random()*10)) %2);
	}

	@Override
	public void act(float delta) {
		stateTime += delta;
		updatePosition(delta);
	}

	private void updatePosition(float delta) {
		x= (int) (coefX*stateTime) + xInit;
		y= (int) (coefY*stateTime) + yInit;
		
		if(x<0 || y<0 || x>=width || y>= height){
			initXY();
			stateTime = 0;
			coefX = newCoef();
			coefY = newCoef();
		}
	}

	private void initXY() {
		xInit = 1 + (int) (Math.random() * width);
		yInit = 1 + (int) (Math.random() * height);
	}

}
