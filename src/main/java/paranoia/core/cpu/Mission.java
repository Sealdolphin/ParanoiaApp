package paranoia.core.cpu;

import paranoia.core.ICoreTechPart;
import paranoia.services.plc.ResourceManager;
import paranoia.visuals.ui.MissionView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.io.IOException;

public class Mission implements ICoreTechPart {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(320, 0));
        try {
            ResourceManager.loadResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Mission mission = new Mission(
            0,
            "Trial mission",
            "Get to da CHOPPA!!",
            MissionPriority.REQUIRED
        );
        frame.add(mission.getVisual());
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mission.fail();
        frame.getContentPane().removeAll();
        frame.add(mission.getVisual());
        frame.pack();
        System.out.println("READY");

    }

    public enum MissionPriority {
        REQUIRED,
        OPTIONAL
    }

    public enum MissionStatus {
        ACCEPTED,
        COMPLETED,
        FAILED
    }

    private Boolean failed;
    private Boolean completed;
    private final MissionPriority priority;

    private final int id;
    private final String title;
    private final String description;

    public Mission(
        int id,
        String title,
        String description
    ) {
        this(id, title, description, MissionPriority.REQUIRED);
    }

    public Mission(
        int id,
        String title,
        String description,
        MissionPriority priority
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        completed = false;
        failed = false;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Integer getId() {
        return id;
    }

    public void complete() {
        completed = true;
    }

    public void fail() {
        failed = true;
    }

    public MissionPriority getPriority() {
        return priority;
    }

    @Override
    public JPanel getVisual() {
        return new MissionView(title, description, id, failed, completed);
    }
}
