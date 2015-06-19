package at.bestsolution.dart.editor.doc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.fx.code.compensator.editor.Input;
import org.eclipse.fx.code.compensator.editor.TextEditor;
import org.eclipse.fx.code.compensator.editor.URIProvider;
import org.eclipse.fx.core.URI;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

import at.bestsolution.dart.server.api.DartServer;
import at.bestsolution.dart.server.api.model.AddContentOverlay;
import at.bestsolution.dart.server.api.model.ChangeContentOverlay;
import at.bestsolution.dart.server.api.model.RemoveContentOverlay;
import at.bestsolution.dart.server.api.model.SourceEdit;
import at.bestsolution.dart.server.api.services.ServiceAnalysis;

public class DartInput implements Input<String>, URIProvider {
	private Path path;
	private String data;
	private DartServer server;
	private boolean activeContentOverlay;

	@Inject
	public DartInput(DartServer server, @Named(TextEditor.DOCUMENT_URL) String url) {
		this.path = Paths.get(java.net.URI.create(url));
		this.server = server;
	}

	@Override
	public URI getURI() {
		return URI.create(path.toUri().toString());
	}

	@Override
	public String getData() {
		if( data == null ) {
			try(BufferedReader reader = Files.newBufferedReader(path)) {
				StringBuilder b = new StringBuilder();
				String line;
				while( (line = reader.readLine()) != null ) {
					b.append(line+"\n");
				}
				reader.close();
				data = b.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	@Override
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public void persist() {
		try(BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(data);
			writer.close();
			if( activeContentOverlay ) {
				server.getService(ServiceAnalysis.class).updateContent(Collections.singletonMap(path.toAbsolutePath().toString(), new RemoveContentOverlay()));
				activeContentOverlay = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void reset() {
		this.data = null;
		getData();
	}

	@Override
	public void dispose() {
		this.data = null;
		this.path = null;
	}

	public void documentChanged(IDocument doc, int fOffset, int fLength, String fText) {
		if( ! activeContentOverlay ) {
			AddContentOverlay overlay = new AddContentOverlay();
			overlay.setContent(doc.get());
			server.getService(ServiceAnalysis.class).updateContent(Collections.singletonMap(path.toAbsolutePath().toString(), overlay));
			activeContentOverlay = true;
		} else {
			ChangeContentOverlay overlay = new ChangeContentOverlay();
			SourceEdit edit = new SourceEdit();
			edit.setOffset(fOffset);
			edit.setLength(fLength);
			edit.setReplacement(fText);
			overlay.setEdits(new SourceEdit[] { edit });
			server.getService(ServiceAnalysis.class).updateContent(Collections.singletonMap(path.toAbsolutePath().toString(), overlay));
		}
	}
}
