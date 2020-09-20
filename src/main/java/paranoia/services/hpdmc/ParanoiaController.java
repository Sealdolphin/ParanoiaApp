package paranoia.services.hpdmc;

import daiv.networking.command.ParanoiaCommand;

public interface ParanoiaController {

    boolean sendCommand(ParanoiaCommand command);

}
