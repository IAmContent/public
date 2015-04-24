package com.iamcontent.robotics.arm.edge.action;

/**
 * {@link Action}s of the LED.
 * @author Greg Elderfield
 */
public enum LedAction implements BitControlledAction {
	ON {
		public byte getActionBits() {
			return (byte) 0x01;
		}
	},
	OFF {
		public byte getActionBits() {
			return (byte) 0x00;
		}
	};

	@Override
	public void applyTo(Actor actor) {
		actor.setLed(this);
	}
}