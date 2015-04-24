package com.iamcontent.robotics.arm.edge.action;

/**
 * An action that is controlled by specific bits.
 * @author Greg Elderfield
 */
public interface BitControlledAction extends Action {
	/**
	 * @return The bits that cause the device to perform the action represented by the implementing class.
	 */
	byte getActionBits();
}
