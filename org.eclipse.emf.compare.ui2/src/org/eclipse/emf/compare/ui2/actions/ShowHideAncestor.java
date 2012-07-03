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
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.ResourceManager;

/**
 * <p>
 * An instance of this class represent an action that permit to Show and hide the ancestor Pane.
 * <p>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */

public class ShowHideAncestor extends AbstractEMFCompareAction {
	/**
	 * To Check out wheter the ancestor pane is hided or not.
	 */
	private boolean ancestorHided;

	/**
	 * the ancestor SashForm Container.
	 */
	private SashForm ancestorContainer;

	/**
	 * The ancestor view composite.
	 */
	private Composite ancestorView;

	/**
	 * Action Constructor.
	 */
	public ShowHideAncestor() {
		super(IAction.AS_CHECK_BOX);
		setToolTipText("Show Ancestor Pane");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
				"icons/full/ctool16/ancestorpane_co.gif"));
		if (this.isChecked()) {
			setToolTipText("Hide Ancestor Pane");
		}
	}

	@Override
	public void run() {
		if (!ancestorHided) {
			ancestorHided = true;
			ancestorView.setVisible(ancestorHided);
			ancestorContainer.setWeights(new int[] {1, 1 });
		} else {
			ancestorHided = false;
			ancestorView.setVisible(ancestorHided);
			ancestorContainer.setWeights(new int[] {0, 1 });
			ancestorContainer.setSashWidth(5);
		}
	}

	public void setAncestorContainer(SashForm ancestorContainer) {
		this.ancestorContainer = ancestorContainer;
	}

	public void setAncestorView(Composite ancestor) {
		this.ancestorView = ancestor;
	}
}
