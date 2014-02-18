package com.foragers.mt.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.foragers.mt.entities.Circle;
import com.foragers.mt.entities.Circle.Color;

public class CircleFactory {

	private static final int ATTEMPT_BEFORE_DROP = 500;
	private int _AttemptMarginCpt = 0;

	private int nbTotal;
	private int nbCurrent=0;
	private int lifeTime = 500;
	private int margin = 10;
	private float initialMargin = 10f;
	private int height;
	private int width;
	private int radiusMin;
	private List<Vector2> positList = new ArrayList<Vector2>();

	public CircleFactory(int nb, int height, int width, int radiusMin) {
		this.nbTotal = nb;
		this.height = height;
		this.width = width;
		this.radiusMin = radiusMin;
		computePositions(); //compute the future position
	}


	private void computePositions() {
		int attempt = 0;
		initPosition();
		//while overlap
		while(attempt < ATTEMPT_BEFORE_DROP && !rulesRespects()){
			movePositions();
			computeDecreaseMargin(attempt);
			attempt++;
		}
	}


	/**All margin% attempt step --> the margin deacrease.<br/>
	 * Example : initial margin = 10 --> all 10%attempt --> current margin --
	 * @param attempt 
	 */
	private void computeDecreaseMargin(final int attempt) {
		int percentPerformAttempt = (attempt - _AttemptMarginCpt);
		float percentLimit = (initialMargin/100f) * ATTEMPT_BEFORE_DROP;
		if((percentPerformAttempt  )> percentLimit){
			this.margin--;
			this._AttemptMarginCpt = attempt;
		}
	}


/*
 * Move a little the overlap circles
 * */
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
					}else{
						v1.x += offSet;
						v1.y += offSet;
						v2.x -= offSet;
						v2.y -= offSet;
					}
					if(v1.x< this.radiusMin){
						v1.x = this.radiusMin +1;
					}
					if(v2.x< this.radiusMin){
						v2.x = this.radiusMin +1;
					}
					if(v1.y< this.radiusMin){
						v1.y = this.radiusMin +1;
					}
					if(v2.y< this.radiusMin){
						v2.y = this.radiusMin +1;
					}
					
					if(v1.x+this.radiusMin> this.width){
						v1.x = this.width - this.radiusMin - 1;
					}
					if(v2.x+this.radiusMin> this.width){
						v2.x = this.width - this.radiusMin - 1;
					}
					
					if(v1.y+this.radiusMin> this.height){
						v1.y = this.height - this.radiusMin - 1;
					}
					if(v2.y+this.radiusMin> this.height){
						v2.y = this.height - this.radiusMin -1 ;
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
		return !result;
	}

	private boolean circlesOverlaps(Vector2 vector1, Vector2 vector2) {
		float dx = vector1.x - vector2.x;
		float dy = vector1.y - vector2.y;
		float distance = dx * dx + dy * dy;
		float radiusSum = 2*this.radiusMin  + this.margin;
		return distance < radiusSum * radiusSum;
	}

	/**
	 * initialize with random position
	 * */
	private void initPosition() {
		int indexPost = 0, x,y ;
		while(indexPost < nbTotal){
			 x = radiusMin + 1 + (int) (Math.random() * (width - 2 * (radiusMin+margin) - 1));
			 y = radiusMin + 1 + (int) (Math.random() * (height - 2 * (radiusMin+margin) - 1));
			this.positList.add(new Vector2(x, y));
			indexPost++;
		}
	}



	public Actor makeCircle(int order, Color color, int radiusMax) {

		float x,y;
		if(this.nbCurrent>this.nbTotal){
			//WARNING !!
			x = positList.get(0).x;
			y = positList.get(0).y;
		}else{
			x = positList.get(nbCurrent).x;
			y = positList.get(nbCurrent).y;
		}
		Actor result = new Circle(order, (int)x, (int) y, color, 2 * radiusMin, 2 *radiusMax , lifeTime);
		this.nbCurrent++;
		return result;
	}

	public void setLifeTime(int lifetime) {
		this.lifeTime = lifetime;
	}

	public void setPixMargin(int margin) {
		this.margin = margin;
		this.initialMargin = margin;
	}
}
