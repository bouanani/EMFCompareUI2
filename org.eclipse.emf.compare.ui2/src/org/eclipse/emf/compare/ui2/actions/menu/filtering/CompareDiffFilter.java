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
package org.eclipse.emf.compare.ui2.actions.menu.filtering;

import com.google.common.collect.Lists;

import java.util.List;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.DifferenceState;
import org.eclipse.emf.compare.Match;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * An instance of this class intends to filter differences between elements.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class CompareDiffFilter extends ViewerFilter {

	/**
	 * The differenceKind on which the filter should be activated.
	 */
	protected List<Object> differenceKind;

	/**
	 * The TreeViewer on which the filter will be applyed.
	 */
	protected TreeViewer viewer;

	/**
	 * Constructor.
	 * 
	 * @param treeViewer
	 *            the viewer.
	 */
	public CompareDiffFilter(TreeViewer treeViewer) {
		differenceKind = Lists.newArrayList();
		viewer = treeViewer;

	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public boolean select(Viewer viewers, Object parentElement, Object element) {
		boolean result = false;
		if (differenceKind.isEmpty()) {
			if (element instanceof Diff) {
				result = fIsDiffUnresolved((Diff)element);
			} else {
				result = true;
			}
		} else if (element instanceof Diff) {
			result = isdiffKindFilter(element) && fIsDiffUnresolved((Diff)element);
		} else if (element instanceof Match) {
			List<Diff> differences = Lists.newArrayList(((Match)element).getAllDifferences());
			for (Diff diff : differences) {
				if (isdiffKindFilter(diff)) {
					result = true;
					continue;
				}
			}
		}
		return result;
	}

	/**
	 * Check wether the choosed filter fits with the Differences Kind.
	 * 
	 * @param element
	 *            treeItem
	 * @return true if the differencekind of the item is equal to the chechked fitler and false if not.
	 */
	public boolean isdiffKindFilter(Object element) {
		for (Object object : differenceKind) {
			if (((Diff)element).getKind().equals(object)) {
				return true;
			}
		}
		return false;
	}

	public boolean fIsDiffUnresolved(Diff difference) {
		return difference.getState().equals(DifferenceState.UNRESOLVED);
	}

	/**
	 * Add filter.
	 * 
	 * @param diffKind
	 *            {@link DifferenceKind}
	 */
	public void addFilter(Object diffKind) {
		differenceKind.add(diffKind);
		viewer.refresh();
	}

	/**
	 * Remove filter.
	 * 
	 * @param diffKind
	 *            {@link DifferenceKind}
	 */
	public void removeFilter(Object diffKind) {
		differenceKind.remove(diffKind);
		viewer.refresh();
	}

}
