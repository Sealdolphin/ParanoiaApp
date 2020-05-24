package paranoia.services.plc;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public abstract class LayoutManager {

    public static Grid createGrid() {
        return new Grid();
    }

    public static Grid createGrid(int top, int left, int bottom, int right) {
        return new Grid(new Insets(top, left, bottom, right));
    }

    public static class Grid {

        private final GridBagConstraints constraints;

        private Grid() {
            this(new Insets(2, 2, 2, 2));
        }

        private Grid(Insets insets) {
            constraints = new GridBagConstraints();
            constraints.insets = insets;
        }

        public GridBagConstraints get() {
            return constraints;
        }

        public Grid at(
            int x, int y
        ) {
            return at(x, y, 1, 1);
        }

        public Grid at(
            int xPos,
            int yPos,
            int width,
            int height
        ) {
            constraints.gridx = xPos;
            constraints.gridy = yPos;
            constraints.gridwidth = width;
            constraints.gridheight = height;
            constraints.anchor = GridBagConstraints.FIRST_LINE_START;
            return this;
        }

        public Grid anchor(
            int anchor
        ) {
            constraints.anchor = anchor;
            return this;
        }

        public Grid fill(
            int fill
        ) {
            constraints.fill = fill;
            return this;
        }
    }

}
