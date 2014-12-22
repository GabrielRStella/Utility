package com.ralitski.util.comm.packet.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * throw this one on some methods to catch packet reception events
 *
 * @author ralitski
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PacketEventHandler {
    /**
     * @return the channels the method with this annotation will listen to;
     *         note, multiple methods per class are supported,
     *         so there may be separate methods to handle separate channels
     */
    String value() default "DEFAULT";
}
