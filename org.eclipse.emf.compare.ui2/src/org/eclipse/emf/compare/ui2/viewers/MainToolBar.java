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

import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.ui2.actions.ExportAction;
import org.eclipse.emf.compare.ui2.actions.FilterActionMenu;
import org.eclipse.emf.compare.ui2.actions.GroupActionMenu;
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
	 * The Editor on which this action appears.
	 */
	private final EMFCompareEditor editor;

	/**
	 * The comparison object provided by the comparison engine and displayed by the {@link #editor}.
	 */
	private final Comparison comparison;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot be null)
	 * @param style
	 *            the style of widget to construct
	 */
	public MainToolBar(Composite parent, EMFCompareEditor editor, Comparison comparison) {
		super(parent, SWT.NONE);
		this.editor = editor;
		this.comparison = comparison;

		setLayout(new FormLayout());
		/*
		 * RighToolBar positon and layout
		 */
		ToolBar righToolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		FormData fdRighToolBar = new FormData();
		fdRighToolBar.top = new FormAttachment(0);
		fdRighToolBar.bottom = new FormAttachment(100);
		fdRighToolBar.right = new FormAttachment(100);
		righToolBar.setLayoutData(fdRighToolBar);

		ToolBarManager toolBarManager = new ToolBarManager(righToolBar);
		createActions(toolBarManager);
		// righToolBar.update();
		toolBarManager.update(true);

		/*
		 * Left toolToolBar positon and style creation
		 */
		ToolBar leftToolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		FormData fdLeftToolBar = new FormData();
		fdLeftToolBar.bottom = new FormAttachment(righToolBar, 0, SWT.BOTTOM);
		fdLeftToolBar.top = new FormAttachment(0);
		fdLeftToolBar.left = new FormAttachment(0);
		leftToolBar.setLayoutData(fdLeftToolBar);

		ToolItem differencesLabel = new ToolItem(leftToolBar, SWT.NONE);
		// FIXME! change the ResourceManager into activator.
		differencesLabel.setImage(ResourceManager.getPluginImage("org.eclipse.emf.compare.ui.2",
				"icons/full/toolb16/differencesIcon.gif"));
		differencesLabel.setText("Structural Differences");
	}

	/**
	 * Adding Actions to the tool Bar.
	 */
	private void createActions(ToolBarManager toolBar) {
		toolBar.add(new ExportAction());
		toolBar.add(new Separator());
		toolBar.add(new FilterActionMenu(editor.getDifferenceFilter()));
		toolBar.add(new GroupActionMenu(getEditor(), getComparison()));
	}

	/**
	 * Get the editor.
	 * 
	 * @return the editor
	 */
	private EMFCompareEditor getEditor() {
		return editor;
	}

	/**
	 * Get the comparison.
	 * 
	 * @return the comparison
	 */
	private Comparison getComparison() {
		return comparison;
	}

}
