package Team4450.Robot23.commands;

import Team4450.Lib.SynchronousPID;
import Team4450.Lib.Util;
import Team4450.Robot23.subsystems.Arm;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Moves the arm to a target position.
 */
public class ExtendArm extends CommandBase 
{
    public static double DEFAULT_POSITION = 100;

    private final Arm       arm;
    private double          targetPostion;    // Revolutions of motor.
    private SynchronousPID  controller = new SynchronousPID(.01, 0, 0);
    private final double    tolerance = .5, maxPower = .3;
    private double          lastTimeCalled;

    public ExtendArm(Arm arm, double target)
    {
        Util.consoleLog();

        this.arm = arm;

        this.targetPostion = target;

        addRequirements(arm);
    }

    @Override
    public void initialize()
    {
        Util.consoleLog();

        controller.reset();

        controller.setSetpoint(targetPostion);

        controller.setOutputRange(-maxPower, maxPower);

        SmartDashboard.putBoolean("ExtendArm", true);

        lastTimeCalled = Util.timeStamp();
    }

    @Override
    public void execute()
    {
        double time = Util.getElaspedTime(lastTimeCalled);

        lastTimeCalled = Util.timeStamp();

        double power = controller.calculate(arm.getPosition(), time);

        arm.setPower(power);
    }

    @Override
    public boolean isFinished()
    {
        return controller.onTarget(tolerance);
    }

    @Override
    public void end(boolean interrupted) 
    {
        arm.stop();

        Util.consoleLog("interrupted=%b", interrupted);

        SmartDashboard.putBoolean("ExtendArm", false);
    }
}
