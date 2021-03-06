package org.team4159.robot.modules;

import org.team4159.robot.HWPorts;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Victor;

public class BallLauncherModule extends Module
{
	private static final double OPERATOR_COEFFICIENT = 0.8;

	private final Victor upperMotor = new Victor (HWPorts.Digital_Sidecar.PWM.BALL_LAUNCHER_UPPER_MOTOR);
	private final Victor lowerMotor = new Victor (HWPorts.Digital_Sidecar.PWM.BALL_LAUNCHER_LOWER_MOTOR);
	private double speed = 0.0;
	
	public BallLauncherModule()
	{
		//upperMotor.enableDeadbandElimination(true);
		//lowerMotor.enableDeadbandElimination(true);
	}
	
	public void runAutonomous()
	{
		double distanceInches = UltrasonicModule.getInstance ().getBackDistance ();
		if (ModuleController.getModeElapsedTime () < 12000)
		{
			set (0.202*.975);
			DriverStationModule.getInstance ().printToDriverStation (0, "BALL LAUNCHING IN PROGRESS!");
		}
		else
		{
			set (0.0);
		}
		
		/*
		 * minimum velocity equation:  = sqrt of (-9.8 * horizontal distance^2)/(2*cos^2 theta(vertical distance - horizontal distance*tan theta))
		 * 
		 * minimum power needed = 3600 * min velocity / ((1.85)(7300)(Math.PI)) 
		 * this is a proportion already, can be plugged right into the motor.set(minimum power);
		 * 
		 * you need to figure out how to move to a designated position and then use that position for horizontal distance.
		 * the vertical distance is 68 inches, theta = 45;
		 * 
		 * Also needed to implement drive to the correct position then trigger a boolean to fire 
		 * can use ultrasonic sensors to find distance
		 * 
		 */
	}
	
	public void runOperator()
	{
		CameraStickModule csm = CameraStickModule.getInstance();
		speed = csm.getLauncherSpeed ();
		DriverStationModule.getInstance ().printToDriverStation (0, "BLS: " + (Math.floor (speed *OPERATOR_COEFFICIENT* 1000.) / 10.));
		set (speed * OPERATOR_COEFFICIENT);
		//if(csm.isGetSensor())
		//{
		//	System.out.println( " dist is : " + UltrasonicModule.getInstance().getBackDistance());
			/*double minVelocity = Math.sqrt((9.8*UltrasonicModule.getInstance().getBackDistance()*UltrasonicModule.getInstance().getBackDistance())/2*(Math.cos(45)*Math.cos(45))*(72-UltrasonicModule.getInstance().getBackDistance()*Math.tan(45)));
			double minPower = 3600*minVelocity / (1.85*7300*Math.PI);
			System.out.println("minPower is : " + minPower);*/
		//}
	}
	
	public void set (double speed)
	{
		this.speed = speed;
		lowerMotor.set (-(speed * 1.00));
		upperMotor.set (-(speed * 0.50));
	}
	
	private static BallLauncherModule instance;
	public static synchronized BallLauncherModule getInstance ()
	{
		if (instance == null)
			instance = new BallLauncherModule ();
		return instance;
	}
}
