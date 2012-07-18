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

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.ui2.providers.CompareAdapterFactoryProviderSpec;
import org.eclipse.emf.compare.ui2.providers.StructureDiffPaneContentProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * The structural differences will be graphically represented into this component. the StructurualDiffView is
 * composed by :
 * </p>
 * <ul>
 * <li><b>ToolBar</b> that contains action for : Exporting, Groupings and Filtering Differences</li>
 * <li><b>TreeViewer</b> to represent the differences into a structural view.
 * </ul>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class StructuralDiffPane extends Composite {

	/**
	 * The Editor on which this action appears.
	 */
	private final EMFCompareEditor editor;

	/**
	 * The comparison object provided by the comparison engine and displayed by the {@link #editor}.
	 */
	private final Comparison comparison;

	/**
	 * {@link TreeViewer} .
	 */
	private TreeViewer differenceTreeViewer;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 *            parent compisite
	 * @param style
	 *            SWT Style
	 */
	public StructuralDiffPane(Composite parent, EMFCompareEditor editor, Comparison comparison) {
		super(parent, SWT.NONE);
		this.editor = editor;
		this.comparison = comparison;
		setLayout(new FormLayout());
		createControl();
	}

	private void createControl() {
		/*
		 * Main Tool Bar
		 */
		MainToolBar toolBar = new MainToolBar(this, getEditor(), getComparison());
		FormData fdmainToolBar = new FormData();
		fdmainToolBar.top = new FormAttachment(0);
		fdmainToolBar.left = new FormAttachment(0);
		fdmainToolBar.right = new FormAttachment(100);
		toolBar.setLayoutData(fdmainToolBar);

		differenceTreeViewer = new TreeViewer(this, SWT.BORDER);
		FormData fdtree = new FormData();
		fdtree.top = new FormAttachment(toolBar, 6);
		fdtree.right = new FormAttachment(100);
		fdtree.bottom = new FormAttachment(100);
		fdtree.left = new FormAttachment(toolBar, 0, SWT.LEFT);
		differenceTreeViewer.getTree().setLayoutData(fdtree);

		List<Match> matches = getComparison().getMatches();
		Collection<AdapterFactory> factories = Lists.newArrayList();
		CompareAdapterFactoryProviderSpec compareFactory = new CompareAdapterFactoryProviderSpec();
		factories.add(compareFactory);
		factories.add(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(factories);

		differenceTreeViewer.setContentProvider(new StructureDiffPaneContentProvider(composedAdapterFactory));
		differenceTreeViewer.setLabelProvider(new AdapterFactoryLabelProvider(composedAdapterFactory));
		differenceTreeViewer.setInput(matches);
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

	/**
	 * Get the differenceTreeViewer.
	 * 
	 * @return the differenceTreeViewer
	 */
	public TreeViewer getTreeViewer() {
		return differenceTreeViewer;
	}

}
