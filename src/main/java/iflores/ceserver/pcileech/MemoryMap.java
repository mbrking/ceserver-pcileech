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

import java.util.Iterator;
import java.util.TreeSet;
import java.util.stream.Stream;

public class MemoryMap<T> implements Iterable<MemoryRegion<T>> {

    private final TreeSet<MemoryRegion<T>> _treeSet = new TreeSet<>();

    public void add(MemoryRegion<T> newEntry) {
        MemoryRegion<T> previousEntry = _treeSet.floor(newEntry);
        MemoryRegion<T> nextEntry = _treeSet.ceiling(newEntry);
        if (regionOverlaps(newEntry, previousEntry)) {
            throw new RuntimeException("Overlapping entries: " + newEntry + "/" + previousEntry);
        }
        if (regionOverlaps(newEntry, nextEntry)) {
            throw new RuntimeException("Overlapping entries: " + nextEntry + "/" + newEntry);
        }
        if (!_treeSet.add(newEntry)) {
            throw new RuntimeException("Duplicate memory range: " + newEntry);
        }
    }

    private boolean regionOverlaps(MemoryRegion<T> entry1, MemoryRegion<T> entry2) {
        return entry1 != null
               && entry2 != null
               && entry1.getRegionStart() <= entry2.getRegionEnd()
               && entry2.getRegionStart() <= entry1.getRegionEnd();
    }

    public MemoryRegion<T> getMemoryRegionContaining(long address) {
        MemoryRegion<T> floorEntry = floor(address);
        if (floorEntry != null) {
            if (floorEntry.getRegionEnd() >= address) {
                return floorEntry;
            }
            MemoryRegion<T> ceilEntry = ceil(address);
            if (ceilEntry != null) {
                return new MemoryRegion<>(null, floorEntry.getRegionEnd() + 1, ceilEntry.getRegionStart() - floorEntry.getRegionEnd() - 1);
            }
            return null; // past end
        }
        MemoryRegion<T> ceilEntry = ceil(address);
        if (ceilEntry != null) {
            return new MemoryRegion<>(null, 0L, ceilEntry.getRegionStart());
        }
        return null;
    }

    private MemoryRegion<T> floor(long address) {
        return _treeSet.stream().filter(x -> x.getRegionStart() <= address).reduce((first, second) -> second).orElse(null);
    }

    private MemoryRegion<T> ceil(long address) {
        return _treeSet.stream().filter(x -> x.getRegionStart() >= address).findFirst().orElse(null);
    }

    @NotNull
    @Override
    public Iterator<MemoryRegion<T>> iterator() {
        return _treeSet.iterator();
    }

    @NotNull
    public Stream<MemoryRegion<T>> stream() {
        return _treeSet.stream();
    }

    public int getRegionCount() {
        return _treeSet.size();
    }

}
