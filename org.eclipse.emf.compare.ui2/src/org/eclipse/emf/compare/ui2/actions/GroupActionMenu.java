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

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.ui2.actions.menu.grouping.DefaultGrouping;
import org.eclipse.emf.compare.ui2.actions.menu.grouping.GroupByKindAction;
import org.eclipse.emf.compare.ui2.actions.menu.grouping.MetaModelElementsChange;
import org.eclipse.emf.compare.ui2.viewers.EMFCompareEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.wb.swt.ResourceManager;

/**
 * Using this action the user could group the differences per kind of changes or per meta-model elements.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class GroupActionMenu extends AbstractEMFCompareAction implements IMenuCreator {

	/**
	 * Menu Manager that will contain the elemnts of the menu proposed by the Action.
	 */
	private MenuManager menuManager;

	/**
	 * The Editor on which this action appears.
	 */
	private final EMFCompareEditor editor;

	/**
	 * The comparison object provided by the comparison engine and displayed by the {@link #editor}.
	 */
	private final Comparison comparison;

	/**
	 * Group Action Constructor.
	 */
	public GroupActionMenu(EMFCompareEditor editor, Comparison comparison) {
		super(IAction.AS_DROP_DOWN_MENU);
		setToolTipText("Groups");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.emf.compare.ui.2",
				"icons/full/toolb16/prefshelp.gif"));
		setMenuCreator(this);
		this.menuManager = new MenuManager();
		this.editor = editor;
		this.comparison = comparison;
		createActions(menuManager);
	}

	/**
	 * Create the grouping action in the given menu.
	 */
	public void createActions(MenuManager menu) {
		menu.add(new DefaultGrouping());
		menu.add(new GroupByKindAction(getEditor(), getComparison()));
		menu.add(new MetaModelElementsChange());
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

	/**
	 * Get the Comparison.
	 * 
	 * @return The {@link #comparison}
	 */
	private Comparison getComparison() {
		return comparison;
	}

	/**
	 * Get the Editor.
	 * 
	 * @return The {@link #editor}
	 */
	private EMFCompareEditor getEditor() {
		return editor;
	}

}
