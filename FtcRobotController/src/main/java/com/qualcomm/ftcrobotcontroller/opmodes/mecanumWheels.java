package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by pranavb on 10/8/15.
 */
public class mecanumWheels extends OpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor rearLeft;
    DcMotor rearRight;
    float frontLeftPwr;
    float frontRightPwr;
    float rearLeftPwr;
    float rearRightPwr;

    @Override
    public void init(){
        frontLeft = hardwareMap.dcMotor.get("fMotorL");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight = hardwareMap.dcMotor.get("fMotorR");
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        rearLeft = hardwareMap.dcMotor.get("rMotorL");
        rearRight = hardwareMap.dcMotor.get("rMotorR");

    }

    @Override
    public void loop(){
        float strafeDirection = gamepad1.left_stick_x;
        float throttle = gamepad1.left_stick_y;

        strafeDirection = Range.clip(strafeDirection, -1, 1);
        throttle = Range.clip(throttle, -1, 1);

        frontLeft.setPower(throttle + strafeDirection);
        rearRight.setPower(throttle + strafeDirection);
        frontRight.setPower(throttle - strafeDirection);
        rearLeft.setPower(throttle - strafeDirection);
    }
    @Override
    public void stop() {

    }

    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }
}
