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

public class PciLeechException extends RuntimeException {

    public PciLeechException() {
    }

    public PciLeechException(String message) {
        super(message);
    }

    public PciLeechException(String message, Throwable cause) {
        super(message, cause);
    }

}
