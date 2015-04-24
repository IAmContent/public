package com.iamcontent.robotics.arm.edge.action;

/**
 * {@link Action}s of the wrist.
 * @author Greg Elderfield
 */
public enum WristAction implements Action {
	EXTEND {
		public byte getActionBits() {
			return (byte) 0x04;
		}
	},
	FLEX {
		public byte getActionBits() {
			return (byte) 0x08;
		}
	},
	STOP {
		public byte getActionBits() {
			return (byte) 0x00;
		}
	};

	@Override
	public void applyTo(Actor actor) {
		actor.setWrist(this);
	}
}