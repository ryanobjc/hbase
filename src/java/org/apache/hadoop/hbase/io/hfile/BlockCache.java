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
   * @param buf The block contents wrapped in a ByteBuffer.
   * @param inMemory Whether block should be treated as in-memory
   */
  public void cacheBlock(String blockName, ByteBuffer buf, boolean inMemory);
  
  /**
   * Add block to cache (defaults to not in-memory).
   * @param blockName Zero-based file block number.
   * @param buf The block contents wrapped in a ByteBuffer.
   */
  public void cacheBlock(String blockName, ByteBuffer buf);
  
  /**
   * Fetch block from cache.
   * @param blockName Block number to fetch.
   * @return Block or null if block is not in the cache.
   */
  public ByteBuffer getBlock(String blockName,
                             Scanner scanner);

  /**
   * Return a block when you are done with it.
   *
   * @param blockName the block you are finished with
   * @param scanner the scanner for whence you are finished
   */
  public void returnBlock(String blockName,
                          Scanner scanner);

  /**
   * Allocate a block buffer, this is an allocation hook for those
   * caches that do a more sophisticated allocation strategy that may not
   * use 'new'.
   * @param size block size
   * @return block
   */
  public ByteBuffer allocate(int size);

  /**
   * Shutdown the cache.
   */
  public void shutdown();
}