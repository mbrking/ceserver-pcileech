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

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;

@Structure.FieldOrder({
        "dwVersion",
        "_Reserved1_0",
        "_Reserved1_1",
        "_Reserved1_2",
        "_Reserved1_3",
        "cPage",
        "wszMultiText",
        "cbMultiText",
        "cMap",
        "pMapArray"
})
public class VMMDLL_MAP_VAD extends Structure {
    public int dwVersion;
    public int _Reserved1_0;
    public int _Reserved1_1;
    public int _Reserved1_2;
    public int _Reserved1_3;
    public int cPage;
    public WString wszMultiText;
    public int cbMultiText;
    public int cMap;
    public VMMDLL_MAP_VADENTRY pMapArray;

    public VMMDLL_MAP_VAD(Pointer p) {
        super(p);
    }

}