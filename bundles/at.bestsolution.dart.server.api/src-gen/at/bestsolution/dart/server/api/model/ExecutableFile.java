package at.bestsolution.dart.server.api.model;

import java.util.Map;

public class ExecutableFile {
	private java.lang.String file;
	private ExecutableKind kind;
	
	public ExecutableFile() {
	}
	
	public java.lang.String getFile() {
		return this.file;
	}

	public void setFile(java.lang.String file) {
		this.file = file;
	}
	public ExecutableKind getKind() {
		return this.kind;
	}

	public void setKind(ExecutableKind kind) {
		this.kind = kind;
	}
}
