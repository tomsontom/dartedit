package at.bestsolution.dart.server.api.model;

import java.util.Map;

public class ChangeContentOverlay {
	private SourceEdit[] edits;
	
	public ChangeContentOverlay() {
	}
	
	public SourceEdit[] getEdits() {
		return this.edits;
	}

	public void setEdits(SourceEdit[] edits) {
		this.edits = edits;
	}
}
