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

import org.eclipse.jface.action.Action;

/**
 * an instance of this class represent an action in EMF Compare Editor , actions could be added later by
 * adding an extension of actions.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public abstract class AbstractEMFCompareAction extends Action {
	/**
	 * this will indicate the actions position inside the toolBar, 0 = right | 1 = left.
	 */
	private static int position;

	// FIXME !!
	/**
	 * IT HAVE TO BE FIXED WITH LAURENT.
	 * 
	 * @param style
	 *            style.
	 */
	public AbstractEMFCompareAction(int style) {
		super("", style);
	}
}
