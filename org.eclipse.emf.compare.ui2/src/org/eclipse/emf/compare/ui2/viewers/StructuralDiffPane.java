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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.ui2.actions.menu.filtering.CompareDiffFilter;
import org.eclipse.emf.compare.ui2.input.EMFCompareInput;
import org.eclipse.emf.compare.ui2.providers.CompareAdapterFactoryProviderSpec;
import org.eclipse.emf.compare.ui2.providers.SelectedItemContentProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;

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
	 * {@link MainToolBar} .
	 */
	private MainToolBar toolBar;

	/**
	 * {@link TreeViewer} .
	 */
	private TreeViewer diffStructtreeViewer;

	/**
	 * Tree Object of the StructDiffTreeViewer.
	 */
	private Tree structTree;

	/**
	 * The Matched elements during the comparison.
	 */
	private EList<Match> matches;

	/**
	 * filer .
	 */
	private CompareDiffFilter filter;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 *            parent compisite
	 * @param style
	 *            SWT Style
	 */
	public StructuralDiffPane(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());
		/**
		 * Main Tool Bar
		 */
		toolBar = new MainToolBar(this, SWT.NONE);
		FormData fdmainToolBar = new FormData();
		fdmainToolBar.top = new FormAttachment(0);
		fdmainToolBar.left = new FormAttachment(0);
		toolBar.setLayoutData(fdmainToolBar);
		diffStructtreeViewer = new TreeViewer(this, SWT.BORDER);
		structTree = diffStructtreeViewer.getTree();
		fdmainToolBar.right = new FormAttachment(100);
		FormData fdtree = new FormData();
		fdtree.top = new FormAttachment(0, 24);
		fdtree.bottom = new FormAttachment(100, 0);
		fdtree.left = new FormAttachment(0);
		fdtree.right = new FormAttachment(100);
		structTree.setLayoutData(fdtree);
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * Set InputProvider - Still not yet fixed - .
	 * 
	 * @param input
	 *            EditorInput
	 */
	public void setInputProvider(IEditorInput input) {
		if (((EMFCompareInput)input).getComparison() != null) {
			matches = ((EMFCompareInput)input).getComparison().getMatches();
			diffStructtreeViewer.setContentProvider(new SelectedItemContentProvider(
					new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)));

			Collection<AdapterFactory> factories = Lists.newArrayList();
			CompareAdapterFactoryProviderSpec myFactory = new CompareAdapterFactoryProviderSpec();
			factories.add(myFactory);
			factories.add(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
			diffStructtreeViewer.setLabelProvider(new AdapterFactoryLabelProvider(new ComposedAdapterFactory(
					factories)));
			diffStructtreeViewer.setInput(matches);
			filter = new CompareDiffFilter(diffStructtreeViewer);
			diffStructtreeViewer.addFilter(filter);

		}
	}

	public MainToolBar getTooBar() {
		return toolBar;
	}

	public void setTooBar(MainToolBar tooBar) {
		this.toolBar = tooBar;
	}

	public TreeViewer getStrucDifftreeViewer() {
		return diffStructtreeViewer;
	}

	public CompareDiffFilter getFilter() {
		return filter;
	}

}
