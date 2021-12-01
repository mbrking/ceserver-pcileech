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

import java.io.Serializable;

public class VadInfo implements Serializable {

    private final String _name;
    private final long _start;
    private final long _end;
    private final int _type;
    private final int _protection;
    private final int _fImage;
    private final int _fFile;
    private final int _fPageFile;
    private final int _fPrivateMemory;
    private final int _fTeb;
    private final int _fStack;
    private final int _fSpare;
    private final int _HeapNum;
    private final int _fHeap;
    private final int _cwszDescription;
    private final int _commitCharge;
    private final int _memCommit;

    public VadInfo(String name, long start, long end, int dword0, int dword1) {
        _name = name;
        _start = start;
        _end = end;

        _type = getValue(dword0, 0, 3);
        _protection = getValue(dword0, 3, 5);
        _fImage = getValue(dword0, 8, 1);
        _fFile = getValue(dword0, 9, 1);
        _fPageFile = getValue(dword0, 10, 1);
        _fPrivateMemory = getValue(dword0, 11, 1);
        _fTeb = getValue(dword0, 12, 1);
        _fStack = getValue(dword0, 13, 1);
        _fSpare = getValue(dword0, 14, 2);
        _HeapNum = getValue(dword0, 16, 7);
        _fHeap = getValue(dword0, 23, 1);
        _cwszDescription = getValue(dword0, 24, 8);
        _commitCharge = getValue(dword1, 0, 31);
        _memCommit = getValue(dword1, 31, 1);
    }

    public String getName() {
        return _name;
    }

    public long getStart() {
        return _start;
    }

    public long getEnd() {
        return _end;
    }

    @Override
    public String toString() {
        return _name + " (" + Long.toHexString(_start) + "-" + Long.toHexString(_end) + ")";
    }

    private int getValue(int mask, int start, int length) {
        return (mask << (32 - start - length)) >>> (32 - length);
    }


    public int getProtection() {
        return _protection;
    }

    public int getfImage() {
        return _fImage;
    }

    public int getfFile() {
        return _fFile;
    }

    public int getfPageFile() {
        return _fPageFile;
    }

    public int getfPrivateMemory() {
        return _fPrivateMemory;
    }

    public int getfTeb() {
        return _fTeb;
    }

    public int getfStack() {
        return _fStack;
    }

    public int getfSpare() {
        return _fSpare;
    }

    public int getHeapNum() {
        return _HeapNum;
    }

    public int getfHeap() {
        return _fHeap;
    }

    public int getCwszDescription() {
        return _cwszDescription;
    }

    public int getCommitCharge() {
        return _commitCharge;
    }

    public int getMemCommit() {
        return _memCommit;
    }

    public int getType() {
        return _type;
    }
}
