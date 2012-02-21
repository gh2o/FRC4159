package org.team4159.robot.modules;

import edu.wpi.first.wpilibj.RobotDrive;

public class DriveModule extends Module
{
	private final RobotDrive drive = PIDModule.getInstance ().createDrive ();
	
	private DriveModule ()
	{
		essential = true;
		drive.setExpiration (0.20);
	}
	
	public void drive (double move, double rotate)
	{
		drive.arcadeDrive (move, rotate);
	}
	
	public void stop ()
	{
		drive.stopMotor ();
	}
	
	public void runDisabled ()
	{
		drive.stopMotor ();
	}
	
	public void runAutonomous ()
	{
		UltrasonicModule um = UltrasonicModule.getInstance();
		if(um.getBackDistance()> 50)
		{
			drive.arcadeDrive(-.2, 0);
		}
	}
	
	public void runOperator ()
	{
		DriveStickModule dsm = DriveStickModule.getInstance ();
		//if (dsm.isBridgeManipButtonPressed ())
		//	drive.stopMotor ();
		//else
			drive.arcadeDrive (dsm.getMoveValue (), dsm.getRotateValue ());
	}
	
	private static DriveModule instance;
	public static synchronized DriveModule getInstance ()
	{
		if (instance == null)
			instance = new DriveModule ();
		return instance;
	}
}
