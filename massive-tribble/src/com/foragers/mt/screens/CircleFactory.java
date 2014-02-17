package com.foragers.mt.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.foragers.mt.entities.Circle;
import com.foragers.mt.entities.Circle.Color;

public class CircleFactory {

	public static enum LIFE_TIME_STRATEGY {STATIC, RANDOM, SMART_MUSIC }

	private static final int ATTEMPT_BEFORE_DROP = 5000;
	private int _AttemptMarginCpt = 0;

	//private LIFE_TIME_STRATEGY strategy;
	private int nbTotal;
	private int nbCurrent=0;
	private int staticLifeTime = 500;
	private int margin = 10;
	private float initialMargin = 10f;
	private int height;
	private int width;
	private int radiusMax;
	private List<Vector2> positList = new ArrayList<Vector2>();

	public CircleFactory(int nb, LIFE_TIME_STRATEGY strategy, int height, int width, int radiusMax) {
		//this.strategy = strategy;
		this.nbTotal = nb;
		this.height = height;
		this.width = width;
		this.radiusMax = radiusMax;

		computePositions();
	}



	private void computePositions() {
		int attempt = 0;
		initPosition();

		while(attempt < ATTEMPT_BEFORE_DROP && !rulesRespects()){
			movePositions();
			computeDecreaseMargin(attempt);
			attempt++;
			//for(int i =0; i < this.nbTotal ; i++)
			//	System.out.println("inter " + this.positList.get(i).x + " " + this.positList.get(i).y);
			//System.out.println("recompute ! " + attempt);
			/*for(com.badlogic.gdx.math.Circle circle : this.badLogicCircles){
				stage.addActor(new Circle((int)circle.x, (int) circle.y, Color.GREEN, 2 * 22, 2 * radiusMax, staticLifeTime*5));
			}*/
		}
		//for(int i =0; i < this.nbTotal ; i++)
		//	System.out.println("new " + this.positList.get(i).x + " " + this.positList.get(i).y);
	}


	/**All margin% attempt step --> the margin deacrease.<br/>
	 * Example : initial margin = 10 --> all 10%attempt --> current margin --
	 * @param attempt 
	 * @return */
	private void computeDecreaseMargin(final int attempt) {
		int percentPerformAttempt = (attempt - _AttemptMarginCpt);
		float percentLimit = (initialMargin/100f) * ATTEMPT_BEFORE_DROP;
		//System.out.println("# " + percentPerformAttempt + " " + percentLimit + " " +attempt);
		//System.out.println("> " + (10f / 100f) * 1000f);
		if((percentPerformAttempt  )> percentLimit){
			//System.out.println("decrease ! " + (margin-1) + " " + attempt);
			this.margin--;
			this._AttemptMarginCpt = attempt;
		}
	}



	private void movePositions() {
		
		boolean result = true;
		for(int i =0; i < this.nbTotal -1 ; i++){
			for(int j=i+1; j < this.nbTotal ; j++ ){
				Vector2 v1 = this.positList.get(i);
				Vector2 v2 = this.positList.get(j);
				result = circlesOverlaps(v1 ,this.positList.get(j));
				int offSet = 1+ (int) (Math.random() *5);
				if(result){
					if(v1.x > v2.x && v1.y < v2.y){
						v1.x += offSet;
						v1.y -= offSet;
						v2.x -= offSet;
						v2.y += offSet;
					}else /*if(c1.x < c2.x && c1.y > c2.y)*/{
						v1.x += offSet;
						v1.y += offSet;
						v2.x -= offSet;
						v2.y -= offSet;
					}
					if(v1.x< this.radiusMax){
						v1.x = this.radiusMax +1;
					}
					if(v2.x< this.radiusMax){
						v2.x = this.radiusMax +1;
					}
					if(v1.y< this.radiusMax){
						v1.y = this.radiusMax +1;
					}
					if(v2.y< this.radiusMax){
						v2.y = this.radiusMax +1;
					}
					
					if(v1.x+this.radiusMax> this.width){
						v1.x = this.width - this.radiusMax - 1;
					}
					if(v2.x+this.radiusMax> this.width){
						v2.x = this.width - this.radiusMax - 1;
					}
					
					if(v1.y+this.radiusMax> this.height){
						v1.y = this.height - this.radiusMax - 1;
					}
					if(v2.y+this.radiusMax> this.height){
						v2.y = this.height - this.radiusMax -1 ;
					}
				}
			}
		}

	}


	/**
	 * the rules is respects ffi :
	 * [-the circles are contains into the screen] --> verify by movePositions();
	 * -the circles don't touch them
	 * */
	private boolean rulesRespects() {
		boolean result = true;
		for(int i =0; i < this.nbTotal -1 ; i++){
			for(int j=i+1; j < this.nbTotal ; j++ ){
				result = circlesOverlaps(this.positList.get(i),this.positList.get(j));
				if(result) break;
			}
			if(result) break;
		}
		//System.out.println("rules => " + !result);
		return !result;
	}


	private boolean circlesOverlaps(Vector2 vector1, Vector2 vector2) {
		//System.out.println("## " + radiusMax + " " + margin);
		float dx = vector1.x - vector2.x;
		float dy = vector1.y - vector2.y;
		float distance = dx * dx + dy * dy;
		float radiusSum = 2*this.radiusMax  + this.margin;
		return distance < radiusSum * radiusSum;
	}
	



	/**
	 * initialize with random position
	 * */
	private void initPosition() {
		int indexPost = 0, x,y ;
		
		while(indexPost < nbTotal){
			 x = radiusMax + 1 + (int) (Math.random() * (width - 2 * (radiusMax+margin) - 1));
			 y = radiusMax + 1 + (int) (Math.random() * (height - 2 * (radiusMax+margin) - 1));
			 //System.out.println("INIT " + x + " " + y);
			/*if(indexPost == 0){
				x = this.radiusMax;
				y = this.radiusMax;
			}else if(indexPost == 1){
				x = this.radiusMax + 100;
				y = this.radiusMax + 100;
			}else{
				x = this.radiusMax + 100;
				y = this.radiusMax + 100;
			}*/
			
			this.positList.add(new Vector2(x, y));
			indexPost++;
		}
	}



	public Actor makeCircle(int order, Color color, int radiusMin) {

		float x,y;
		if(this.nbCurrent>this.nbTotal){
			//WARNING !!
			x = positList.get(0).x;
			y = positList.get(0).y;
		}else{
			x = positList.get(nbCurrent).x;
			y = positList.get(nbCurrent).y;
		}
		Actor result = new Circle(order, (int)x, (int) y, color, 2 * radiusMin, 2 * radiusMax, staticLifeTime*500);
		this.nbCurrent++;
		return result;
	}


	public void setLifeTime(int lifetime) {
		this.staticLifeTime = lifetime;
	}

	public void setPixMargin(int margin) {
		this.margin = margin;
		this.initialMargin = margin;
	}



}
