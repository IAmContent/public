package com.iamcontent.robotics.arm.edge.action;

/**
 * {@link Action}s of the elbow.
 * @author Greg Elderfield
 */
public enum ElbowAction implements Action {
	EXTEND {
		public byte getActionBits() {
			return (byte) 0x10;
		}
	},
	FLEX {
		public byte getActionBits() {
			return (byte) 0x20;
		}
	},
	STOP {
		public byte getActionBits() {
			return (byte) 0x00;
		}
	};

	@Override
	public void applyTo(Actor actor) {
		actor.setElbow(this);
	}
}