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

import com.sun.jna.Structure;
import com.sun.jna.WString;

import java.util.Arrays;
import java.util.List;

@Structure.FieldOrder({
        "vaStart",
        "vaEnd",
        "vaVad",
        "_dword0",
        "_dword1",
        "u2",
        "cbPrototypePte",
        "vaPrototypePte",
        "vaSubsection",
        "wszText",
        "cwszText",
        "_Reserved1",
        "vaFileObject",
        "cVadExPages",
        "cVadExPagesBase",
        "_Reserved2"
})
public class VMMDLL_MAP_VADENTRY extends Structure {
    public long vaStart;
    public long vaEnd;
    public long vaVad;
    public int _dword0; // see header file for bit breakdown
    public int _dword1; // see header file for bit breakdown
    public int u2;
    public int cbPrototypePte;
    public long vaPrototypePte;
    public long vaSubsection;
    public WString wszText;
    public int cwszText;
    public int _Reserved1;
    public long vaFileObject;
    public int cVadExPages;
    public int cVadExPagesBase;
    public long _Reserved2;

}

