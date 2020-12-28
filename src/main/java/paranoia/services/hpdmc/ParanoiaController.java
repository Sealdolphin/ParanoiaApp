package paranoia.services.hpdmc;

import daiv.networking.command.ParanoiaCommand;
import paranoia.core.ICoreTechPart;

public interface ParanoiaController {

    boolean sendCommand(ParanoiaCommand command);

    <T extends ICoreTechPart> void addListener(Class<T> asset, ParanoiaListener<T> listener);

}
