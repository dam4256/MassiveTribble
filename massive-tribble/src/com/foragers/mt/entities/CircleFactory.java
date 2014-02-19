package com.foragers.mt.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.foragers.mt.entities.Circle.Color;

public class CircleFactory {

	private static final int ATTEMPT_BEFORE_DROP = 500;
	private int _AttemptMarginCpt = 0;

	private int nb;
	private int nbCurrent = 0;
	private int lifeTime = 500;
	private int margin = 10;
	private float initialMargin = 10f;
	private int height;
	private int width;
	private int radiusMin;
	private int radiusMax;
	
	private List<Vector2> positions = new ArrayList<Vector2>();

	public CircleFactory(int nb, int height, int width, int radiusMin, int radiusMax) {
		this.nb = nb;
		this.height = height;
		this.width = width;
		this.radiusMin = radiusMin;
		this.radiusMax = radiusMax;
		
		computePositions();
	}
	
	public Actor makeCircle(int order, Color color) {
		float x, y;
		
		if (nbCurrent > nb) {
			x = positions.get(0).x;
			y = positions.get(0).y;
		} else {
			x = positions.get(nbCurrent).x;
			y = positions.get(nbCurrent).y;
		}
		
		nbCurrent++;
		
		return new Circle(order, (int) x, (int) y, color, 2 * radiusMin, 2 * radiusMax, lifeTime);
	}

	public void setLifeTime(int lifetime) {
		lifeTime = lifetime;
	}

	public void setPixMargin(int margin) {
		this.margin = margin;
		initialMargin = margin;
	}

	private void computePositions() {
		int attempt = 0;
		initPosition();
		while (attempt < ATTEMPT_BEFORE_DROP && !rulesRespects()) {
			movePositions();
			computeDecreaseMargin(attempt);
			attempt++;
		}
	}

	private void initPosition() {
		int indexPost = 0, x, y;
		while (indexPost < nb){
			 x = radiusMax + 1 + (int) (Math.random() * (width - 2 * (radiusMax + margin) - 1));
			 y = radiusMax + 1 + (int) (Math.random() * (height - 2 * (radiusMax + margin) - 1));
			positions.add(new Vector2(x, y));
			indexPost++;
		}
	}
	
	private boolean rulesRespects() {
		boolean result = true;

		for (int i = 0; i < nb - 1; i++) {
			for (int j = i + 1; j < nb; j++) {
				result = circlesOverlaps(positions.get(i), positions.get(j));
				if (result) {
					break;
				}
			}
			
			if (result) {
				break;
			}
		}
		
		return !result;
	}
	
	private void movePositions() {
		boolean result = true;
		
		for (int i = 0; i < nb - 1; i++) {
			for (int j = i + 1; j < nb; j++) {
				Vector2 v1 = positions.get(i);
				Vector2 v2 = positions.get(j);
				result = circlesOverlaps(v1, positions.get(j));
				int offSet = 1 + (int) (Math.random() * 5);
				
				if (result) {
					if (v1.x > v2.x && v1.y < v2.y) {
						v1.x += offSet;
						v1.y -= offSet;
						v2.x -= offSet;
						v2.y += offSet;
					} else {
						v1.x += offSet;
						v1.y += offSet;
						v2.x -= offSet;
						v2.y -= offSet;
					}
					
					if (v1.x < radiusMax) {
						v1.x = radiusMax + 1;
					}
					
					if (v2.x < radiusMax) {
						v2.x = radiusMax + 1;
					}
					
					if (v1.y < radiusMax) {
						v1.y = radiusMax + 1;
					}
					
					if (v2.y < radiusMax) {
						v2.y = radiusMax + 1;
					}

					if (v1.x + radiusMax > width) {
						v1.x = width - radiusMax - 1;
					}
					
					if (v2.x + radiusMax > width) {
						v2.x = width - radiusMax - 1;
					}

					if (v1.y + radiusMax > height) {
						v1.y = height - radiusMax - 1;
					}
					
					if (v2.y + radiusMax > height) {
						v2.y = height - radiusMax - 1;
					}
				}
			}
		}
	}

	private void computeDecreaseMargin(int attempt) {
		int percentPerformAttempt = (attempt - _AttemptMarginCpt);
		float percentLimit = (initialMargin / 100f) * ATTEMPT_BEFORE_DROP;
		
		if (percentPerformAttempt > percentLimit) {
			margin--;
			_AttemptMarginCpt = attempt;
		}
	}

	private boolean circlesOverlaps(Vector2 vector1, Vector2 vector2) {
		float dx = vector1.x - vector2.x;
		float dy = vector1.y - vector2.y;
		float radiusSum = 2 * radiusMin + margin;
		
		return dx * dx + dy * dy < radiusSum * radiusSum;
	}

}
