package paranoia.services.hpdmc;

import paranoia.services.technical.command.ParanoiaCommand;

public interface ParanoiaController {

    boolean sendCommand(ParanoiaCommand command);

}
