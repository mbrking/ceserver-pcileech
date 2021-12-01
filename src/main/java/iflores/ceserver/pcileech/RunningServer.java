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

import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

public class RunningServer extends Thread {

    private final Process _process;
    private final Set<RunningServerListener> _listeners = new HashSet<>();
    private boolean _closed;

    public RunningServer(Process process) {
        _process = process;
    }

    @Override
    public void run() {
        try {
            Reader in = new InputStreamReader(_process.getInputStream());
            char[] buf = new char[16384];
            while (true) {
                int count = in.read(buf);
                if (count < 0) {
                    break;
                }
                if (count > 0) {
                    fireCharsRead(buf, 0, count);
                }
            }
            System.out.flush();
            fireClose(_process.waitFor());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            fireClose(null);
        }
    }

    public void addListener(RunningServerListener l) {
        _listeners.add(l);
    }

    private void fireCharsRead(char[] chars, @SuppressWarnings("SameParameterValue") int offset, int length) {
        String s = new String(chars, offset, length); // make safe copy
        SwingUtilities.invokeLater(
                () -> {
                    for (RunningServerListener listener : _listeners) {
                        listener.charsRead(s);
                    }
                }
        );
    }

    private void fireClose(Integer exitCode) {
        SwingUtilities.invokeLater(
                () -> {
                    if (_closed) {
                        return;
                    }
                    _closed = true;
                    for (RunningServerListener listener : _listeners) {
                        listener.closed(exitCode);
                    }
                }
        );
    }

    public void shutdownNow() {
        try {
            OutputStream pout = _process.getOutputStream();
            pout.write('\n');
            pout.flush();
        } catch (Throwable t) {
            _process.destroyForcibly();
            t.printStackTrace();
        }
    }

    public void shutdownNowAndWait() throws InterruptedException {
        _process.destroyForcibly();
        _process.waitFor();
    }

}
