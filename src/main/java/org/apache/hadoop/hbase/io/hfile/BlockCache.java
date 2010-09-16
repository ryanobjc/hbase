/**
 * Copyright 2009 The Apache Software Foundation
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hbase.io.hfile;

import java.nio.ByteBuffer;

/**
 * Block cache interface.
 * TODO: Add filename or hash of filename to block cache key.
 */
public interface BlockCache {
  /**
   * The block cache needs references to the block
   * cache so it can do some buffer movements.
   *
   * The implementer should ensure this change is atomic
   * (perhaps using a ReentrantReadWriteLock).
   */
  public interface Scanner {
    public void provideNewBlock(ByteBuffer newBlock);
  }

  /**
   * Add block to cache.
   * @param blockName Zero-based file block number.
   * @param ref the reference from allocate(int)
   * @param block the block (that should be a subview of the prev block)
   * @param inMemory Whether block should be treated as in-memory
   */
  public BufferAndRef cacheBlock(Object ref,
                                 ByteBuffer block,
                                 String blockName,
                                 boolean inMemory);

  /**
   * Fetch block from cache.
   *
   * The block has been ref()ed for you. You will need to
   * deref() it later using the ref returned.
   *
   * @param blockName Block number to fetch.
   * @return Block or null if block is not in the cache.
   */
  public BufferAndRef getBlock(String blockName);

  /**
   * Mark this block reference as 'no longer used'. You must not retain pointers
   * to this block/buffer!!!
   *
   * @param ref the buffer and reference
   * @return
   */
  public int release(BufferAndRef ref);

  /**
   * Allocate a block buffer, this is an allocation hook for those
   * caches that do a more sophisticated allocation strategy that may not
   * use 'new'.
   *
   * @param size block size
   * @return buffer for block and reference handle
   */
  public BufferAndRef allocate(int size);

  /**
   * Abort an allocation.
   * @param block the previous allocated block.
   */
  public void abortAllocation(BufferAndRef block);

  /**
   * A type to encapsulate a ByteBuffer and the Cache reference (for deallocation, see above)
   * pointer.  Caches should ignore releases that have a null ref.
   */
  public static class BufferAndRef  {
    public final ByteBuffer buffer;
    public final Object ref;
    public BufferAndRef(ByteBuffer buf, Object ref) {
      this.buffer = buf;
      this.ref = ref;
    }
  }
  
  /**
   * Shutdown the cache.
   */
  public void shutdown();
}