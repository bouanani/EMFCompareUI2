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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Content provider used .
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Maher Bouanani</a>
 */

public class PropertyContentProvider implements IStructuredContentProvider {

	/** EObject which properties are provided by this content provider. */
	private EObject inputEObject;

	/**
	 * Put the property Content Provider.
	 * 
	 * @param eObject
	 *            EObject given in parameter
	 */
	public PropertyContentProvider(Object eObject) {
		this.inputEObject = (EObject)eObject;
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see IStructuredContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object inputElement) {
		// FIXME ! a check for a match should be added later
		Object[] elements = new Object[] {};
		// This will fetch the property source of the input object
		final List<List<Object>> inputElements = new ArrayList<List<Object>>();
		final AdapterFactory factory = AdapterUtils.getAdapterFactory();
		final IItemPropertySource inputPropertySource = (IItemPropertySource)factory.adapt(inputEObject,
				IItemPropertySource.class);
		// Iterates through the property descriptor to display only the "property" features of the input
		// object
		for (final IItemPropertyDescriptor descriptor : inputPropertySource
				.getPropertyDescriptors(inputEObject)) {
			/*
			 * Filtering out "advanced" properties can be done by hiding properties on which
			 * Arrays.binarySearch(descriptor.getFilterFlags(input), "org.eclipse.ui.views.properties.expert")
			 * returns an int > 0.
			 */
			final EStructuralFeature feature = (EStructuralFeature)descriptor.getFeature(inputEObject);
			final List<Object> row = new ArrayList<Object>();
			row.add(feature);
			row.add(descriptor.getPropertyValue(inputEObject));
			inputElements.add(row);
		}
		elements = inputElements.toArray();

		// For Sorting elements
		Arrays.sort(elements, new Comparator<Object>() {
			public int compare(Object first, Object second) {
				final String name1 = ((EStructuralFeature)((List<?>)first).get(0)).getName();
				final String name2 = ((EStructuralFeature)((List<?>)second).get(0)).getName();

				return name1.compareTo(name2);
			}
		});
		return elements;
	}

	/**
	 * Returns the EObject which properties are currently displayed in the properties tab.
	 * 
	 * @return The EObject which properties are currently displayed in the properties tab.
	 */
	public EObject getInputEObject() {
		return inputEObject;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(Viewer, Object, Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Must See how to deal with this
	}
}
