package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTurnOnly extends CommandGroup {

    public AutoTurnOnly(int position) {

        double rotateAngle = 0;

        switch (position){
            case 1:

                // Run forward
                addSequential(new AutoDrive(.7, 80.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(60f));

                // Run forward
                addSequential(new AutoDrive(.7, 60.0f));


                break;
            case 2:
                // Run forward
                addSequential(new AutoDrive(.7, 118.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(58f));

                // Run forward
                addSequential(new AutoDrive(.6, 14.0f));

                break;
            case 3:
                // Turn towards the center
                addSequential(new AutoAlign(45f));

                // Run forward 35 inches
                addSequential(new AutoDrive(.7, 34.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(-2.0f));

                // Run forward 50 inches
                addSequential(new AutoDrive(.5, 36.0f));

                break;
            case 4:
                // Turn towards the tower
                addSequential(new AutoAlign(-45f));

                // Run forward 35 inches
                addSequential(new AutoDrive(.7, 18.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(1.0f));

                // Run forward 50 inches
                addSequential(new AutoDrive(.5, 42.0f));

                break;
            case 5:

                // Turn towards the center
                addSequential(new AutoAlign(-45f));

                // Run forwar
                addSequential(new AutoDrive(.7, 90.0f));

                // Turn towards the tower
                addSequential(new AutoAlign(1.0f));

                // Run forward 50 inches
                addSequential(new AutoDrive(.5, 42.0f));

                break;
        }

    }

}
