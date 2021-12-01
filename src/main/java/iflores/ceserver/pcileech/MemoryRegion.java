/*
 * This file is part of ceserver-pcileech by Isabella Flores
 *
 * Copyright © 2021 Isabella Flores
 *
 * It is licensed to you under the terms of the
 * GNU Affero General Public License, Version 3.0.
 * Please see the file LICENSE for more information.
 */

package iflores.ceserver.pcileech;

import org.jetbrains.annotations.NotNull;

public class MemoryRegion<T> implements Comparable<MemoryRegion<T>> {

    private final T _userObject;
    private final long _regionStart;
    private final long _size;

    public MemoryRegion(T userObject, long regionStart, long size) {
        _userObject = userObject;
        _regionStart = regionStart;
        _size = size;
    }

    public T getUserObject() {
        return _userObject;
    }

    public long getRegionStart() {
        return _regionStart;
    }

    @Override
    public int compareTo(@NotNull MemoryRegion o) {
        return Long.compare(_regionStart, o._regionStart);
    }

    public long getRegionEnd() {
        return _regionStart + _size - 1;
    }

    public long getRegionSize() {
        return _size;
    }

    @Override
    public String toString() {
        return "[start=" + Long.toHexString(_regionStart) + ", size=" + _size + "]";
    }

}
