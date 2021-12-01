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

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PciLeech {

    private static final JnaPciLeech _jnaPciLeech = Native.load("vmm", JnaPciLeech.class);

    private PciLeech() {
    }

    @NotNull
    public static List<Integer> getPids() {
        long pcPIDs_malloc = Native.malloc(4);
        try {
            Pointer pcPIDs = new Pointer(pcPIDs_malloc);
            pcPIDs.clear(4);
            if (
                    !_jnaPciLeech.VMMDLL_PidList(
                            Pointer.NULL,
                            pcPIDs
                    )
            ) {
                throw new PciLeechException();
            }
            int bufferSize = pcPIDs.getInt(0) * 4;
            long pdwPIDs_malloc = Native.malloc(bufferSize);
            try {
                Pointer pdwPIDs = new Pointer(pdwPIDs_malloc);
                pdwPIDs.clear(bufferSize);
                if (
                        !_jnaPciLeech.VMMDLL_PidList(
                                pdwPIDs,
                                pcPIDs
                        )
                ) {
                    throw new PciLeechException();
                }
                List<Integer> results = new ArrayList<>();
                int numResults = pcPIDs.getInt(0);
                for (int i = 0; i < numResults; i++) {
                    results.add(pdwPIDs.getInt(i * 4L));
                }
                return results;
            } finally {
                Native.free(pdwPIDs_malloc);
            }
        } finally {
            Native.free(pcPIDs_malloc);
        }
    }

    public static List<VadInfo> getVad(int pid, boolean identifyModules) {
        long pcbVadMap_malloc = Native.malloc(4);
        try {
            Pointer pcbVadMap = new Pointer(pcbVadMap_malloc);
            pcbVadMap.clear(4);
            if (
                    !_jnaPciLeech.VMMDLL_Map_GetVadW(
                            pid,
                            null,
                            pcbVadMap,
                            identifyModules
                    )
            ) {
                throw new PciLeechException();
            }
            int bufferSize = pcbVadMap.getInt(0);
            long pVadMap_malloc = Native.malloc(bufferSize);
            try {
                Pointer pVadMap = new Pointer(pVadMap_malloc);
                pVadMap.clear(bufferSize);
                VMMDLL_MAP_VAD map = new VMMDLL_MAP_VAD(pVadMap);
                if (
                        !_jnaPciLeech.VMMDLL_Map_GetVadW(
                                pid,
                                map,
                                pcbVadMap,
                                identifyModules
                        )
                ) {
                    throw new PciLeechException();
                }
                List<VadInfo> vadInfos = new ArrayList<>();
                VMMDLL_MAP_VADENTRY[] entries = (VMMDLL_MAP_VADENTRY[]) map.pMapArray.toArray(map.cMap);
                for (VMMDLL_MAP_VADENTRY entry : entries) {
                    VadInfo vadInfo = new VadInfo(
                            entry.wszText.toString(),
                            entry.vaStart,
                            entry.vaEnd,
                            entry._dword0,
                            entry._dword1
                    );
                    vadInfos.add(vadInfo);
                }
                return vadInfos;
            } finally {
                Native.free(pVadMap_malloc);
            }
        } finally {
            Native.free(pcbVadMap_malloc);
        }
    }

    public static String getProcessExecutableName(int pid) {
        return _jnaPciLeech.VMMDLL_ProcessGetInformationString(
                pid,
                2
        );
    }

    public static byte[] readMemory(int pid, long address, int size, VmmDllFlags... flags) {
        if (address < 0) {
            throw new IllegalArgumentException();
        }
        if (size < 0) {
            return new byte[0];
        }
        if (size > 1024 * 1024 * 1024) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            return new byte[0];
        }
        long flagsLong = 0;
        for (VmmDllFlags flag : flags) {
            flagsLong |= flag.getValue();
        }

        long buffer_malloc = Native.malloc(size);
        try {
            Pointer pb = new Pointer(buffer_malloc);
            long pcbReadOpt_malloc = Native.malloc(4);
            try {
                Pointer pcbReadOpt = new Pointer(pcbReadOpt_malloc);
                int bytesRemaining = size;
                Pointer readPointer = pb;
                while (true) {
                    boolean result = _jnaPciLeech.VMMDLL_MemReadEx(
                            pid,
                            address,
                            readPointer,
                            bytesRemaining,
                            pcbReadOpt,
                            flagsLong
                    );
                    int count = pcbReadOpt.getInt(0);
                    if (!result) {
                        throw new PciLeechException();
                    }
                    if (count <= 0) {
                        break;
                    }
                    address += count;
                    bytesRemaining -= count;
                    if (bytesRemaining <= 0) {
                        if (bytesRemaining < 0) {
                            throw new IllegalStateException();
                        }
                        break;
                    }
                    readPointer = readPointer.share(count);
                }
                int totalRead = size - bytesRemaining;
                byte[] buf = new byte[totalRead];
                pb.read(0L, buf, 0, totalRead);
                return buf;
            } finally {
                Native.free(pcbReadOpt_malloc);
            }
        } finally {
            Native.free(buffer_malloc);
        }
    }

    public static void writeMemory(int pid, long address, byte[] bytes) {
        if (address < 0) {
            throw new IllegalArgumentException();
        }
        long buffer_malloc = Native.malloc(bytes.length);
        try {
            Pointer pb = new Pointer(buffer_malloc);
            pb.write(0L, bytes, 0, bytes.length);
            int bytesRemaining = bytes.length;
            boolean result = _jnaPciLeech.VMMDLL_MemWrite(
                    pid,
                    address,
                    pb,
                    bytesRemaining
            );
            if (!result) {
                throw new PciLeechException();
            }
        } finally {
            Native.free(buffer_malloc);
        }
    }

    public static boolean initialize(String[] args) {
        return _jnaPciLeech.VMMDLL_Initialize(args.length, args);
    }
}
