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
package org.eclipse.emf.compare.ui2.actions;

import java.util.List;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceState;
import org.eclipse.emf.compare.ui2.input.EMFCompareInput;
import org.eclipse.emf.compare.ui2.utilities.Utilities;
import org.eclipse.emf.compare.ui2.viewers.EMFCompareEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.wb.swt.ResourceManager;

/**
 * <p>
 * An instance of this class represent an action that permit to copy all the content of the left side element
 * into the right side.
 * <p>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class CopyAlltoLeft extends AbstractEMFCompareAction {

	/**
	 * The Editor View.
	 */
	private EMFCompareEditor editorView;

	/**
	 * Constructor.
	 */
	public CopyAlltoLeft() {
		super(IAction.AS_PUSH_BUTTON);
		setToolTipText("Copy All Non-Conflict Changes from Right To Left");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
				"icons/full/ctool16/copy_l_co.gif"));
	}

	@Override
	public void run() {
		boolean changed = false;
		editorView = (EMFCompareEditor)Utilities.getEditorPart();
		List<Diff> differences = ((EMFCompareInput)editorView.getEditorInput()).getComparison()
				.getDifferences();
		for (Diff diff : differences) {
			if (diff.getConflict() != null && diff.getState().equals(DifferenceState.UNRESOLVED)) {
				diff.copyRightToLeft();
				changed = true;
			}
		}
		if (changed) {
			editorView.fireDirtyPropertyChange(true);
			editorView.setLeftDirty(true);
			editorView.getStructDiffViewPane().getTreeViewer().refresh();
			setEnabled(false);
			editorView.getDifferenceViewPane().getVisualisationDiffToolBar().getCopyAlltoRight().setEnabled(
					false);
		}
	}
}
