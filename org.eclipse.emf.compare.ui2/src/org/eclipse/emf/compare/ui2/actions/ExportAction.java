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
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.wb.swt.ResourceManager;

/**
 * an instance of this class will give the user the possibility of saving the actual difference results. this
 * action is designed to contain a menu for further extension in the future.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class ExportAction extends AbstractEMFCompareAction implements IMenuCreator {

	/**
	 * Menu Manager that will contain the elemnts of the menu proposed by the Action.
	 */
	protected MenuManager menuManager;

	/**
	 * ExportAction Constructor.
	 */
	public ExportAction() {
		super(IAction.AS_DROP_DOWN_MENU);
		setToolTipText("Export Diff Model");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
				"icons/full/toolb16/save.gif"));
		setMenuCreator(this);
		menuManager = new MenuManager();
		addActions();
	}

	/**
	 * Adding Action to menus.
	 */
	public void addActions() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see IMenuCreator#dispose()
	 */
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see IMenuCreator#getMenu(Control)
	 */
	public Menu getMenu(Control parent) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see IMenuCreator#getMenu(Menu)
	 */
	public Menu getMenu(Menu parent) {
		return null;
	}

}
