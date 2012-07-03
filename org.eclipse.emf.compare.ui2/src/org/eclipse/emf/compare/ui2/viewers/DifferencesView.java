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

package org.eclipse.emf.compare.ui2.viewers;

import org.eclipse.emf.compare.ui2.providers.AdapterUtils;
import org.eclipse.emf.compare.ui2.providers.PropertyContentProvider;
import org.eclipse.emf.compare.ui2.providers.PropertyLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * <p>
 * This class presents the needed component to represent the given selected resources into the workbench.
 * <b>'DifferencesView'</b> class is composed by a {@link TabFolder} that contains respectively :
 * </p>
 * <ul>
 * <li><b>Differences</b> : This tab contain a {@link TreeViewer} to represent the given element</li>
 * <li><b>Properties</b> : This tab contain a {@link Table} to represent the properties of a selected element
 * into the <b>Differences</b> tab</li>
 * </ul>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class DifferencesView extends Composite {
	/**
	 * DifferencesView TabFolder.
	 */
	private TabFolder diffViewTab;

	/**
	 * Represents the resource Path of the selected elements.
	 */
	private Label resourcePath;

	/**
	 * The Resource Path Label container.
	 */
	private Composite label;

	/**
	 * Differences tab.
	 */
	private TabItem diffTab;

	/**
	 * Properties Tab.
	 */
	private TabItem propertiesTab;

	/**
	 * A Table of Properties.
	 */
	private Table propertiesTable;

	/**
	 * A TableViewer of the Properties table.
	 */
	private TableViewer propertiesTabViewer;

	/**
	 * The Attribute Name TableColumnViewer.
	 */
	private TableViewerColumn attributeNameColumn;

	/**
	 * The Value Column.
	 */
	private TableViewerColumn valueColumn;

	/**
	 * The Attribute Name Column Name.
	 */
	private TableColumn tblcAttributeName;

	/**
	 * The TableColumn Value.
	 */
	private TableColumn tblclmnValue;

	/**
	 * The ResourceTree.
	 */
	private Tree resourceTree;

	/**
	 * Resource TreeViewer.
	 */
	private TreeViewer resourceTreeViewer;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 *            Parent Composite
	 * @param style
	 *            SWT Style
	 */
	public DifferencesView(Composite parent, int style) {
		super(parent, SWT.NONE);
		setDragDetect(false);
		setLayout(new FormLayout());
		label = new Composite(this, SWT.NO_FOCUS | SWT.EMBEDDED);
		FormData fdLabel = new FormData();
		fdLabel.right = new FormAttachment(100);
		fdLabel.top = new FormAttachment(0, 5);
		label.setLayoutData(fdLabel);
		label.setLayout(new FormLayout());
		resourcePath = new Label(label, SWT.SHADOW_IN);
		FormData fdResourcePath = new FormData();
		fdResourcePath.bottom = new FormAttachment(100);
		fdResourcePath.right = new FormAttachment(100);
		fdResourcePath.top = new FormAttachment(0);
		fdResourcePath.left = new FormAttachment(0);
		resourcePath.setLayoutData(fdResourcePath);
		final int height = 9;
		resourcePath.setFont(SWTResourceManager.getFont("Segoe UI", height, SWT.NORMAL));
		resourcePath.setText("org.eclipse.tutorial.editors/models/DefaultName.ecore");
		diffViewTab = new TabFolder(this, SWT.BOTTOM);
		fdLabel.left = new FormAttachment(diffViewTab, 0, SWT.LEFT);
		FormData fdiffViewTab = new FormData();
		fdiffViewTab.left = new FormAttachment(0);
		fdiffViewTab.right = new FormAttachment(100);
		fdiffViewTab.bottom = new FormAttachment(100);
		final int offset = 25;
		fdiffViewTab.top = new FormAttachment(0, offset);
		diffViewTab.setLayoutData(fdiffViewTab);
		diffTab = new TabItem(diffViewTab, SWT.NONE);
		diffTab.setText("Differences");
		resourceTreeViewer = new TreeViewer(diffViewTab, SWT.NONE);
		resourceTree = resourceTreeViewer.getTree();
		diffTab.setControl(resourceTree);
		propertiesTab = new TabItem(diffViewTab, SWT.NONE);
		propertiesTab.setText("Properties");
		propertiesTabViewer = new TableViewer(diffViewTab, SWT.NONE | SWT.FULL_SELECTION);
		propertiesTable = propertiesTabViewer.getTable();
		propertiesTable.setHeaderVisible(true);
		propertiesTab.setControl(propertiesTable);
		attributeNameColumn = new TableViewerColumn(propertiesTabViewer, SWT.NONE);
		tblcAttributeName = attributeNameColumn.getColumn();
		tblcAttributeName.setMoveable(true);
		final int width = 153;
		tblcAttributeName.setWidth(width);
		tblcAttributeName.setText("Attribute  Name");
		valueColumn = new TableViewerColumn(propertiesTabViewer, SWT.NONE);
		tblclmnValue = valueColumn.getColumn();
		tblclmnValue.setWidth(width);
		tblclmnValue.setText("Value");
	}

	@Override
	protected void checkSubclass() {
	}

	public TabFolder getDiffViewTab() {
		return diffViewTab;
	}

	public TreeViewer getResourceTreeViewer() {
		return resourceTreeViewer;
	}

	/**
	 * Set the InputProvider of the Properties Table Viewer.
	 * 
	 * @param notifier
	 *            the input notifier
	 */
	public void setTableInputProvider(Object notifier) {
		propertiesTabViewer.setContentProvider(new PropertyContentProvider(notifier));
		propertiesTabViewer.setLabelProvider(new PropertyLabelProvider(AdapterUtils.getAdapterFactory()));
		propertiesTabViewer.setInput(notifier);
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath.setText(resourcePath);
	}

}
