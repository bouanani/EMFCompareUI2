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

import org.eclipse.emf.compare.ui2.viewers.EMFCompareEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.wb.swt.ResourceManager;

/**
 * <p>
 * An instance of this class represent an action that permit to select the Previous change.
 * <p>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class NavigateAction extends AbstractEMFCompareAction {

	/**
	 * The Editor on which this action appears.
	 */
	private final EMFCompareEditor editor;

	/**
	 * Define wether it's a Next Selection or Previous Selection
	 */
	private final boolean next;

	/**
	 * Action Constructor.
	 * 
	 * @param style
	 *            style of the action
	 */
	public NavigateAction(String toolTip, EMFCompareEditor editor, Boolean next) {
		super(IAction.AS_PUSH_BUTTON);
		this.editor = editor;
		this.next = next;
		setToolTipText(toolTip);
		if (next) {
			setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
					"icons//full/ctool16/next_nav.gif"));
		} else {
			setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
					"icons/full/ctool16/prev_nav.gif"));
		}
	}

	/**
	 * Selects the next or previous DiffElement as compared to the currently selected one.
	 * 
	 * @param fIsNext
	 *            down True if we seek the next DiffElement, False for the previous.
	 */
	protected void navigate(boolean next) {

	}

}
