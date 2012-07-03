/** ****************************************************************************
 * Copyright (c) 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.emf.compare.ui2.viewers;

import org.eclipse.emf.compare.ui2.actions.ExportAction;
import org.eclipse.emf.compare.ui2.actions.FilterAction;
import org.eclipse.emf.compare.ui2.actions.GroupAction;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.ResourceManager;

/**
 * An instance of this class represents a ToolBar of the sutrucual Differences View Pane.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class MainToolBar extends Composite {
	/**
	 * Exporting Results into a file ( *.diff ).
	 */
	protected ExportAction exportItem;

	/**
	 * Filtering the Structural Differences.
	 */
	protected FilterAction filterItem;

	/**
	 * Grouping Structural Differnces.
	 */
	protected GroupAction groupItem;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot be null)
	 * @param style
	 *            the style of widget to construct
	 */
	public MainToolBar(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		createActions();
		/**
		 * Left toolToolBar
		 */
		ToolBar leftToolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		FormData fdLeftToolBar = new FormData();
		fdLeftToolBar.top = new FormAttachment(0);
		fdLeftToolBar.left = new FormAttachment(0);
		leftToolBar.setLayoutData(fdLeftToolBar);
		ToolItem differencesLabel = new ToolItem(leftToolBar, SWT.NONE);
		differencesLabel.setImage(ResourceManager.getPluginImage("org.eclipse.emf.compare.ui.2",
				"icons/full/toolb16/differencesIcon.gif"));
		differencesLabel.setText("Structural Differences");
		/**
		 * RighToolBar
		 */
		ToolBar righToolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		fdLeftToolBar.bottom = new FormAttachment(righToolBar, 0, SWT.BOTTOM);
		FormData fdRighToolBar = new FormData();
		fdRighToolBar.top = new FormAttachment(0);
		fdRighToolBar.bottom = new FormAttachment(100);
		fdRighToolBar.right = new FormAttachment(100);
		righToolBar.setLayoutData(fdRighToolBar);
		ToolBarManager toolBarManager = new ToolBarManager(righToolBar);
		toolBarManager.add(exportItem);
		toolBarManager.add(new Separator());
		toolBarManager.add(filterItem);
		toolBarManager.add(groupItem);
		righToolBar.update();
		toolBarManager.update(true);
	}

	/**
	 * Adding Actions to the tool Bar.
	 */
	private void createActions() {
		exportItem = new ExportAction();
		filterItem = new FilterAction();
		groupItem = new GroupAction();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public FilterAction getFilterItem() {
		return filterItem;
	}
}
