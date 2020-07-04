package paranoia.core;

import org.json.JSONObject;
import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.services.plc.LayoutManager;
import paranoia.visuals.custom.ParanoiaImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

public class Player extends JPanel implements ParanoiaPlayer {

    private final String name;
    private final BufferedImage image;
    private ParanoiaAttribute lastPicked = new ParanoiaAttribute("");

    public Player(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    @Override
    public String getFullName() {
        return name;
    }

    @Override
    public JPanel getVisual() {
        ParanoiaImage g = new ParanoiaImage(image, true);
        g.setPreferredSize(new Dimension(75, 75));

        setLayout(new GridBagLayout());

        add(g, LayoutManager.createGrid().at(0, 0, 1, 3).get());
        add(new JLabel(name), LayoutManager.createGrid().at(1, 0).get());
        add(new JLabel("Last picked:"), LayoutManager.createGrid().at(1, 1).get());
        add(new JLabel(lastPicked.getName() + ": " + lastPicked.getValue()), LayoutManager.createGrid().at(1, 2).get());
        return this;
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("last", lastPicked.getName());
        json.put("value", lastPicked.getValue());
        return json;
    }
}
