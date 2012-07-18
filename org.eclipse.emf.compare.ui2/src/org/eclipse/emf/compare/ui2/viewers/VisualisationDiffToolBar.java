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

import org.eclipse.emf.compare.ui2.actions.CopyAlltoLeft;
import org.eclipse.emf.compare.ui2.actions.CopyAlltoRight;
import org.eclipse.emf.compare.ui2.actions.CopyLeftTorRight;
import org.eclipse.emf.compare.ui2.actions.CopyRightToLeft;
import org.eclipse.emf.compare.ui2.actions.NavigateAction;
import org.eclipse.emf.compare.ui2.actions.ShowHideAncestor;
import org.eclipse.emf.compare.ui2.utilities.EMFCompareConfiguration;
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
 * <p>
 * This Class represents the ToolBar that contains the different actions supported by the EMF Compare UI 2.0
 * </p>
 * <ul>
 * <li><b>ToolBar</b> that contains action for : Exporting, Groupings and Filtering Differences</li>
 * <li><b>TreeViewer</b> to represent the differences into a structural view.
 * </ul>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class VisualisationDiffToolBar extends Composite {

	/**
	 * The Editor on which this toolBar will appear.
	 */
	private final EMFCompareEditor editor;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot be null)
	 * @param style
	 *            the style of widget to construct
	 */
	public VisualisationDiffToolBar(Composite parent, int style, EMFCompareEditor editor) {
		super(parent, style);
		this.editor = editor;
		setLayout(new FormLayout());

		/*
		 * Left Tool Bar
		 */
		Composite leftToolBarComposite = new Composite(this, SWT.NONE);
		leftToolBarComposite.setLayout(new FormLayout());
		FormData fdleftToolBarComposite = new FormData();
		fdleftToolBarComposite.top = new FormAttachment(0);
		fdleftToolBarComposite.left = new FormAttachment(0);
		leftToolBarComposite.setLayoutData(fdleftToolBarComposite);
		ToolBar leftToolBar = new ToolBar(leftToolBarComposite, SWT.FLAT | SWT.RIGHT);
		FormData fdleftToolBar = new FormData();
		fdleftToolBar.top = new FormAttachment(0);
		leftToolBar.setLayoutData(fdleftToolBar);
		ToolItem visualizationType = new ToolItem(leftToolBar, SWT.NONE);
		visualizationType.setImage(ResourceManager.getPluginImage("org.eclipse.emf.compare.ui.2",
				"iconsMainTBar/differencesIcon.gif"));
		visualizationType.setText("Visualizatin of Structural Differences");
		leftToolBar.update();

		/*
		 * RighToolBar
		 */
		ToolBar righToolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		FormData fdRighToolBar = new FormData();
		fdRighToolBar.top = new FormAttachment(0);
		fdRighToolBar.right = new FormAttachment(100);
		righToolBar.setLayoutData(fdRighToolBar);
		ToolBarManager righToolBarManager = new ToolBarManager(righToolBar);
		createAncestorPaneManagerAction(righToolBarManager, EMFCompareConfiguration.fThreeWay);
		createActions(righToolBarManager);
		righToolBar.update();
		righToolBarManager.update(true);
	}

	/**
	 * Adding Actions to the tool Bar.
	 */
	private void createActions(ToolBarManager toolBarManager) {
		toolBarManager.add(new CopyAlltoRight());
		toolBarManager.add(new CopyAlltoLeft());
		toolBarManager.add(new CopyLeftTorRight());
		toolBarManager.add(new CopyRightToLeft());
		toolBarManager.add(new Separator());
		toolBarManager.add(new NavigateAction("Select Next Change", getEditor(), true));
		toolBarManager.add(new NavigateAction("Select Previous Change", getEditor(), false));
	}

	/**
	 * To Manage the creation of an ancestor action to manage the visibilty of the ancestor Pane.
	 * 
	 * @param ok
	 *            boolean parameters if true the action is created if false the action is not created.
	 * @param toolBarManager
	 *            the ToolBarManager on which the action should be added
	 */
	private void createAncestorPaneManagerAction(ToolBarManager toolBarManager, boolean ok) {
		if (ok) {
			ShowHideAncestor showHideAncestor = new ShowHideAncestor();
			toolBarManager.add(showHideAncestor);
			Separator separator = new Separator();
			toolBarManager.add(separator);
		}
	}

	/**
	 * Get the editor.
	 * 
	 * @return the editor
	 */
	public EMFCompareEditor getEditor() {
		return editor;
	}

}
