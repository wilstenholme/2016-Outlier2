package org.usfirst.frc.team5687.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Created by Ben Bernard on 3/5/2016.
 */
public class AutoShootOnly extends CommandGroup {

    public AutoShootOnly() {

        addParallel(new Prime());
        addSequential(new AutoChaseTarget());
        addSequential(new Fire());
    }

}
