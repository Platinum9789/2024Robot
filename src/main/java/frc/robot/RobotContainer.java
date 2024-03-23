// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.PrepareLaunch;
//import frc.robot.subsystems.Robot;
//import frc.robot.subsystems.PWMDrivetrain;
//import frc.robot.subsystems.PWMLauncher;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.CANLauncher;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  //private final PWMDrivetrain m_drivetrain = new PWMDrivetrain();
  private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  //private final PWMLauncher m_launcher = new PWMLauncher();
  private final CANLauncher m_launcher = new CANLauncher();
  private String RobotContainer = "m_robotContainer";
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected = "AutoSelected";
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  //private final GenericHID flightStickLeft = new GenericHID(1);
  // private final GenericHID flightStickRight = new GenericHID(2);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_chooser.setDefaultOption("Default", new customAuto());
    //m_chooser.addOption("My Auto", kCustomAuto);
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // Set the default command for the drivetrain to drive using the joysticks
    m_drivetrain.setDefaultCommand(new RunCommand(() -> m_drivetrain.tankDrive(-Math.pow(m_driverController.getLeftY(), 3),
    -Math.pow(m_driverController.getRightY(), 3)), m_drivetrain));
    //m_drivetrain.setDefaultCommand(new RunCommand(() -> m_drivetrain.tankDrive(-Math.pow(-0.5*flightStickLeft.getRawAxis(1), 3),
    //-Math.pow(-0.5*flightStickRight.getRawAxis(1), 3)), m_drivetrain));
    
    /*Create an inline sequence to run when the operator presses and holds the A (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command */
    m_operatorController
        .a()
        .whileTrue(
            new PrepareLaunch(m_launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(m_launcher))
                .handleInterrupt(() -> m_launcher.stop()));

    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
    m_operatorController.leftBumper().whileTrue(m_launcher.getIntakeCommand());
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  public Command customAuto() {
    //return new RunCommand(() -> m_drivetrain.tankDrive(0, 0), m_drivetrain);
    return new SequentialCommandGroup(
      new WaitCommand(7) );
      new PrepareLaunch(m_launcher).withTimeout(4);
      new LaunchNote(m_launcher).withTimeout(4);
    }
}
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  
    //m_robotContainer = new RobotContainer();
    // An example command will be run in autonomous
      // switch (Private String AutoSelected. m_autoSelected){
      
      //   case kCustomAuto:
      //   return new RunCommand(() -> m_drivetrain.tankDrive(0, 0), m_drivetrain);
      //   break;
      //   case kDefaultAuto:
      //   default: return new RunCommand(() -> m_drivetrain.tankDrive(-0.5, -0.5), m_drivetrain)
      //   .withTimeout(1)
      //   .andThen(new RunCommand(() -> m_drivetrain.tankDrive(0, 0), m_drivetrain));
      //   break;
      //   return new SequentialCommandGroup(
      //     new WaitCommand(7) );
      //     new PrepareLaunch(m_launcher).withTimeout(4);
      //     new LaunchNote(m_launcher).withTimeout(4);
      // }
      }

