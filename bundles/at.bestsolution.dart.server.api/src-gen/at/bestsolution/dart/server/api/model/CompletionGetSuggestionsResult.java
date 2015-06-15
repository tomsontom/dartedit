package at.bestsolution.dart.server.api.model;

import java.util.Map;

public class CompletionGetSuggestionsResult {
	private CompletionId id;

	public CompletionGetSuggestionsResult() {
	}
	
	public CompletionId getId() {
		return this.id;
	}
	
	public void setId(CompletionId id) {
		this.id = id;
	}
}
