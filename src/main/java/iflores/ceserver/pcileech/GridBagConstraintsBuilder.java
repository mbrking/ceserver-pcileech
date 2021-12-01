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

import java.awt.*;

public class GridBagConstraintsBuilder extends GridBagConstraints {

    public GridBagConstraintsBuilder gridx(int gridx) {
        this.gridx = gridx;
        return this;
    }

    public GridBagConstraintsBuilder anchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public GridBagConstraintsBuilder insets(Insets insets) {
        this.insets = insets;
        return this;
    }

    public GridBagConstraintsBuilder weightx(double weightx) {
        this.weightx = weightx;
        return this;
    }

    public GridBagConstraintsBuilder fill(int fill) {
        this.fill = fill;
        return this;
    }

}
