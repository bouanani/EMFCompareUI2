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

import com.google.common.collect.Lists;

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
 * An instance of this class represent an action that permit to copy all the content of the right side element
 * into the left side.
 * <p>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class CopyAlltoRight extends AbstractEMFCompareAction {
	/**
	 * The Editor View.
	 */
	private EMFCompareEditor editorView;

	/**
	 * Action Constructor.
	 */
	public CopyAlltoRight() {
		super(IAction.AS_PUSH_BUTTON);
		setToolTipText("Copy All Non-Conflict Changes from Left To Right");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
				"icons/full/ctool16/copy_r_co.gif"));
	}

	@Override
	public void run() {
		boolean changed = false;
		editorView = (EMFCompareEditor)Utilities.getEditorPart();
		List<Diff> differences = Lists.newArrayList();
		differences = ((EMFCompareInput)editorView.getInput()).getComparison().getDifferences();
		for (Diff diff : differences) {
			if (diff.getConflict() == null && diff.getState().equals(DifferenceState.UNRESOLVED)) {
				diff.copyLeftToRight();
				changed = true;
				System.out.println("DONE");
			}
			else {
				System.out.println("Conflict has been found");
			}
		}
		if (changed) {
			editorView.fireDirtyPropertyChange(true);
			editorView.setLeftDirty(true);
			editorView.getStructuralView().getStrucDifftreeViewer().refresh();
			setEnabled(false);
			editorView.getVisualisationDiffView().getVisualisationDiffToolBar().getCopyAlltoLeft()
					.setEnabled(false);
		}
	}

}
