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

interface Win32Constants {

    int TH32CS_SNAPPROCESS = 0x02;
    int TH32CS_SNAPTHREAD = 0x04;
    int TH32CS_SNAPMODULE = 0x08;
    int TYPE_MEM_MAPPED = 0x40000;
    int TYPE_MEM_ = 0x20000;
    int TYPE_MEM_COMMIT = 0x1000;
    int TYPE_MEM_FREE = 0x10000;

    int PAGE_READONLY = 2;
    int PAGE_READWRITE = 4;
    int PAGE_WRITECOPY = 8;
    int PAGE_EXECUTE = 16;
    int PAGE_EXECUTE_READ = 32;
    int PAGE_EXECUTE_READWRITE = 64;
    int PAGE_EXECUTE_WRITECOPY = 128;
    int PAGE_GUARD = 256;
    int PAGE_NOACCESS = 1;
    int PAGE_NOCACHE = 512;

}
