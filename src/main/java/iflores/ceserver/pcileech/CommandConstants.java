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

public interface CommandConstants {

//    byte CMD_GETVERSION = 0;
//    byte CMD_CLOSECONNECTION = 1;
//    byte CMD_TERMINATESERVER = 2;
    byte CMD_OPENPROCESS = 3;
    byte CMD_CREATETOOLHELP32SNAPSHOT = 4;
    byte CMD_PROCESS32FIRST = 5;
    byte CMD_PROCESS32NEXT = 6;
    byte CMD_CLOSEHANDLE = 7;
    byte CMD_VIRTUALQUERYEX = 8;
    byte CMD_READPROCESSMEMORY = 9;
    byte CMD_WRITEPROCESSMEMORY = 10;
//    byte CMD_STARTDEBUG = 11;
//    byte CMD_STOPDEBUG = 12;
//    byte CMD_WAITFORDEBUGEVENT = 13;
//    byte CMD_CONTINUEFROMDEBUGEVENT = 14;
//    byte CMD_SETBREAKPOINT = 15;
//    byte CMD_REMOVEBREAKPOINT = 16;
//    byte CMD_SUSPENDTHREAD = 17;
//    byte CMD_RESUMETHREAD = 18;
//    byte CMD_GETTHREADCONTEXT = 19;
//    byte CMD_SETTHREADCONTEXT = 20;
    byte CMD_GETARCHITECTURE = 21;
    byte CMD_MODULE32FIRST = 22;
    byte CMD_MODULE32NEXT = 23;

    byte CMD_GETSYMBOLLISTFROMFILE = 24;
//    byte CMD_LOADEXTENSION = 25;

//    byte CMD_ALLOC = 26;
//    byte CMD_FREE = 27;
//    byte CMD_CREATETHREAD = 28;
//    byte CMD_LOADMODULE = 29;
//    byte CMD_SPEEDHACK_SETSPEED = 30;

    byte CMD_VIRTUALQUERYEXFULL = 31;
    byte CMD_GETREGIONINFO = 32;

//    byte CMD_AOBSCAN = (byte) 200;

}
