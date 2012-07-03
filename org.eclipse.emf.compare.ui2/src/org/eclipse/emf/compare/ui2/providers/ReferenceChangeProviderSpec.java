package org.eclipse.emf.compare.ui2.providers;

import com.google.common.collect.Lists;

import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.provider.ReferenceChangeItemProvider;
import org.eclipse.emf.compare.provider.spec.ReferenceChangeItemProviderSpec;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.wb.swt.ResourceManager;

/**
 * Specialized {@link ReferenceChangeItemProvider} returning nice output for {@link #getText(Object)} and
 * {@link #getImage(Object)} depending on the Kind of Differences.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">BOUANANI Maher</a>
 */
public class ReferenceChangeProviderSpec extends ReferenceChangeItemProviderSpec {

	/**
	 * A boolean attribute to check wether the Difference is remote or local ; true if local ; false if
	 * remote.
	 */
	private boolean fIslocal;

	/**
	 * {@inheritDoc}
	 * 
	 * @param adapterFactory
	 */
	public ReferenceChangeProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.provider.ReferenceChangeItemProvider#getImage(java.lang.Object)
	 */
	@Override
	public Object getImage(Object object) {
		ReferenceChange refChange = (ReferenceChange)object;
		Object image = CompareAdapterFactoryProviderSpec.getImage(getRootAdapterFactory(), refChange
				.getValue());
		Object overlay = getOverlayImageByKind(refChange);
		List<Object> images = Lists.newArrayList(image);
		images.add(overlay);
		ComposedImage composedImage = new ComposedImage(images);
		return composedImage;
	}

	public Object getOverlayImageByKind(ReferenceChange object) {
		Object ret = null;
		switch (object.getKind()) {
			case ADD:
				ret = ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
						"icons/full/ovr16/add_ov.gif").createImage();
				break;
			case DELETE:
				ret = ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
						"icons/full/ovr16/del_ov.gif").createImage();
				break;
			case MOVE:
				ret = ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
						"icons/full/ovr16/merged_ov.gif").createImage();
				break;
			default:
				ret = ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
						"icons/full/obj16/referenceChange.gif").createImage();
		}
		return ret;
	}

}
