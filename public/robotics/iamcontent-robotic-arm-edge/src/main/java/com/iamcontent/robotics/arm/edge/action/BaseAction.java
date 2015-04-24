package com.iamcontent.robotics.arm.edge.action;

/**
 * {@link Action}s to rotate the base of the arm.
 * @author Greg Elderfield
 */
public enum BaseAction implements BitControlledAction {
	LEFT {
		public byte getActionBits() {
			return (byte) 0x02;
		}
	},
	RIGHT {
		public byte getActionBits() {
			return (byte) 0x01;
		}
	},
	STOP {
		public byte getActionBits() {
			return (byte) 0x00;
		}
	};

	@Override
	public void applyTo(Actor actor) {
		actor.setBase(this);
	}
}