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
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

/**
 * This class represents the Content Provider of the of the selected Item into the workbench.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class SelectedItemContentProvider extends AdapterFactoryContentProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            A factory for creating adapters and associating them with notifiers
	 */
	public SelectedItemContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof Collection) {
			return ((Collection)object).size() > 0;
		}
		return super.hasChildren(object);
	}

	@Override
	public Object[] getElements(Object object) {
		if (object instanceof Collection) {
			return ((Collection)object).toArray();
		}
		return super.getElements(object);
	}

	@Override
	public Object[] getChildren(Object object) {
		if (object instanceof Collection) {
			return ((Collection)object).toArray();
		}
		return super.getChildren(object);
	}

}
