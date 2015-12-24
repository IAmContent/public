package com.iamcontent.device.servo.command;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A compound command.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a servo. 
 */
public abstract class CompoundServoCommand<C> extends ArrayList<ServoCommand<C>> {

	@SafeVarargs
	public CompoundServoCommand(ServoCommand<C>... commands) {
		addAll(Arrays.asList(commands));
	}
	
	private static final long serialVersionUID = 1L;
}
