/*
 * Copyright 2010 The Apache Software Foundation
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
 * A cache that is 'null' and doesnt do anything. This exists to make HFile code
 * simpler.
 */
public class NullCache implements BlockCache {
  @Override
  public void cacheBlock(String blockName, ByteBuffer buf, boolean inMemory) {
    // We don't cache!
  }

  @Override
  public void cacheBlock(String blockName, ByteBuffer buf) {
    // We don't cache!
  }

  @Override
  public ByteBuffer getBlock(String blockName, Scanner scanner) {
    return null; // We never cache anything.
  }

  @Override
  public void returnBlock(String blockName, Scanner scanner) {
    // do nothing, regular GC takes care of this for us.
  }

  @Override
  public ByteBuffer allocate(int size) {
    return ByteBuffer.allocate(size);
  }

  @Override
  public void shutdown() {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
