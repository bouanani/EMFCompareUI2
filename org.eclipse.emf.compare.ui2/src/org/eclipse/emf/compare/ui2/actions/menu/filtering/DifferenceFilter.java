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
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

/**
 * An instance of this class intends to filter differences between elements.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class DifferenceFilter extends ViewerFilter {

	/**
	 * The differenceKind on which the filter should be activated.
	 */
	private List<DifferenceKind> differenceKind = Lists.newArrayList();

	/**
	 * The List of the TreeViewer on which the filter will be applyed.
	 */
	private List<TreeViewer> viewers = Lists.newArrayList();

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
	public void addFilter(DifferenceKind diffKind) {
		differenceKind.add(diffKind);
		for (TreeViewer viewer : viewers) {
			viewer.refresh();
		}
	}

	/**
	 * Remove filter.
	 * 
	 * @param diffKind
	 *            {@link DifferenceKind}
	 */
	public void removeFilter(DifferenceKind diffKind) {
		differenceKind.remove(diffKind);
		for (TreeViewer viewer : viewers) {
			viewer.refresh();
		}
	}

	/**
	 * Install this filter on the given viewer.
	 * 
	 * @param viewer
	 *            the viewer on which the filter will be installed
	 */
	public void install(final TreeViewer viewer) {
		viewer.addFilter(this);
		viewer.getTree().addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				uninstall(viewer);
			}
		});
		viewers.add(viewer);
	}

	/**
	 * Uninstall this filter on the given viewer
	 * 
	 * @param viewer
	 *            the viewer on which the filter will be uninstalled
	 */
	public void uninstall(TreeViewer viewer) {
		viewer.removeFilter(this);
		viewers.remove(viewer);

	}
}
