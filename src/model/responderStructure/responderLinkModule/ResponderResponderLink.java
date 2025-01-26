package model.responderStructure.responderLinkModule;

import model.linkStructure.personLinkModule.ResponderLink;
import model.responderStructure.responderModule.Responder;

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