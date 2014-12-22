package com.ralitski.util.comm.packet.event;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ralitski.util.MapList;
import com.ralitski.util.Pair;
import com.ralitski.util.comm.packet.Packet;
import com.ralitski.util.doc.Usage;

/**
 * @author ralitski
 */
@Usage(Usage.EXTERNAL)
public class PacketEventManager {

    private MapList<String, OMPair> handlers = new MapList<String, OMPair>();
    
    public void clearHandlers() {
    	handlers.clear();
    }

    /**
     * @param o object to be added to the handler list
     */
    @Usage(Usage.EXTERNAL)
    public void addHandler(Object o) {
        for (Method m : o.getClass().getDeclaredMethods()) {
            PacketEventHandler handler = m.getAnnotation(PacketEventHandler.class);
            Class<?>[] params = m.getParameterTypes();
            if (handler != null && params.length == 1 && params[0] == PacketReceivedEvent.class) {
                handlers.add(handler.value(), new OMPair(o, m));
                m.setAccessible(true);
            }
        }
    }

    /**
     * calls a {@link com.ralitski.util.comm.packet.event.PacketReceivedEvent}
     *
     * @param p the packet that was received
     * @return the thrown event
     */
    @Usage(Usage.INTERNAL)
    public PacketReceivedEvent callEvent(Packet p) {
        PacketReceivedEvent event = new PacketReceivedEvent(p);
        String channel = p.getChannel();
        if ("ALL".equals(channel)) {
            for (String s : handlers.keySet()) {
                for (OMPair pair : handlers.get(s)) {
                    callEvent(pair, event);
                }
            }
        } else {
            if(handlers.containsKey(channel)) {
                for (OMPair pair : handlers.get(channel)) {
                    callEvent(pair, event);
                }
            }
            if(handlers.containsKey("ALL")) {
                for (OMPair pair : handlers.get("ALL")) {
                    callEvent(pair, event);
                }
            }
        }
        return event;
    }

    @Usage(Usage.INTERNAL)
    private void callEvent(OMPair pair, PacketReceivedEvent event) {
        Object o = pair.getKey();
        Method m = pair.getValue();
        try {
            m.invoke(o, event);
        } catch (Exception ex) {
            Logger.getLogger(PacketEventManager.class.getName()).log(Level.SEVERE,
                    "Could not call packet event for Object[" + o.toString() + "] Method[" + m.getName() + "]",
                    ex);
        }
    }

    //so I dont have to deal with nested generics
    private class OMPair extends Pair<Object, Method> {

        public OMPair(Object o, Method m) {
            super(o, m);
        }
    }
}
