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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.ui2.input.EMFCompareInput;
import org.eclipse.emf.compare.ui2.utilities.Utilities;
import org.eclipse.emf.compare.ui2.viewers.StructuralDiffPane;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * <p>
 * An instance of this class represent an action that permit to select the Previous change.
 * <p>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public abstract class AbstractDiffNavigateAction extends AbstractEMFCompareAction {

	/**
	 * Keep track of the current selection.
	 */
	protected final List<Diff> currentSelection = new ArrayList<Diff>();

	/**
	 * The current selection's index.
	 */
	private static int index;

	/**
	 * Action Constructor.
	 * 
	 * @param style
	 *            style of the action
	 */
	public AbstractDiffNavigateAction(int style) {
		super(style);

	}

	/**
	 * Selects the next or previous DiffElement as compared to the currently selected one.
	 * 
	 * @param fIsNext
	 *            down True if we seek the next DiffElement, False for the previous.
	 */
	protected void navigate(boolean fIsNext) {
		final List<Diff> listDifferences = getDifferences();
		if (listDifferences.size() != 0) {
			final Diff differenceElement;
			if (currentSelection.size() != 0) {
				differenceElement = currentSelection.get(0);
				index = getIndexOfSelectedElement(differenceElement);
			} else if (listDifferences.size() == 1) {
				differenceElement = listDifferences.get(0);
			} else if (fIsNext) {
				differenceElement = listDifferences.get(0);
				index = 0;
			} else {
				differenceElement = listDifferences.get(listDifferences.size() - 1);
				index = listDifferences.size() - 1;
			}
			for (int i = 0; i < listDifferences.size(); i++) {
				if (listDifferences.get(i).equals(differenceElement) && fIsNext) {
					Diff next = listDifferences.get(i);
					if (index == 0 && next != null) {
						next = listDifferences.get(index);
						updateSelection(next);
						index = i + 1;
						break;
					} else if (next != null) {
						if ((index + 1) > listDifferences.size()) {
							index = 0;
						} else {
							index = i + 1;
							next = listDifferences.get(index);
							updateSelection(next);
							break;
						}
					}
				} else if (listDifferences.get(i).equals(differenceElement) && !fIsNext) {
					Diff previous = listDifferences.get(i);
					if (index == (listDifferences.size() - 1) && previous != null) {
						previous = listDifferences.get(index);
						updateSelection(previous);
						index = index - 1;
						break;
					}
					if (i > 0) {
						previous = listDifferences.get(i - 1);
					}
					if (previous != null) {
						updateSelection(previous);
						break;
					}
				}
			}
		}
	}

	/**
	 * This will return the list of differences.
	 * 
	 * @return List<Diff> list of differences.
	 */
	protected List<Diff> getDifferences() {
		final List<Diff> diffs = getInput().getComparison().getDifferences();
		return diffs;
	}

	/**
	 * Sets the parts' tree selection given the list of DiffElements to select.
	 * 
	 * @param diffElement
	 *            Diff backing the current selection.
	 */
	protected void updateSelection(Diff diffElement) {
		currentSelection.clear();
		IStructuredSelection newSelection = new StructuredSelection(diffElement);
		getStructuralViewPane().getStrucDifftreeViewer().setSelection(newSelection);
		currentSelection.add(diffElement);
	}

	/**
	 * Get the EMF Compare Input.
	 * 
	 * @return EMFCompareInput
	 */
	private EMFCompareInput getInput() {
		return (EMFCompareInput)Utilities.getInput();
	}

	/**
	 * Get an instance of the Structural Diff View.
	 * 
	 * @return StructuralDiffPane
	 */
	private StructuralDiffPane getStructuralViewPane() {
		return (StructuralDiffPane)Utilities.getStructuralView();
	}

	/**
	 * Return the Index of the current selected element.
	 * 
	 * @param currentElement
	 * @return the index of the current Selected Element.
	 */
	private int getIndexOfSelectedElement(Diff currentElement) {
		return getDifferences().indexOf(currentElement);
	}
}
