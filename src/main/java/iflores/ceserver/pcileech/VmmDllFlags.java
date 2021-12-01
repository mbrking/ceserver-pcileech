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

public enum VmmDllFlags {

    // do not use the data cache (force reading from memory acquisition device)
    NOCACHE(0x0001),
    // zero pad failed physical memory reads and report success if read within range of physical memory.
    ZEROPAD_ON_FAIL(0x0002),
    // force use of cache - fail non-cached pages - only valid for reads, invalid with NOCACHE/ZEROPAD_ON_FAIL.
    FORCECACHE_READ(0x0008),
    // do not try to retrieve memory from paged out memory from pagefile/compressed (even if possible)
    NOPAGING(0x0010),
    // do not try to retrieve memory from paged out memory if read would incur additional I/O (even if possible).
    NOPAGING_IO(0x0020),
    // do not write back to the data cache upon successful read from memory acquisition device.
    NOCACHEPUT(0x0100),
    // only fetch from the most recent active cache region when reading.
    CACHE_RECENT_ONLY(0x0200),
    // do not perform additional predictive page reads (default on smaller requests).
    NO_PREDICTIVE_READ(0x0400);

    private final long _value;

    VmmDllFlags(long value) {
        _value = value;
    }

    public long getValue() {
        return _value;
    }
}
