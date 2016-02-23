package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5687.robot.OI;
import org.usfirst.frc.team5687.robot.Robot;
import org.usfirst.frc.team5687.robot.subsystems.DriveTrain;

import java.util.Date;

/**
 * Autonomous command to run the drivetrain.
 * For now, runs at a preset speed for a preset time.
 * Eventually we will want to add distance-based options.
 */

public class AutoDrive extends Command {
    DriveTrain driveTrain = Robot.driveTrain;
    OI oi = Robot.oi;
    private long end = 0;
    private int timeToDrive = 0;
    private double inchesToDrive = 0;
    private double rightSpeed = 0;
    private double leftSpeed = 0;
    private double inchesDriven = 0;
    private boolean driveByTime;

    //Drive based on time
    public AutoDrive(double speed, int timeToDrive) {
        requires(driveTrain);
        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.timeToDrive = timeToDrive;
        this.driveByTime = true;

        DriverStation.reportError("Driving by Time",false);
    }

    //Drive based on distance
    public AutoDrive(double speed, double inchesToDrive) {
        requires(driveTrain);
        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.inchesToDrive = inchesToDrive;
        this.driveByTime = false;

        DriverStation.reportError("Driving by Distance",false);
    }

    @Override
    protected void initialize() {
        DriverStation.reportError(String.format("Accelerating to %1$f\n", rightSpeed), false);
        end = (new Date()).getTime() + timeToDrive;

        driveTrain.resetDriveEncoders();
    }

    @Override
    protected void execute() {
        driveTrain.tankDrive(leftSpeed, rightSpeed);
    }

    @Override
    protected boolean isFinished() {
        if (!driveByTime) {
            inchesDriven = driveTrain.getRightDistance();
            return Math.abs(inchesDriven)>=Math.abs(inchesToDrive);
        }

        else if(driveByTime) {
            long now = (new Date()).getTime();
            return now > end;
        }
    return true;
    }

    @Override
    protected void end() {
        driveTrain.tankDrive(0,0);
    }

    @Override
    protected void interrupted() {

    }
}
