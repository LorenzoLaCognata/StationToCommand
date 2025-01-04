package linkStructure.responderLinkModule;

import responderStructure.responderModule.Responder;

public abstract class ResponderLink {

    private Responder responder;

    public ResponderLink(Responder responder) {
      System.out.println("ResponderLink initializing");
      this.responder = responder;
		  System.out.println("ResponderLink initialized successfully");
    }
	
}