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

import java.util.Iterator;

public class ToolHelp32Snapshot_Modules {

    private static final long MAX_UNSIGNED_INT = 0xffffffffL;
    private final SelectedProcess _selectedProcess;
    private Iterator<MemoryRegion<VadInfo>> _moduleInfoIterator;

    public ToolHelp32Snapshot_Modules(SelectedProcess selectedProcess) {
        _selectedProcess = selectedProcess;
    }

    public boolean hasNextModuleInfo() {
        return _moduleInfoIterator.hasNext();
    }

    public MemoryRegion<VadInfo> nextModuleInfo() {
        return _moduleInfoIterator.next();
    }

    public void restartModuleInfo() {
        _moduleInfoIterator =
                _selectedProcess
                        .getMemoryMap()
                        .stream()
                        .filter(x -> x.getRegionSize() <= MAX_UNSIGNED_INT)
                        .iterator();
    }

}
