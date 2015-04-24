package com.iamcontent.robotics.arm.edge.action;

/**
 * Defines an object that can perform {@link Action}s.
 * @author Greg Elderfield
 */
public interface Actor {
	void setBase(BaseAction base);
	void setShoulder(ShoulderAction shoulder);
	void setElbow(ElbowAction elbow);
	void setWrist(WristAction wrist);
	void setGripper(GripperAction gripper);
	void setLed(LedAction led);
	void stopAllMovement();
}
