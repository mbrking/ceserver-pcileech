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

public class ProcessInfo {

    private final String _name;
    private final int _pid;

    public ProcessInfo(String name, int pid) {
        _name = name;
        _pid = pid;
    }

    public int getPid() {
        return _pid;
    }

    public String getName() {
        return _name;
    }
}
