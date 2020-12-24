package paranoia.core;

import paranoia.core.cpu.ParanoiaAttribute;

import java.awt.image.BufferedImage;

public class Player {

    private final String name;
    private final BufferedImage image;
    private ParanoiaAttribute lastPicked = new ParanoiaAttribute("");
    private Clone clone;

    public Player(String name) {
        this(name, null);
    }

    public Player(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

//    @Override
//    public JPanel getVisual() {
//        removeAll();
//        ParanoiaImage g = new ParanoiaImage(image, true);
//        g.setPreferredSize(new Dimension(75, 75));
//
//        setLayout(new GridBagLayout());
//
//        add(g, LayoutManager.createGrid().at(0, 0, 1, 3).get());
//        add(new JLabel(name), LayoutManager.createGrid().at(1, 0).get());
//        add(new JLabel("Last picked:"), LayoutManager.createGrid().at(1, 1).get());
//        add(new JLabel(lastPicked.getName() + ": " + lastPicked.getValue()), LayoutManager.createGrid().at(1, 2).get());
//        return this;
//    }

}
