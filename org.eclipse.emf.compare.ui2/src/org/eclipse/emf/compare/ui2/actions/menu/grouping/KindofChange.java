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
package org.eclipse.emf.compare.ui2.actions.menu.grouping;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.ui2.input.EMFCompareInput;
import org.eclipse.emf.compare.ui2.providers.CompareAdapterFactoryProviderSpec;
import org.eclipse.emf.compare.ui2.providers.GroupByKindContentProvider;
import org.eclipse.emf.compare.ui2.utilities.Utilities;
import org.eclipse.emf.compare.ui2.viewers.StructuralDiffPane;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * Grouping Per Kind of Change Action.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class KindofChange extends Action {

	/**
	 * The EMF Compare Input .
	 */
	private EMFCompareInput input;

	/**
	 * Constructor.
	 */
	public KindofChange() {
		super("Per kind Of Changes ", IAction.AS_RADIO_BUTTON);
	}

	@Override
	public void run() {
		DiffKindGroups diff = getDiffGroups();
		TreeViewer viewer = ((StructuralDiffPane)Utilities.getStructuralView()).getStrucDifftreeViewer();
		viewer.setContentProvider(new GroupByKindContentProvider(new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE)));
		Collection<AdapterFactory> factories = Lists.newArrayList();
		CompareAdapterFactoryProviderSpec myFactory = new CompareAdapterFactoryProviderSpec();
		factories.add(myFactory);
		factories.add(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		viewer.setLabelProvider(new AdapterFactoryLabelProvider(new ComposedAdapterFactory(factories)));
		viewer.setInput(diff);
		viewer.refresh();
	}

	/**
	 * Get the Difference Groups by Kind
	 * 
	 * @return {@link DiffKindGroups}
	 */
	public DiffKindGroups getDiffGroups() {
		List<Diff> differences = getInput().getComparison().getDifferences();
		DiffKindGroups results = new DiffKindGroups();
		for (Diff diff : differences) {
			if (results.containKind(diff)) {
				results.addDifferenceElement(diff);
			} else {
				results.addDifferenceGroupElement(getGroupName(diff), diff.getKind());
			}

		}
		return results;
	}

	public EMFCompareInput getInput() {
		return (EMFCompareInput)Utilities.getInput();
	}

	public String getGroupName(Diff diff) {
		if (diff.getKind().equals(DifferenceKind.ADD)) {
			return "added";
		} else if (diff.getKind().equals(DifferenceKind.CHANGE)) {
			return "changed";
		} else if (diff.getKind().equals(DifferenceKind.MOVE)) {
			return "moved";
		} else if (diff.getKind().equals(DifferenceKind.DELETE)) {
			return "removed";
		} else {
			return "other";
		}
	}
}
