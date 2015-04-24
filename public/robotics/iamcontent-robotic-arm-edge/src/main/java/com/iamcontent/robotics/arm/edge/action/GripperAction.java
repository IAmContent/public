package com.iamcontent.robotics.arm.edge.action;

/**
 * {@link Action}s of the gripper.
 * @author Greg Elderfield
 */
public enum GripperAction implements BitControlledAction {
	CLOSE {
		public byte getActionBits() {
			return (byte) 0x01;
		}
	},
	OPEN {
		public byte getActionBits() {
			return (byte) 0x02;
		}
	},
	STOP {
		public byte getActionBits() {
			return (byte) 0x00;
		}
	};

	@Override
	public void applyTo(Actor actor) {
		actor.setGripper(this);
	}
}