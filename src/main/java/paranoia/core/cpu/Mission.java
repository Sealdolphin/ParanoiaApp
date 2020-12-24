package paranoia.core.cpu;

import paranoia.core.ICoreTechPart;
import paranoia.visuals.ui.MissionView;

import javax.swing.JPanel;

public class Mission implements ICoreTechPart {

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
