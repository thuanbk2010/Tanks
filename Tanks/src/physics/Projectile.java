package physics;

import java.awt.*;
import java.awt.image.BufferedImage;

import drawable.drawable;
import drawable.standardShell;

import java.math.*;

import Main.Ticker;
import terrain.Terrain;

public  class Projectile{

	protected  double intX;
	protected  double intY;
	protected  double x0;
	protected  double y0;
	protected  double g;
	protected double angle;
	double power;
	private Terrain terrain;
	private standardShell std;
	private static double wind;
	public  double vX;
	public  double vY;
	public double height;
	public static double[] points;
	long time;

	public Projectile(double x, double y, Wind wind, double power){
		intX = x;
		intY= y;
		x0 = x;
		y0 = y;
		g = 1;
		angle = Math.tan(y/x);
		this.wind = wind.getWindSpeed();
		vX = 0;
		vY = 0;
		double[] points = new double[2];
		points[0] = intX;
		points[1] = intY;
		height = 0;
		time = 0;
		Ticker.addMethod(this::run);
		this.power = power;
	}

	public Projectile() {
		// TODO Auto-generated constructor stub
	}

	public double getPower() {
		return power;
	}

	public void setPower(int power){
		this.power = power;
		vX = (double) this.power * Math.cos(angle);
		vY = (double) this.power* Math.sin(angle);
	}
	
	public void setAngle(double ang){
		angle = ang;
	}
	
	
	public void setX(double x){
		intX = x;
		
	}
	public void setY(double y){
		intY = y;
		
	}
	
	public double[] fire(long time, double x, double y){
		System.out.println("do I get here?");
		time = (long) (time * Math.pow(10,-9));
		this.time = time + this.time;
		//get time in seconds
		vX = vX + wind*time;
		x = (double) x0 + vX * (double) time;
		y = (double) y0 + vY * (double) time + 0.5  * Math.pow(time, 2);
		points = new double[2];
		
		//cordinates are stored in the points array
		points[0] = x;
		points[1] = y;
		System.out.println(points[0] +" " + points[1]);
		return points;
	}

	public double getAngle() {
		
		return angle;
	}
	public void run(long time){
		fire(time, points[0],points[1]);
		
	}
	
}
