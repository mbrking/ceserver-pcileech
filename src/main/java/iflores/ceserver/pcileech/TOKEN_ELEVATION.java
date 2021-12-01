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
import com.sun.jna.platform.win32.WinDef;

@Structure.FieldOrder("TokenIsElevated")
public class TOKEN_ELEVATION extends Structure {
    public WinDef.DWORD TokenIsElevated = new WinDef.DWORD(0);
}
