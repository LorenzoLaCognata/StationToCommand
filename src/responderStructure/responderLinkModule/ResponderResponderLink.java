package responderStructure.responderLinkModule;

import responderStructure.responderModule.Responder;
import linkStructure.responderLinkModule.ResponderLink;

public class ResponderResponderLink extends ResponderLink {

	private float relationship;
	
    public ResponderResponderLink(Responder responder, float relationship) {
		super(responder);
		this.relationship = relationship;
    }
	
}