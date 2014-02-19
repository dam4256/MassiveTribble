package com.foragers.mt.entities;

import com.foragers.mt.Media;



public class ScoreManager{

	private static ScoreManager instance = null;
	private int nextExpectOrderCircle = 3;
	private int currentScore = 0;
	private int minRadius;
	private int maxDistance;
	private int lastGain = 0;
	private String msg;
	
	private ScoreManager(){
		
	}
	
	public static void initScoreManager(int minRadius, int maxRadius){
		ScoreManager sm = getInstance();
		sm.minRadius = minRadius;
		sm.maxDistance = maxRadius - minRadius;
	}
	
	private static ScoreManager getInstance(){
		if(instance == null){
			instance = new ScoreManager();
		}
		return instance;
	}
	
	public static void circleHasTouch(Circle circle){
		ScoreManager sm = getInstance();
		int currentOrder = circle.getOrder();
		if(currentOrder == sm.nextExpectOrderCircle){
			Media.click.play();
			circle.remove();
			sm.updateScore(circle);
			sm.nextExpectOrderCircle--;
		}
		
	}

	private void updateScore(Circle circle) {
		int currentDistance = (circle.getCurrentDiameterMax()/2) -  this.minRadius;
		int distancePercent = (currentDistance * 100) / this.maxDistance;
		if(distancePercent<=10){
			lastGain = 500;
			msg = "You are the boss!";
		}else if(distancePercent>=11 && distancePercent<=25 ){
			lastGain = 300;
			msg = "Yes !";
		}else if(distancePercent>=26 && distancePercent<=50){
			lastGain = 100;
		}else{
			lastGain = 50;
		}
		currentScore +=lastGain;
	/*	ntre 90 et 100% : 500 points
		Entre 75 et 89% : 300 points
		Entre 50 et 74% : 100 points
		Moins de 50% : 50 points*/
		
	}

	public static void circleHasDiedBeforeTouch(Circle circle){
		ScoreManager sm = getInstance();
		sm.nextExpectOrderCircle = circle.getOrder() +1;
		sm.msg = circle.getOrder() + " " + " is died !";
		sm.lastGain = -150;
		sm.currentScore += sm.lastGain;
	}

	
	public static CharSequence getMsg(){
		ScoreManager sm = getInstance();
		String msg = "";
		if(sm.lastGain > 0){
			msg = "+" + sm.lastGain;
			sm.lastGain= 0;
		}else if(sm.lastGain < 0){
			msg = "-" + sm.lastGain;
			sm.lastGain= 0;
		}
		if(sm.msg != "" && sm.msg!=null){
			msg +=" ";
			msg += sm.msg;
			sm.msg= "";
		}
		
		return msg;
	}
	
	
	public static CharSequence getScore() {
		ScoreManager sm = getInstance();
		/*String msg = "";
		if(sm.currentScore>0){
			msg = "+";
		}else if(sm.currentScore < 0){
			msg = "-";
		}
		msg += sm.currentScore;
		return msg;*/
		return ""+sm.currentScore;
	}
	
}
