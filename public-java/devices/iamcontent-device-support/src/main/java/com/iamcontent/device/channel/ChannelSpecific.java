package com.iamcontent.device.channel;

/**
 * Represents an object that is specific to a single channel.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify a channel. 
 */
public interface ChannelSpecific<C> {
	C getChannelId();
}
