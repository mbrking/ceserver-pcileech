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

import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;

import javax.swing.*;
import java.text.SimpleDateFormat;

import static com.sun.jna.platform.win32.Shell32.SEE_MASK_NOCLOSEPROCESS;
import static com.sun.jna.platform.win32.WinUser.SW_SHOWDEFAULT;

public class Main {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) {
        maybeRerunAsAdministrator(args);
        Settings settings = Settings.load();
        SwingUtilities.invokeLater(
                () -> {
                    MainFrame f = new MainFrame(settings);
                    f.setVisible(true);
                }
        );
    }

    private static void maybeRerunAsAdministrator(String[] args) {
        WinNT.HANDLEByReference phToken = new WinNT.HANDLEByReference();
        WinNT.HANDLE processHandle = Kernel32.INSTANCE.GetCurrentProcess();
        if (!Advapi32.INSTANCE.OpenProcessToken(processHandle, WinNT.TOKEN_QUERY, phToken)) {
            Win32Utils.throwLastWin32Exception();
        }
        TOKEN_ELEVATION tokenInformation = new TOKEN_ELEVATION();
        IntByReference cbNeeded = new IntByReference(0);

        if (!Advapi32.INSTANCE.GetTokenInformation(
                phToken.getValue(),
                WinNT.TOKEN_INFORMATION_CLASS.TokenElevation,
                tokenInformation,
                tokenInformation.size(),
                cbNeeded
        )) {
            Win32Utils.throwLastWin32Exception();
        }

        if (tokenInformation.TokenIsElevated.intValue() == 0) {
            ShellAPI.SHELLEXECUTEINFO execInfo = new ShellAPI.SHELLEXECUTEINFO();
            execInfo.lpFile = System.getProperty("java.home") + "/bin/javaw.exe";
            StringBuilder params = new StringBuilder();
            params.append("-classpath ");
            params.append(System.getProperty("java.class.path"));
            params.append(' ');
            params.append(Main.class.getName());
            for (String arg : args) {
                params.append(' ');
                params.append(arg);
            }
            execInfo.lpParameters = params.toString();
            execInfo.nShow = SW_SHOWDEFAULT;
            execInfo.fMask = SEE_MASK_NOCLOSEPROCESS;
            execInfo.lpVerb = "runas";
            boolean result = Shell32.INSTANCE.ShellExecuteEx(execInfo);
            if (!result) {
                Win32Utils.throwLastWin32Exception();
            }
            System.exit(0);
        }
    }

    public static void log(ClientHandler clientHandler, String message) {
        synchronized (System.out) {
            System.out.println(
                    "["
                    + SIMPLE_DATE_FORMAT.format(System.currentTimeMillis())
                    + " "
                    + (clientHandler == null ? "---SYSTEM---" : clientHandler)
                    + "] "
                    + message
            );
        }
    }

}
