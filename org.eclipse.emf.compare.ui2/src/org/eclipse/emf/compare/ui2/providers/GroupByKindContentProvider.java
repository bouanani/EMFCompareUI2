package org.eclipse.emf.compare.ui2.providers;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.ui2.actions.menu.grouping.DiffKindGroups;
import org.eclipse.emf.compare.ui2.actions.menu.grouping.DiffKindGroups.DifferenceGroupElement;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

public class GroupByKindContentProvider extends AdapterFactoryContentProvider {

	/**
	 * @param adapterFactory
	 */
	public GroupByKindContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object[] getElements(Object object) {
		if (object instanceof DiffKindGroups) {
			return ((DiffKindGroups)object).getElements().toArray();
		} else if (object instanceof DifferenceGroupElement) {
			return ((DifferenceGroupElement)object).getElements().toArray();
		}
		return super.getElements(object);
	}

	@Override
	public Object[] getChildren(Object object) {
		if (object instanceof DifferenceGroupElement) {
			return ((DifferenceGroupElement)object).getElements().toArray();
		}
		return super.getChildren(object);
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof DiffKindGroups) {
			return ((DiffKindGroups)object).getElements().size() > 0;
		} else if (object instanceof DifferenceGroupElement) {
			return ((DifferenceGroupElement)object).getElements().size() > 0;
		}
		return super.hasChildren(object);
	}
}
