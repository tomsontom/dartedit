package at.bestsolution.dart.editor.complete;

import org.eclipse.fx.code.compensator.editor.Input;
import org.eclipse.fx.code.compensator.editor.ProposalComputer;
import org.eclipse.fx.code.compensator.editor.services.ProposalComputerFactory;
import org.osgi.service.component.annotations.Component;

import at.bestsolution.dart.editor.doc.DartInput;

@Component
public class DartProposalComputerFactory implements ProposalComputerFactory {

	@Override
	public boolean applies(Input<?> input) {
		return input instanceof DartInput;
	}

	@Override
	public Class<? extends ProposalComputer> createProposalComputer() {
		return DartProposalComputer.class;
	}

}
