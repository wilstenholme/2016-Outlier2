package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5687.robot.Constants;
import org.usfirst.frc.team5687.robot.Robot;

import static org.usfirst.frc.team5687.robot.Robot.driveTrain;

/**
 *
 */
public class AutoTraverseOnly extends CommandGroup{

    double traverseSpeed;
    double rotateAngle;


    protected void initialize() {

        switch (Robot.robot.getSelectedDefense()){
            case "LowBar":
                traverseSpeed = Constants.Autonomous.staticDefenseTraverseSpeeds.LOW_BAR_SPEED;
                break;
            case "Moat":
                traverseSpeed = Constants.Autonomous.staticDefenseTraverseSpeeds.MOAT_SPEED;
                break;
            case "RockWall":
                traverseSpeed = Constants.Autonomous.staticDefenseTraverseSpeeds.ROCK_WALL_SPEED;
                break;
            case "Ramparts":
                traverseSpeed = Constants.Autonomous.staticDefenseTraverseSpeeds.RAMPARTS_SPEED;
                break;
            case "RoughTerrain":
                traverseSpeed = Constants.Autonomous.staticDefenseTraverseSpeeds.ROUGH_TERRAIN_SPEED;
                break;
        }


        switch (Robot.robot.getSelectedPosition()){
            case "A":
                rotateAngle=50;
                break;
            case "B":
               rotateAngle=30;
                break;
            case "C":
                rotateAngle=15;
                break;
            case "D":
                rotateAngle=-10;
                break;
            case "E":
                rotateAngle=-25;
                break;
        }
    }


    protected void execute() {

    }



    public  AutoTraverseOnly() {

        //none of this will work 'til it is merged with Rotate and AutoTraverse commands

        //addSequential(new AutoDrive(.5,2));
        // addSequential(new autoTraverseStaticDefense(traverseSpeed));
        //addSequential(new autoAllign(rotateAngle));

        DriverStation.reportError("Traversing "+Robot.robot.getSelectedDefense()+", in position "+Robot.robot.getSelectedPosition()+", at "+traverseSpeed+" speed.",false);

    }
}
