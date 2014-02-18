package com.foragers.mt.entities;

import com.foragers.mt.Sound;



public class ScoreManager{

	private static ScoreManager instance = null;
	//private static int _cptPASCOMMECA;
	private static String msgPASCOMMESA;
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
		int currentOrder = Integer.parseInt(circle.getOrder());
		System.out.println("# " + currentOrder);
		if(currentOrder == sm.nextExpectOrderCircle){
			Sound.click.play();
			circle.remove();
			sm.updateScore(circle);
			sm.nextExpectOrderCircle--;
		}
		
	}

	private void updateScore(Circle circle) {
		int currentDistance = (circle.getCurrentDiameterMax()/2) -  this.minRadius;
		int distancePercent = (currentDistance * 100) / this.maxDistance;
	//	System.out.println("\\ "+distancePercent + " "+currentDistance + " " + maxDistance);
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
		System.out.println("MAJ score"+ currentScore);
	/*	ntre 90 et 100% : 500 points
		Entre 75 et 89% : 300 points
		Entre 50 et 74% : 100 points
		Moins de 50% : 50 points*/
		
	}

	public static void circleHasDiedBeforeTouch(Circle circle){
		ScoreManager sm = getInstance();
		sm.nextExpectOrderCircle = Integer.parseInt(circle.getOrder()) +1;
		sm.msg = circle.getOrder() + " " + circle.getColor() + " is died !";
		sm.lastGain = -150;
		sm.currentScore -= sm.lastGain;
	}

	
	public static CharSequence getMsg(){
		ScoreManager sm = getInstance();
		msgPASCOMMESA = "";
		if(sm.lastGain > 0){
			msgPASCOMMESA = "+" + sm.lastGain;
			///_cptPASCOMMECA=0;
			sm.lastGain= 0;
		}else if(sm.lastGain < 0){
			msgPASCOMMESA = "-" + sm.lastGain;
			//_cptPASCOMMECA=0;
			sm.lastGain= 0;
		}
		if(sm.msg != "" && sm.msg!=null){
			msgPASCOMMESA +=" ";
			msgPASCOMMESA += sm.msg;
			//_cptPASCOMMECA=0;
			sm.msg= "";
		}
		
		//TODO pas comme ça !!
		//if(_cptPASCOMMECA > 5000000){
		//	msgPASCOMMESA = "";
		//}
		//_cptPASCOMMECA++;
		//System.out.println("RR" + msgPASCOMMESA + "^");
		return msgPASCOMMESA;
	}
	
	
	public static CharSequence getScore() {
		ScoreManager sm = getInstance();		
		return String.valueOf(sm.currentScore);
	}
	
}
