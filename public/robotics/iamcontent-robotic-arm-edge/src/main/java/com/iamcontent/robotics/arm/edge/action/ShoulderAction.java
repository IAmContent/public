package com.iamcontent.robotics.arm.edge.action;

/**
 * {@link Action}s of the shoulder.
 * @author Greg Elderfield
 */
public enum ShoulderAction implements Action {
	BACKWARDS {
		public byte getActionBits() {
			return (byte) 0x40;
		}
	},
	FORWARDS {
		public byte getActionBits() {
			return (byte) 0x80;
		}
	},
	STOP {
		public byte getActionBits() {
			return (byte) 0x00;
		}
	};

	@Override
	public void applyTo(Actor actor) {
		actor.setShoulder(this);
	}
}