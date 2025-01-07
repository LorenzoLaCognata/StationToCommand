package responderStructure.responderLinkModule;

import responderStructure.responderModule.Responder;
import linkStructure.responderLinkModule.ResponderLink;

public class ResponderResponderLink extends ResponderLink {

	private float relationship;
	
    public ResponderResponderLink(Responder responder, float relationship) {
		super(responder);
		this.relationship = relationship;
    }

	public float getRelationship() {
		return relationship;
	}

	public void setRelationship(float relationship) {
		this.relationship = relationship;
	}

}