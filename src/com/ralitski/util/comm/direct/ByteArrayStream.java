package com.ralitski.util.comm.direct;

import java.util.Arrays;

public class ByteArrayStream {
	
	/*
	 * I basically mashed together ByteArrayInputStream and ByteArrayOutputStream. hope it works.
	 * edit: it dooooo
	 */

    private byte[] buf;

    private int count;

    private int pos;
    
    private int flushes;
    
    private boolean enableSmartFlush = true;

    public ByteArrayStream() {
        this(64);
    }

    public ByteArrayStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Negative initial size: "
                                               + size);
        }
        buf = new byte[size];
    }
    
    public void enableSmartFlush() {
    	enableSmartFlush = true;
    }
    
    public void disableSmartFlush() {
    	enableSmartFlush = false;
    }
    
    //writing

    private void ensureCapacity(int minCapacity) {
        // overflow-conscious code
        if (minCapacity - buf.length > 0)
            grow(minCapacity);
    }

    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = buf.length;
        int newCapacity = oldCapacity << 1;
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity < 0) {
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            newCapacity = Integer.MAX_VALUE;
        }
        buf = Arrays.copyOf(buf, newCapacity);
    }

    public synchronized void write(int b) {
    	//System.out.println(b);
        ensureCapacity(count + 1);
        buf[count] = (byte) b;
        count += 1;
    }

    public synchronized void write(byte b[], int off, int len) {
        if ((off < 0) || (off > b.length) || (len < 0) ||
            ((off + len) - b.length > 0)) {
            throw new IndexOutOfBoundsException();
        }
        ensureCapacity(count + len);
        System.arraycopy(b, off, buf, count, len);
        count += len;
    }

    public synchronized byte toByteArray()[] {
        return Arrays.copyOf(buf, count);
    }
    
    //reading

    public synchronized int read() {
        return (pos < count) ? (buf[pos++] & 0xff) : -1;
    }

    public synchronized int read(byte b[], int off, int len) {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        }

        if (pos >= count) {
            return -1;
        }

        int avail = count - pos;
        if (len > avail) {
            len = avail;
        }
        if (len <= 0) {
            return 0;
        }
        System.arraycopy(buf, pos, b, off, len);
        pos += len;
        return len;
    }

    public synchronized long skip(long n) {
        long k = count - pos;
        if (n < k) {
            k = n < 0 ? 0 : n;
        }

        pos += k;
        return k;
    }

    public synchronized int available() {
        return count - pos;
    }
    
    //unique implementation - reduces buffer size, ensures average write capacity
    public void flush() {
    	flushes++;
    	int newLength = buf.length;
    	if(enableSmartFlush) {
    		newLength = count - pos + (count / flushes);
    	}
    	if(pos > 0) {
    		buf = Arrays.copyOfRange(buf, pos, newLength);
    		count = available();
    		pos = 0;
    	}
    }
}
