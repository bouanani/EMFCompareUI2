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
import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * ModelContent Provider used by the
 * {@link org.eclipse.emf.compare.ui2.viewers.StructuralDiffPane #getStrucDifftreeViewer()}. Assumes that its
 * input is a {@link Diff}.
 * 
 * @author <a href="mailto:bouanani.meher@obeo.fr">Bouanani Meher</a>
 */
public class ModelStructureContentProvider implements ITreeContentProvider {

	/**
	 * Resutl of the underlying comparison.
	 */
	protected EList<Match> input;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		input = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(Viewer, Object, Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		((TreeViewer)viewer).getTree().clearAll(true);
		if (newInput == null) {
			return;
		}
		if (newInput instanceof Match) {
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ITreeContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object inputElement) {
		return ((Collection)input).toArray();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ITreeContentProvider#getChildren(Object)
	 */
	public Object[] getChildren(Object parentElement) {
		Object[] children = null;
		if (parentElement instanceof EObject) {
			final Collection<EObject> childrenList = new ArrayList<EObject>();
			for (final EObject child : ((EObject)parentElement).eContents()) {
				childrenList.add(child);
			}
			children = childrenList.toArray();
		}
		return children;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ITreeContentProvider#getParents(Object)
	 */
	public Object getParent(Object element) {
		Object parent = null;
		if (element instanceof EObject) {
			parent = ((EObject)element).eContainer();
		}
		return parent;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChildren(Object element) {
		boolean hasChildren = false;
		if (element instanceof EObject) {
			hasChildren = !((EObject)element).eContents().isEmpty();
		}
		return hasChildren;
	}

}
