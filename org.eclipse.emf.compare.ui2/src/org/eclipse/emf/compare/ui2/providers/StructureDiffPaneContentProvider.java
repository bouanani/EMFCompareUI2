/*******************************************************************************
 * Copyright (c) 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.emf.compare.ui2.providers;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.ui2.actions.menu.grouping.DifferenceGroup;
import org.eclipse.emf.compare.ui2.actions.menu.grouping.DifferenceGroupProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

/**
 * This class represents the Content Provider of the of the Strucut
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class StructureDiffPaneContentProvider extends AdapterFactoryContentProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            A factory for creating adapters and associating them with notifiers
	 */

	public StructureDiffPaneContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public Object[] getElements(Object object) {
		if (object instanceof DifferenceGroupProvider) {
			return ((DifferenceGroupProvider)object).getElements().toArray();
		} else if (object instanceof Collection<?>) {
			return ((Collection<?>)object).toArray();
		}
		return super.getElements(object);
	}

	@Override
	public Object[] getChildren(Object object) {
		if (object instanceof DifferenceGroup) {
			return ((DifferenceGroup)object).getDifferences().toArray();
		} else if (object instanceof Collection<?>) {
			return ((Collection<?>)object).toArray();
		}
		return super.getChildren(object);
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof DifferenceGroupProvider) {
			return !((DifferenceGroupProvider)object).getElements().isEmpty();
		} else if (object instanceof DifferenceGroup) {
			return !((DifferenceGroup)object).getDifferences().isEmpty();
		} else if (object instanceof Collection<?>) {
			return !((Collection<?>)object).isEmpty();
		}
		return super.hasChildren(object);
	}

}
