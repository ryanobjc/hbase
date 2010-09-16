package org.apache.hadoop.hbase.io.hfile;

import java.nio.ByteBuffer;

/**
 * A block cache that uses a single allocation block, and manages internally.
 * 
 */
public class SingleAllocationBlockCache implements BlockCache {


  final byte [] cacheSpace;
  int point1;
  int point2;
  final int cacheSize;

  

  public SingleAllocationBlockCache(long size) {
    if (size > Integer.MAX_VALUE) {
      throw new UnsupportedOperationException("We don't support caches > 2gb right now");
    }

    this.cacheSize = (int)size;
    cacheSpace = new byte[this.cacheSize];

    point1 = point2 = 0;
  }


  @Override
  public void cacheBlock(String blockName, ByteBuffer buf, boolean inMemory) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void cacheBlock(String blockName, ByteBuffer buf) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public ByteBuffer getBlock(String blockName, Scanner scanner) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void returnBlock(String blockName, Scanner scanner) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  /** interface **/

  @Override
  public ByteBuffer allocate(int size) {
    synchronized (cacheSpace) {
      // is there room?
      if ((this.cacheSize - this.point2) > size) {
        // grab
        int start = point2;
        point2 += size;
        return ByteBuffer.wrap(cacheSpace, start, size);
      } else {
        // OOM/need to defragment or start at beginning of the ring buffer.

        throw new OutOfMemoryError("wah");
      }
    }
  }

  @Override
  public void shutdown() {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
