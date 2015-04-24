package com.iamcontent.robotics.arm.edge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.iamcontent.robotics.arm.edge.action.Action;
import com.iamcontent.robotics.arm.edge.action.ElbowAction;
import com.iamcontent.robotics.arm.edge.action.LedAction;
import com.iamcontent.robotics.arm.edge.action.WristAction;

/**
 * An example driver for the {@link RoboticEdgeArm}. Useful for manual testing.
 * @author Greg Elderfield
 */
public class RoboticEdgeArmExampleDriver implements Runnable {
	final RoboticEdgeArm arm = new RoboticEdgeArm();
	final BufferedReader in = in();
	
	public static void main(String[] args) {
		final RoboticEdgeArmExampleDriver driver = new RoboticEdgeArmExampleDriver();
		driver.run();
	}
	
	@Override
	public void run() {
		for(String c = nextCommand(); shouldContinue(c); c = nextCommand())
			execute(c);
	}

	private void execute(String command) {
		if (isStop(command))
			arm.stopMovements();
		else
			execute(parsed(command));
	}

	private void execute(Action action) {
		if (action==null)
			return;
		action.applyTo(arm);
	}

	private Action parsed(String command) {
		return ElbowAction.EXTEND;
	}

	private boolean isStop(String command) {
		return command.isEmpty() || "STOP".equals(command);
	}

	private boolean shouldContinue(String command) {
		return command!=null && !isQuit(command);
	}

	private boolean isQuit(String instruction) {
		return "Q".equals(instruction);
	}

	private String nextCommand() {
		try {
			final String line = in.readLine();
			return line==null ? null : line.trim().toUpperCase();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private BufferedReader in() {
		return new BufferedReader(new InputStreamReader(System.in));
	}
}
