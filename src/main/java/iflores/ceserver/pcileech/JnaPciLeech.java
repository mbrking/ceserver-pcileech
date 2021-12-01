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

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.WString;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public interface JnaPciLeech extends Library {

    boolean VMMDLL_Initialize(int argc, String[] argv);

    boolean VMMDLL_MemReadEx(
            int dwPID,
            long qwVA,
            Pointer pb,
            int cb,
            Pointer pcbReadOpt,
            long flags
    );

    boolean VMMDLL_MemWrite(
            int dwPID,
            long qwVA,
            Pointer pb,
            int cb
    );

    boolean VMMDLL_PidList(
            Pointer pPIDs,
            Pointer pcPIDs
    );

    boolean VMMDLL_Map_GetVadW(
            int dwPID,
            VMMDLL_MAP_VAD pVadMap,
            Pointer pcbVadMap,
            boolean fIdentifyModules
    );

    String VMMDLL_ProcessGetInformationString(
            int dwPID,
            int fOptionString
    );

    long VMMDLL_ProcessGetModuleBaseW(
            int dwPID,
            WString wszModuleName
    );

}