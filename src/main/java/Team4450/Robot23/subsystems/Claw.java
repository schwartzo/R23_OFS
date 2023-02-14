package Team4450.Robot23.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import Team4450.Lib.FXEncoder;
import Team4450.Lib.Util;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static Team4450.Robot23.Constants.*;

public class Claw extends SubsystemBase
{
    private WPI_TalonFX     motor = new WPI_TalonFX(CLAW_MOTOR);
    private FXEncoder       encoder = new FXEncoder(motor);
    private DigitalInput    limitSwitch = new DigitalInput(ARM_SWITCH);

    private final double    ARM_MAX = 1000;

    public Claw()
    {
        Util.consoleLog();
    }

    public void setPower(double power)
    {
        // If power positive, which means open, check limit switch stop if true.
        // If power negative, which means close, check encoder for max height, stop if there.

        if ((power > 0 && limitSwitch.get()) || (power < 0 && encoder.get() >= ARM_MAX)) power = 0;

        if (limitSwitch.get()) encoder.reset();

        motor.set(power);
    }

    public void stop()
    {
        motor.stopMotor();
    }

    public int getPosition()
    {
        return encoder.get();
    }

    public boolean getSwitch()
    {
        return limitSwitch.get();
    }

    public void updateDS()
    {

    }
}
