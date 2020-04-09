package paranoia.services.hpdmc.manager;

import paranoia.core.ICoreTechPart;
import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ParanoiaListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AttributeManager implements ParanoiaManager<ParanoiaAttribute> {

    private final Collection<ParanoiaAttribute> attributes = new ArrayList<>();
    private final List<ParanoiaListener<ParanoiaAttribute>> listeners = new ArrayList<>();

    public AttributeManager(){
        //Set up default skills and stats
        Arrays.stream(Stat.values()).forEach(s -> updateAsset(ParanoiaAttribute.getStat(s, 0)));
        Arrays.stream(Skill.values()).forEach(s -> updateAsset(ParanoiaAttribute.getSkill(s, 0)));
    }

    private void updateListeners() {
        listeners.forEach( listener -> listener.updateVisualDataChange(attributes));
    }

    @Override
    public void addListener(ParanoiaListener<ParanoiaAttribute> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ParanoiaListener<ParanoiaAttribute> listener) {
        listeners.remove(listener);
    }

    public Integer getAttribute(String name) {
        ParanoiaAttribute attribute = attributes.stream()
            .filter(a -> a.getName().equals(name))
            .findAny().orElse(null);
        if(attribute != null) {
            return attribute.getValue();
        } else return -1;
    }

    @Override
    public void updateAsset(ICoreTechPart asset) {
        ParanoiaAttribute attribute = (ParanoiaAttribute) asset;
        ParanoiaAttribute old = attributes.stream().filter(attr -> attr.getName().equals(attribute.getName()))
            .findAny().orElse(null);
        if (old != null){
            old.setValue(attribute.getValue());
        } else {
            attributes.add(attribute);
        }
        updateListeners();
    }

    @Override
    public void removeAsset(ICoreTechPart asset) {
        ParanoiaAttribute removable = (ParanoiaAttribute) asset;
        attributes.stream().filter(attr -> attr.getName().equals(removable.getName()))
            .findAny().ifPresent(old -> attributes.remove(removable));
        updateListeners();
    }
}
