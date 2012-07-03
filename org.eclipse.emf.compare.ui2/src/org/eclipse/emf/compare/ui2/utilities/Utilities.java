/*******************************************************************************
 * Copyright (c) 2000, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Obeo - Tweaking for EMF Compare
 *******************************************************************************/
package org.eclipse.emf.compare.ui2.utilities;

import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.compare.ui2.input.AbstractEMFCompareInput;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.EditorPart;

/**
 * This class provides various utility methods that can be used to call EMF Compare on various notifiers. This
 * is a part of Utilities of org.eclipse.compare.internal.Utilities .
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public final class Utilities {

	/**
	 * Current Editor Input.
	 */
	private static AbstractEMFCompareInput input;

	/**
	 * Editor view that contain the principal views.
	 */
	private static Composite structuralView;

	/**
	 * EMF Compare Editor Part.
	 */
	private static EditorPart emfCEditorPart;

	/**
	 * Private constructor.
	 */
	private Utilities() {
		// Nothing to do
	}

	/**
	 * Convenience method: extract all accessible <code>IResources</code> from given selection. Never returns
	 * null.
	 * 
	 * @param selection
	 *            the given selection
	 * @return IResource[] all accessible resources
	 */
	public static IResource[] getResources(ISelection selection) {
		ArrayList tmp = internalGetResources(selection, IResource.class);
		return (IResource[])tmp.toArray(new IResource[tmp.size()]);
	}

	/**
	 * Return a list of an internal resources from a given selection and a type.
	 * 
	 * @param selection
	 * @param type
	 * @return list of resources
	 */

	// FIXME ! Need to be fixed Complexity probles
	private static ArrayList internalGetResources(ISelection selection, Class type) {
		ArrayList tmp = new ArrayList();
		if (selection instanceof IStructuredSelection) {
			Object[] s = ((IStructuredSelection)selection).toArray();
			for (int i = 0; i < s.length; i++) {
				IResource resource = null;
				Object o = s[i];
				if (type.isInstance(o)) {
					resource = (IResource)o;
				} else if (o instanceof ResourceMapping) {
					try {
						ResourceTraversal[] travs = ((ResourceMapping)o).getTraversals(
								ResourceMappingContext.LOCAL_CONTEXT, null);
						if (travs != null) {
							for (int k = 0; k < travs.length; k++) {
								IResource[] resources = travs[k].getResources();
								for (int j = 0; j < resources.length; j++) {
									// FIXME ! THINK HOW TO FIX THIS !!!!!
									if (type.isInstance(resources[j]) && resources[j].isAccessible()) {
										tmp.add(resources[j]);
									}
								}
							}
						}
					} catch (CoreException ex) {
						// FIXME !
						System.out.println(ex.getMessage());
					}
				} else if (o instanceof IAdaptable) {
					IAdaptable a = (IAdaptable)o;
					Object adapter = a.getAdapter(IResource.class);
					if (type.isInstance(adapter)) {
						resource = (IResource)adapter;
					}
				}

				if (resource != null && resource.isAccessible()) {
					tmp.add(resource);
				}
			}
		}
		return tmp;
	}

	/**
	 * Return the current Editor Input.
	 * 
	 * @param input
	 * @return the current Editor Input
	 */
	public static AbstractEMFCompareInput getInput() {
		if (input != null) {
			return input;
		} else {
			return null;
			// FIXME
		}
	}

	/**
	 * Register the current Editor Input.
	 * 
	 * @param abstractInput
	 */
	public static void registerInput(AbstractEMFCompareInput abstractInput) {
		input = abstractInput;
	}

	public static Composite getStructuralView() {
		if (structuralView != null) {
			return structuralView;
		} else {
			return null;
			// FIXME
		}
	}

	public static void registerStructuralView(Composite view) {
		structuralView = view;
	}

	public static EditorPart getEditorPart() {
		return emfCEditorPart;
	}

	public static void registerEditorPart(EditorPart editorPart) {
		emfCEditorPart = editorPart;
	}
}
