package paranoia.helper;

import org.assertj.swing.core.GenericTypeMatcher;
import paranoia.visuals.clones.SelfPanel;

public class SelfPanerMatcher extends GenericTypeMatcher<SelfPanel> {

    public SelfPanerMatcher(Class<SelfPanel> supportedType) {
        super(supportedType, true);
    }

    @Override
    protected boolean isMatching(SelfPanel component) {
        return false;
    }
}
