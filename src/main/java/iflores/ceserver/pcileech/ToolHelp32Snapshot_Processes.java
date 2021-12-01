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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToolHelp32Snapshot_Processes {

    private final List<ProcessInfo> _results = new ArrayList<>();
    private Iterator<ProcessInfo> _processInfoIterator;

    public ToolHelp32Snapshot_Processes() {
        List<Integer> pids = PciLeech.getPids();
        for (Integer pid : pids) {
            _results.add(new ProcessInfo(PciLeech.getProcessExecutableName(pid), pid));
        }
    }

    public void restartProcessInfo() {
        _processInfoIterator = _results.iterator();
    }

    public boolean hasNextProcessInfo() {
        return _processInfoIterator.hasNext();
    }

    public ProcessInfo nextProcessInfo() {
        return _processInfoIterator.next();
    }
}
