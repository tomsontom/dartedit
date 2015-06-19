package at.bestsolution.dart.editor.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fx.code.compensator.editor.Input;
import org.eclipse.fx.code.compensator.editor.services.PartitionerFactory;
import org.eclipse.fx.code.compensator.editor.services.SourceViewerConfigurationFactory;
import org.eclipse.fx.code.compensator.hsl.hSL.Model;
import org.eclipse.fx.code.editor.hsl.HSLConfiguration;
import org.eclipse.fx.code.editor.hsl.HSLEditorComponent;
import org.eclipse.fx.code.editor.hsl.HSLModelProvider;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import at.bestsolution.dart.editor.doc.DartInput;

@Component(service={PartitionerFactory.class,SourceViewerConfigurationFactory.class,DartSourceConfigAndPartitionerFactory.class})
public class DartSourceConfigAndPartitionerFactory extends HSLEditorComponent {
	
	private List<HSLModelProvider> providerList = new ArrayList<>();
	
	@Reference(cardinality=ReferenceCardinality.MULTIPLE,policy=ReferencePolicy.DYNAMIC,policyOption=ReferencePolicyOption.GREEDY)
	public void registerProvider(HSLModelProvider provider) {
		synchronized (providerList) {
			providerList.add(provider);
		}
	}
	
	public void unregisterProvider(HSLModelProvider provider) {
		synchronized (providerList) {
			providerList.remove(provider);
		}
	}
	
	// SourceViewerConfigurationFactory

	@Override
	public Class<? extends SourceViewerConfiguration> createConfiguration(Input<?> input) {
		return HSLConfiguration.class;
	}

	// PartitionerFactory & SourceViewerConfigurationFactory
	
	@Override
	public boolean applies(Input<?> input) {
		return input instanceof DartInput;
	}

	public Model getModel(Input<?> input) {
		List<HSLModelProvider> list;
		
		synchronized (providerList) {
			list = new ArrayList<HSLModelProvider>(providerList);
		}
		
		return list.stream()
						.filter( p -> p.applies(input))
						.findFirst()
						.map( p -> p.getModel(input)).orElse(null);
	}

}
