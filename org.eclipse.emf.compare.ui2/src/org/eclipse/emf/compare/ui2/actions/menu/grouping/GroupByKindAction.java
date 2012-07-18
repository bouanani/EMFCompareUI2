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

import java.util.List;

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.ui2.viewers.EMFCompareEditor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

/**
 * Grouping Per Kind of Change Action.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class GroupByKindAction extends Action {

	/**
	 * The Editor on which this action appears.
	 */
	private final EMFCompareEditor editor;

	/**
	 * The comparison object provided by the comparison engine and displayed by the {@link #editor}.
	 */
	private final Comparison comparison;

	/**
	 * Constructor.
	 */
	public GroupByKindAction(EMFCompareEditor editor, Comparison comparison) {
		super("Per kind Of Changes ", IAction.AS_RADIO_BUTTON);
		this.editor = editor;
		this.comparison = comparison;
	}

	@Override
	public void run() {
		DifferenceGroupProvider diff = getDiffGroups();
		editor.group(diff);
	}

	/**
	 * Get the Difference Groups by Kind
	 * 
	 * @return {@link DiffKindGroups}
	 */
	public DiffKindGroups getDiffGroups() {
		List<Diff> differences = comparison.getDifferences();
		DiffKindGroups results = new DiffKindGroups();
		results.addAll(differences);
		return results;
	}
}
