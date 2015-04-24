package com.iamcontent.robotics.arm.edge;

import static com.iamcontent.io.usb.EasedUsbDevice.eased;

import javax.usb.UsbConst;
import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;

import com.iamcontent.io.usb.EasyUsbDevice;
import com.iamcontent.io.usb.Usb;
import com.iamcontent.robotics.arm.edge.action.Action;
import com.iamcontent.robotics.arm.edge.action.Actor;
import com.iamcontent.robotics.arm.edge.action.BaseAction;
import com.iamcontent.robotics.arm.edge.action.ElbowAction;
import com.iamcontent.robotics.arm.edge.action.GripperAction;
import com.iamcontent.robotics.arm.edge.action.LedAction;
import com.iamcontent.robotics.arm.edge.action.ShoulderAction;
import com.iamcontent.robotics.arm.edge.action.WristAction;

/**
 * Issues {@link Action} instructions to the Maplin/OWI Robotic Edge Arm.
 * 
 * @author Greg Elderfield
 */
public class RoboticEdgeArm implements Actor {
	public static final short VENDOR_ID = 0x1267;
	public static final short PRODUCT_ID = 0x0;

	private final EasyUsbDevice device;
	
	private LedAction led = LedAction.OFF;
	private BaseAction base = BaseAction.STOP;
	private ShoulderAction shoulder = ShoulderAction.STOP;
	private ElbowAction elbow = ElbowAction.STOP;
	private WristAction wrist = WristAction.STOP;
	private GripperAction gripper = GripperAction.STOP;

	/**
	 * Creates an instance with the first Robot Arm device that is found.
	 */
	public RoboticEdgeArm() {
		device = Usb.device(VENDOR_ID, PRODUCT_ID);
	}

	/**
	 * Creates an instance with the given UsbDevice.
	 */
	public RoboticEdgeArm(UsbDevice device) {
		this.device = eased(device);
	}

	public BaseAction getBase() {
		return base;
	}

	@Override
	public void setBase(BaseAction base) {
		if (stateChanged(this.base, base)) {
			this.base = base;
			sendStateToDevice();
		}
	}

	public ShoulderAction getShoulder() {
		return shoulder;
	}

	@Override
	public void setShoulder(ShoulderAction shoulder) {
		if (stateChanged(this.shoulder, shoulder)) {
			this.shoulder = shoulder;
			sendStateToDevice();
		}
	}

	public ElbowAction getElbow() {
		return elbow;
	}

	@Override
	public void setElbow(ElbowAction elbow) {
		if (stateChanged(this.elbow, elbow)) {
			this.elbow = elbow;
			sendStateToDevice();
		}
	}

	public WristAction getWrist() {
		return wrist;
	}

	@Override
	public void setWrist(WristAction wrist) {
		if (stateChanged(this.wrist, wrist)) {
			this.wrist = wrist;
			sendStateToDevice();
		}
	}

	public GripperAction getGripper() {
		return gripper;
	}

	@Override
	public void setGripper(GripperAction gripper) {
		if (stateChanged(this.gripper, gripper)) {
			this.gripper = gripper;
			sendStateToDevice();
		}
	}

	public LedAction getLed() {
		return led;
	}

	@Override
	public void setLed(LedAction led) {
		if (stateChanged(this.led, led)) {
			this.led = led;
			sendStateToDevice();
		}
	}
	
	/**
	 * Sets the state of the device according to the given parameters, each of
	 * which may be null, indicating 'do not change'.
	 */
	public void setState(BaseAction base, ShoulderAction shoulder, ElbowAction elbow, WristAction wrist, GripperAction gripper, LedAction led) {
		boolean stateHasChanged = false;
		if (stateChanged(this.base, base)) {
			this.base = base;
			stateHasChanged = true;
		}
		if (stateChanged(this.shoulder, shoulder)) {
			this.shoulder = shoulder;
			stateHasChanged = true;
		}
		if (stateChanged(this.elbow, elbow)) {
			this.elbow = elbow;
			stateHasChanged = true;
		}
		if (stateChanged(this.wrist, wrist)) {
			this.wrist = wrist;
			stateHasChanged = true;
		}
		if (stateChanged(this.gripper, gripper)) {
			this.gripper = gripper;
			stateHasChanged = true;
		}
		if (stateChanged(this.led, led)) {
			this.led = led;
			stateHasChanged = true;
		}
		if (stateHasChanged) {
			sendStateToDevice();
		}
	}

	@Override
	public void stopAllMovement() {
		final LedAction leaveLedUnchanged = null;
		setState(BaseAction.STOP, ShoulderAction.STOP, ElbowAction.STOP, WristAction.STOP, GripperAction.STOP, leaveLedUnchanged);
	}
	
	/**
	 * Sends a control message to the device. The message that is sent is the
	 * message that is required to synchronize the device's state with the state
	 * of this instance.
	 */
	protected void sendStateToDevice() {
		final UsbControlIrp controlRequest = controlRequest();
		controlRequest.setData(currentState());
		device.syncSubmit(controlRequest);
	}

	protected UsbControlIrp controlRequest() {
		final byte requestType = UsbConst.REQUESTTYPE_TYPE_VENDOR;
		final byte request = UsbConst.REQUEST_GET_DESCRIPTOR;
		final short value = 0x100;
		final short index = 0;
		return device.createUsbControlIrp(requestType, request, value, index);
	}

	/**
	 * Indicates whether the new state represents a state change from the current state. 
	 * A null value for the new state indicates that the state should not be changed.
	 * 
	 * @return true if the new state represents a state change from the current state, false otherwise.
	 */
	private boolean stateChanged(Action currentState, Action newState) {
		return (newState != null) && (currentState != newState);
	}

	private byte[] currentState() {
		return new byte[] {
				(byte) (shoulder.getActionBits() | getElbow().getActionBits() | getWrist().getActionBits() | getGripper().getActionBits()), 
						getBase().getActionBits(), 
						getLed().getActionBits() };
	}
}
