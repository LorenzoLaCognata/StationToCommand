package linkStructure.personLinkModule;

import responderStructure.responderModule.Responder;

public abstract class ResponderLink {

    private final Responder responder;

    public ResponderLink(Responder responder) {
        this.responder = responder;
    }

    public Responder getResponder() {
        return responder;
    }

}