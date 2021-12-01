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

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class DocumentChangeListener implements DocumentListener {

    private final Runnable _runnable;

    public DocumentChangeListener(Runnable runnable) {
        _runnable = runnable;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChanged();
    }

    private void documentChanged() {
        _runnable.run();
    }
}
