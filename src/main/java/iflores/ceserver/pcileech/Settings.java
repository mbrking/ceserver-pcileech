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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Settings {

    private static final String ROOT_KEY = "iflores.ceserver.pcileech";

    private String _memprocfsExePath;
    private String _pciLeechArguments;

    private Settings(String memprocfsExePath, String pciLeechArguments) {
        _memprocfsExePath = memprocfsExePath;
        _pciLeechArguments = pciLeechArguments;
    }

    public static Settings load() {
        try {
            byte[] settingsBytes = Preferences.userRoot().getByteArray(ROOT_KEY, null);
            String memprocfsExePath;
            String pcileechArguments;
            if (settingsBytes == null) {
                memprocfsExePath = "";
                pcileechArguments = "-printf -v -device fpga";
            } else {
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(settingsBytes));
                int version = in.readInt();
                if (version > 0) {
                    throw new IllegalArgumentException("I don't know how to handle settings version " + version);
                }
                memprocfsExePath = in.readUTF();
                pcileechArguments = in.readUTF();
            }
            return new Settings(
                    memprocfsExePath,
                    pcileechArguments
            );
        } catch (Throwable t) {
            throw new RuntimeException("Unable to read settings", t);
        }
    }

    public void save() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(baos);
            out.writeInt(0); // version 0
            out.writeUTF(_memprocfsExePath);
            out.writeUTF(_pciLeechArguments);
            Preferences prefRoot = Preferences.userRoot();
            prefRoot.putByteArray(ROOT_KEY, baos.toByteArray());
            try {
                prefRoot.sync();
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }
        } catch (Throwable t) {
            throw new RuntimeException("Unable to write settings", t);
        }
    }

    public String getMemProcFsExePath() {
        return _memprocfsExePath;
    }

    public void setMemProcFsExePath(String memprocfsExePath) {
        _memprocfsExePath = memprocfsExePath;
    }

    public String getPciLeechArguments() {
        return _pciLeechArguments;
    }

    public void setPciLeechArguments(String pciLeechArguments) {
        _pciLeechArguments = pciLeechArguments;
    }

}
