package com.iamcontent.robotics.arm.edge.action;

/**
 * {@link Action}s affecting the robot arm generally.
 * @author Greg Elderfield
 */
public enum GeneralAction implements Action {
	STOP_ALL_MOVEMENT {
		@Override
		public void applyTo(Actor actor) {
			actor.stopAllMovement();
		}
	};

	@Override
	public byte getActionBits() {
		throw new UnsupportedOperationException("Action " + this + " has no corresponding action bits.");
	}
}
