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

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.ui2.utilities.Utilities;
import org.eclipse.emf.compare.ui2.viewers.EMFCompareEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.wb.swt.ResourceManager;

/**
 * <p>
 * In instance of this class represents an action that permit to copy the current change from right to the
 * left.
 * <p>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class CopyLeftTorRight extends AbstractEMFCompareAction {

	/**
	 * To handl out the selected object in order to copy this element in the right side.
	 */
	private Diff currentSelection;

	/**
	 * The Editor Viewer.
	 */
	private EMFCompareEditor editorView;

	/**
	 * Action Constructor.
	 */
	public CopyLeftTorRight() {
		super(IAction.AS_PUSH_BUTTON);
		setToolTipText("Copy Current Change from Left to Right");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
				"icons/full/ctool16/copycont_r_co.gif"));
	}

	@Override
	public void run() {
		currentSelection.copyLeftToRight();
		System.out.println(currentSelection.getState());
		editorView = (EMFCompareEditor)Utilities.getEditorPart();
		editorView.fireDirtyPropertyChange(true);
		editorView.setRightDirty(true);
		// FIXME !
		((EMFCompareEditor)editorView).getStructuralView().getStrucDifftreeViewer().refresh();
		// setisLeftIsDirty = true
	}

	public void setCurrentSelection(Diff selection) {
		currentSelection = selection;
	}
}
