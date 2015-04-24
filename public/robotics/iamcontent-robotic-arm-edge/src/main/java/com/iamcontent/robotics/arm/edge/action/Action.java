package com.iamcontent.robotics.arm.edge.action;

/**
 * Represents an action that may be performed by the robot arm.
 * 
 * @author Greg Elderfield
 */
public interface Action {
	/**
	 * Causes the action represented by the implementing class to be performed by the given {@link Actor}. 
	 */
	void applyTo(Actor actor);
}