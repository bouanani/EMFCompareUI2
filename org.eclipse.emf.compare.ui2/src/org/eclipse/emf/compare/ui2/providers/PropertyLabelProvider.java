/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.compare.ui2.providers;

import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider used by the table control of this part.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class PropertyLabelProvider extends AdapterFactoryLabelProvider {
	/**
	 * Instantiates this label provider given its {@link AdapterFactory}.
	 * 
	 * @param theAdapterFactory
	 *            Adapter factory providing this {@link org.eclipse.jface.viewers.LabelProvider}'s text and
	 *            images.
	 */
	public PropertyLabelProvider(AdapterFactory theAdapterFactory) {
		super(theAdapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see AdapterFactoryLabelProvider#getColumnImage(Object, int)
	 */
	@Override
	public Image getColumnImage(Object object, int columnIndex) {
		Image image = super.getColumnImage(object, columnIndex);
		if (object instanceof List) {
			image = super.getColumnImage(((List<?>)object).get(columnIndex), columnIndex);

		}
		return image;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see AdapterFactoryLabelProvider#getColumnText(Object, int)
	 */
	@Override
	public String getColumnText(Object object, int columnIndex) {
		String text = super.getColumnText(object, columnIndex);
		if (object instanceof List) {
			text = super.getColumnText(((List<?>)object).get(columnIndex), columnIndex);
		}
		return text;
	}
}
