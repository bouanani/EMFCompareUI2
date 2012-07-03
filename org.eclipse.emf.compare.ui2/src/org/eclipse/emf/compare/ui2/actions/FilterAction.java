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

import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.ui2.actions.menu.filtering.FilterActionType;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.wb.swt.ResourceManager;

/**
 * Using this action the user could filter the differences as : - Changed Elements - Added Elements - Removed
 * Elements - Moved Elements.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class FilterAction extends AbstractEMFCompareAction implements IMenuCreator {

	/**
	 * Menu Manager that will contain the elemnts of the menu proposed by the Action.
	 */
	protected MenuManager menuManager;

	/**
	 * Filter Action constructor.
	 * 
	 * @param style
	 */
	public FilterAction() {
		super(IAction.AS_DROP_DOWN_MENU);
		setToolTipText("Filters");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
				"icons/full/toolb16/filter.gif"));
		setMenuCreator(this);
		menuManager = new MenuManager();
		addActions();

	}

	/**
	 * Adding Action to menus.
	 */
	public void addActions() {
		menuManager.add(new FilterActionType(DifferenceKind.CHANGE));
		menuManager.add(new FilterActionType(DifferenceKind.ADD));
		menuManager.add(new FilterActionType(DifferenceKind.DELETE));
		menuManager.add(new FilterActionType(DifferenceKind.MOVE));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see IMenuCreator#dispose()
	 */
	public void dispose() {
		menuManager.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see IMenuCreator#getMenu(Control)
	 */
	public Menu getMenu(Control parent) {
		return menuManager.createContextMenu(parent);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see IMenuCreator#getMenu(Menu)
	 */
	public Menu getMenu(Menu parent) {
		return menuManager.getMenu();
	}
}
