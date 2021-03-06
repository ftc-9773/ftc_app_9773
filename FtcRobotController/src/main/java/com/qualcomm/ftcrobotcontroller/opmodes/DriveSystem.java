package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by pranavb on 11/8/15.
 */
public class DriveSystem {
    DcMotor frontLeft, frontRight, rearLeft, rearRight;
    final static int ENCODER_CPR = 1120;
    final static double GEAR_RATIO = 1;
    final static double diameter = 4.0;




    public class Wheel {
        public class MecanumWheel{
            final double frictionCoefficientMat = 1;
            final double frictionCoefficientMountain = 1;
        }
    }

    public DriveSystem(DcMotor fL, DcMotor fR, DcMotor rL, DcMotor rR) {
        this.frontLeft = fL;
        this.frontRight = fR;
        this.rearLeft = rL;
        this.rearRight = rR;
    }

    public void mecanumWheelDrive(float strafeDirection, float strafeThrottle, float turnDirection, float turnThrottle) {
        strafeDirection = Range.clip(strafeDirection, -1, 1);
        strafeThrottle = Range.clip(strafeThrottle, -1, 1);
        turnThrottle = Range.clip(turnThrottle, -1, 1);
        turnDirection = Range.clip(turnDirection, -1, 1);

        float frontLeftPwr = (Range.clip(strafeThrottle + strafeDirection, -1, 1) - Range.clip(turnThrottle + turnDirection, -1, 1));
        float frontRightPwr = (Range.clip(strafeDirection - strafeThrottle, -1, 1) + Range.clip(turnThrottle - turnDirection, -1, 1));
        float rearLeftPwr = Range.clip(strafeDirection - strafeThrottle, -1, 1) + Range.clip(turnThrottle + turnDirection, -1, 1);
        float rearRightPwr = Range.clip(strafeThrottle + strafeDirection, -1, 1) - Range.clip(turnThrottle - turnDirection, -1, 1);

        frontLeft.setPower(Range.clip(frontLeftPwr, -1, 1));
        rearRight.setPower(Range.clip(rearRightPwr, -1, 1));
        frontRight.setPower(Range.clip(frontRightPwr, -1, 1));
        rearLeft.setPower(Range.clip(rearLeftPwr, -1, 1));
    }

    public double mecanumWheelAutoDrive(double dist, double pwr){
        /*
        frontLeft.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        frontRight.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rearLeft.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rearRight.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);*/

        /*frontLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        frontRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        rearRight.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
        rearLeft.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);*/

        double circumference = Math.PI * diameter;
        double rotations = dist / circumference;
        double counts = rotations * ENCODER_CPR * GEAR_RATIO;

        /*frontRight.setTargetPosition((int) -counts);
        frontLeft.setTargetPosition((int) counts);
        rearLeft.setTargetPosition((int) counts);
        rearRight.setTargetPosition((int) -counts);*/



        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + (int) counts);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + (int) counts);
        rearLeft.setTargetPosition(rearLeft.getCurrentPosition() + (int) counts);
        rearRight.setTargetPosition(rearRight.getCurrentPosition() + (int) counts);

        frontLeft.setPower(pwr);
        frontRight.setPower(pwr);
        rearLeft.setPower(pwr);
        rearRight.setPower(pwr);

        /*frontLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        frontRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        rearLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        rearRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);*/

        return counts;
    }
}
