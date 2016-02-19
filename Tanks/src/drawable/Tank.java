package drawable;

import Main.Main;
import Main.Ticker;

import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongConsumer;

public abstract class Tank implements Drawable2 {
	private double barrelAngle = 0.0;
	private double tankAngle = 0.0;
	private int gas = 500;
	private Point location;
	private BufferedImage image;
	private double healthPercent;
	private String name;
	private int launchPower;

	private int motionTickerID = -1;
	private int cannonTickerID = -1;
	private boolean goLeft;
	private boolean counterClockwise;

	public Tank() {
		healthPercent = 1.0f;
		name = "";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/img/temporaryTank.png"));
		} catch (IOException e) {
			System.out.println("The tank file requested does not exist! Please fix this before continuing!");
		}
		location = new Point(100, 100000);
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public double getHealth() {
		return healthPercent;
	}

	public void setHealth(double healthPercent) {
		this.healthPercent = healthPercent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLaunchPower() {
		return launchPower;
	}

	public void setLaunchPower(int launchPower) {
		this.launchPower = launchPower;
	}

	public void adjustLaunchPower(int launchPower) {
		this.launchPower = Math.max(0, this.launchPower + launchPower);
	}

	public void setBarrelAngle(double angle) { barrelAngle = angle; }

	public double getBarrelAngle() { return barrelAngle; }

	public void adjustBarrelAngle(double angle) {
		barrelAngle += angle;
	}

	public void setTankAngle(double angle) { tankAngle = angle; }

	public double getTankAngle() { return tankAngle; }

	public void setGas(int gas) { this.gas = gas; }

	public int getGas() { return gas; }

	@Override
	public Point getLocation() {
		return location;
	}

	@Override
	public int getX() {
		return (int) location.getX();
	}

	@Override
	public int getY() {
		return (int) location.getY();
	}

	@Override
	public BufferedImage queryImage() {
		return image;
	}

	public void startMotion(boolean goLeft) {
		this.goLeft = goLeft;
		if (motionTickerID == -1) motionTickerID = Ticker.addMethod(this::moveTank);
		Main.sound.loadSound("sounds/Movement.wav");
		Main.sound.runLoop();
	}

	public void stopMotion() {
		Ticker.removeMethod(motionTickerID);
		motionTickerID = -1;
		Main.sound.stop();
	}

	public void aimCannon(boolean counterClockWise) {
		this.counterClockwise = counterClockWise;
		if (cannonTickerID == -1) cannonTickerID = Ticker.addMethod(this::rotateCannon);
		Main.sound.loadSound("sounds/Bounce.wav");
		Main.sound.runLoop();
	}

	public void stopAimCannon() {
		Ticker.removeMethod(cannonTickerID);
		cannonTickerID = -1;
		Main.sound.stop();
	}

	private void moveTank(long elapsedNanos) {
		double speed = 100.0 * ((double) elapsedNanos / 1000000000);
		double newX = location.getX() + (goLeft ? -speed : speed);
		if (newX > 0 && newX < Main.xLength) location.setLocation(newX, 1000);
	}

	private void rotateCannon(long elapsedNanos) {
		double rate = 3.0 * ((double) elapsedNanos / 1000000000);
		barrelAngle += (counterClockwise ? rate : -rate);
	}

	/**
	 * Used to find the angle of rotation of the tank for keeping it level on the terrain
	 *
	 * @param x X position of the tank
	 * @param points Terrain array of points for determining slope
	 *
	 * @return the angle in radians
	 */
	public double angle(int x, int[][] points) {
		int y1 = 0;
		int y2 = 0;
		for(int i = 0; i < points[x].length; i += 1){
			if(points[x][i] > 0){
				y1 = i;
				break;
			}
		}

		for(int i = 0; i < points[x].length; i += 1){
			if(points[x - 15][i] > 0){
				y2 = i;
				break;
			}
		}
		double angle = Math.atan((y2 - y1) / -15.0);
		tankAngle = angle;
		return angle;
	}//end of angle method
}
