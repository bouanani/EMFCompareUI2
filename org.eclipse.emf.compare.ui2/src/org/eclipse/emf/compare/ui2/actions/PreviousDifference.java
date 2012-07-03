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

import org.eclipse.jface.action.IAction;
import org.eclipse.wb.swt.ResourceManager;

/**
 * <p>
 * An instance of this class represent an action that permit to select the Next change.
 * <p>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class PreviousDifference extends AbstractDiffNavigateAction {
	/**
	 * Action Constructor.
	 */
	public PreviousDifference() {
		super(IAction.AS_PUSH_BUTTON);
		setToolTipText("Select Previous Change");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
				"icons/full/ctool16/prev_nav.gif"));
	}

	@Override
	public void run() {
		System.out.println("false");
		navigate(false);
	}
}
