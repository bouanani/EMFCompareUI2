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
import org.eclipse.emf.compare.ui2.actions.menu.filtering.DifferenceFilter;
import org.eclipse.emf.compare.ui2.actions.menu.filtering.FilterAction;
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
public class FilterActionMenu extends AbstractEMFCompareAction implements IMenuCreator {

	/**
	 * The Filter that will be applyed by the action.
	 */
	private final DifferenceFilter differenceFilter;

	/**
	 * Menu Manager that will contain the elemnts of the menu proposed by the Action.
	 */
	private MenuManager menuManager;

	/**
	 * Filter Action constructor.
	 * 
	 * @param style
	 */
	public FilterActionMenu(DifferenceFilter differenceFilter) {
		super(IAction.AS_DROP_DOWN_MENU);
		this.menuManager = new MenuManager();
		this.differenceFilter = differenceFilter;
		setMenuCreator(this);
		setToolTipText("Filters");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
				"icons/full/toolb16/filter.gif"));
		createActions(menuManager);
	}

	/**
	 * Adding Action to menus.
	 */
	public void createActions(MenuManager menu) {
		menu.add(new FilterAction("Changed Elements", DifferenceKind.CHANGE, differenceFilter));
		menu.add(new FilterAction("Added Elements", DifferenceKind.ADD, differenceFilter));
		menu.add(new FilterAction("Removed Elements", DifferenceKind.DELETE, differenceFilter));
		menu.add(new FilterAction("Moved Elements", DifferenceKind.MOVE, differenceFilter));
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
