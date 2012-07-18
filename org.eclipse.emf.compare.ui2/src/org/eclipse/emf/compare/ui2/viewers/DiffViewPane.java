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

import com.google.common.collect.Lists;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.ui2.input.EMFCompareInput;
import org.eclipse.emf.compare.ui2.providers.StructureDiffPaneContentProvider;
import org.eclipse.emf.compare.ui2.sync.EventHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;

/**
 * <p>
 * All the selected elements will be represented into this component and differences will be visualized here
 * also. this graphical component is composed principally by three {@link DifferencesView} and a
 * {@link VisualisationDiffToolBar}.
 * </p>
 * <ul>
 * <li>ToolBar : Contain several actions that permits the user to navigate into the differences and copy them
 * from leftToRight or from RightToLeft and also to Show/Hide the ancestor pane.</li>
 * <li>ancestor : Will be shown only in case of three-way differences)</li>
 * <li>leftView : The first selected element.</li>
 * <li>rightView : The second selected element.</li>
 * </ul>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */

public class DiffViewPane extends Composite implements ISelectionChangedListener {

	/**
	 * A Tool Bar.
	 * 
	 * @see VisualisationDiffToolBar
	 */
	private VisualisationDiffToolBar visualisationDiffToolBar;

	/**
	 * SashForm.
	 * 
	 * @see SashForm
	 */
	private SashForm sashForm;

	/**
	 * Ancestor Pane.
	 * 
	 * @see DifferencesView
	 */
	private DifferencesView ancestorView;

	/**
	 * LeftView Pane.
	 * 
	 * @see DifferencesView
	 */
	private DifferencesView leftView;

	/**
	 * RightView Pane.
	 * 
	 * @see DifferencesView
	 */
	private DifferencesView rightView;

	/**
	 * EventSychronizer for the of the DifferenceView Pane.
	 */
	private EventHandler eventSynchronizer;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot be null)
	 * @param style
	 *            the style of widget to construct
	 */
	public DiffViewPane(Composite parent, int style, EMFCompareEditor editor) {
		super(parent, SWT.NONE);
		setLayout(new FormLayout());
		/**
		 * Utilities Tool bar
		 */
		visualisationDiffToolBar = new VisualisationDiffToolBar(this, SWT.NONE);
		FormData fdDiffViewToolBar = new FormData();
		fdDiffViewToolBar.top = new FormAttachment(0, 0);
		fdDiffViewToolBar.left = new FormAttachment(0);
		visualisationDiffToolBar.setLayoutData(fdDiffViewToolBar);
		sashForm = new SashForm(this, SWT.BORDER | SWT.VERTICAL);
		fdDiffViewToolBar.right = new FormAttachment(sashForm, 0, SWT.RIGHT);
		sashForm.setSashWidth(2);
		FormData fdContainerSashForm = new FormData();
		fdContainerSashForm.top = new FormAttachment(visualisationDiffToolBar, 0);
		fdContainerSashForm.bottom = new FormAttachment(100, 0);
		fdContainerSashForm.left = new FormAttachment(0);
		fdContainerSashForm.right = new FormAttachment(100);
		sashForm.setLayoutData(fdContainerSashForm);
		ancestorView = new DifferencesView(sashForm, SWT.NONE);
		visualisationDiffToolBar.getShowHideAncestor().setAncestorView(ancestorView);
		ancestorView.setVisible(false);
		SashForm diffSashForm = new SashForm(sashForm, SWT.NONE);
		diffSashForm.setSashWidth(30);
		leftView = new DifferencesView(diffSashForm, SWT.NONE);
		Tree tree = leftView.getResourceTreeViewer().getTree();
		FormData leftViewForm = (FormData)leftView.getDiffViewTab().getLayoutData();
		leftViewForm.bottom = new FormAttachment(100);
		leftViewForm.top = new FormAttachment(0, 25);
		leftViewForm.right = new FormAttachment(100);
		leftViewForm.left = new FormAttachment(0);
		rightView = new DifferencesView(diffSashForm, SWT.NONE);
		FormData rightForm = (FormData)rightView.getDiffViewTab().getLayoutData();
		rightForm.bottom = new FormAttachment(100);
		rightForm.top = new FormAttachment(0, 25);
		rightForm.right = new FormAttachment(100);
		rightForm.left = new FormAttachment(0);
		diffSashForm.setWeights(new int[] {1, 1 });
		sashForm.setWeights(new int[] {0, 2 });
		// Syncronize different events beteween the elements.
		eventSynchronizer = new EventHandler(ancestorView, leftView, rightView);
		// Configuring Actions
		visualisationDiffToolBar.getShowHideAncestor().setAncestorContainer(sashForm);
	}

	/**
	 * Set the InputProvider of this View.
	 * 
	 * @param input
	 *            EditorInput
	 */
	public void setInputProvider(IEditorInput input) {
		setAncestorInputProvider(input);
		setLeftSideInputProvider(input);
		setRighSideInputProvider(input);
	}

	/**
	 * Set the AncestorInput Provider.
	 * 
	 * @param input
	 *            EditorInput
	 */
	private void setAncestorInputProvider(IEditorInput input) {
		if (((EMFCompareInput)input).getAncestor() != null) {
			Notifier selectedItem = ((EMFCompareInput)input).getAncestor();
			TreeViewer ancestorSide = ancestorView.getResourceTreeViewer();
			ancestorSide.setContentProvider(new StructureDiffPaneContentProvider(new ComposedAdapterFactory(
					ComposedAdapterFactory.Descriptor.Registry.INSTANCE)));
			ancestorSide.setLabelProvider(new AdapterFactoryLabelProvider(new ComposedAdapterFactory(
					ComposedAdapterFactory.Descriptor.Registry.INSTANCE)));
			ancestorSide.setInput(Lists.newArrayList(selectedItem));
		}
	}

	/**
	 * Set the LeftSide InputProvider.
	 * 
	 * @param input
	 *            EditorInput
	 */
	private void setLeftSideInputProvider(IEditorInput input) {
		Notifier selectedItem = ((EMFCompareInput)input).getLeft();
		TreeViewer leftSideTreeViewer = leftView.getResourceTreeViewer();
		leftSideTreeViewer.setContentProvider(new StructureDiffPaneContentProvider(
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE)));
		leftSideTreeViewer.setLabelProvider(new AdapterFactoryLabelProvider(new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE)));
		leftSideTreeViewer.setInput(Lists.newArrayList(selectedItem));
		leftView.setResourcePath(((Resource)selectedItem).getURI().toString());
	}

	/**
	 * Set the RightSide InputProvider.
	 * 
	 * @param input
	 *            EditorInput
	 */
	private void setRighSideInputProvider(IEditorInput input) {
		Notifier selectedItem = ((EMFCompareInput)input).getRight();
		TreeViewer rightSideTreeViewer = rightView.getResourceTreeViewer();
		rightSideTreeViewer.setContentProvider(new StructureDiffPaneContentProvider(
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.Impl.INSTANCE)));
		rightSideTreeViewer.setLabelProvider(new AdapterFactoryLabelProvider(new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE)));
		rightSideTreeViewer.setInput(Lists.newArrayList(selectedItem));
		rightView.setResourcePath(((Resource)selectedItem).getURI().toString());
	}

	/**
	 * Synchronise the selected elements in the different TreeViewer. {@inheritDoc}
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		if (event.getSelection() instanceof IStructuredSelection) {
			Object selected = ((IStructuredSelection)event.getSelection()).getFirstElement();
			if (selected instanceof Match) {
				Match match = (Match)selected;
				if (match.getLeft() != null) {
					IStructuredSelection newSelection = new StructuredSelection(match.getLeft());
					leftView.getResourceTreeViewer().setSelection(newSelection);
				}
				if (match.getRight() != null) {
					IStructuredSelection newSelection = new StructuredSelection(match.getRight());
					rightView.getResourceTreeViewer().setSelection(newSelection);
				}
				if (match.getOrigin() != null) {
					IStructuredSelection newSelection = new StructuredSelection(match.getOrigin());
					ancestorView.getResourceTreeViewer().setSelection(newSelection);
				}
				// visualisationDiffToolBar.getCopyLeftTorRight().setEnabled(false);
				// visualisationDiffToolBar.getCopyRightToLeft().setEnabled(false);
			}
			if (selected instanceof Diff) {
				Diff diff = (Diff)selected;
				if (diff.getMatch().getLeft() != null) {
					IStructuredSelection newSelection = new StructuredSelection(diff.getMatch().getLeft());
					leftView.getResourceTreeViewer().setSelection(newSelection);
				}
				if (diff.getMatch().getRight() != null) {
					IStructuredSelection newSelection = new StructuredSelection(diff.getMatch().getRight());
					rightView.getResourceTreeViewer().setSelection(newSelection);
				}
				if (diff.getMatch().getOrigin() != null) {
					IStructuredSelection newSelection = new StructuredSelection(diff.getMatch().getOrigin());
					ancestorView.getResourceTreeViewer().setSelection(newSelection);
				}
				// handleCopyActionVisiblity(diff);
			}
		}
	}

	// /**
	// * Handle the availability Copy(LToR||RToL).
	// *
	// * @param difference
	// * the selected difference from the TreeViewer.
	// */
	// public void handleCopyActionVisiblity(Diff difference) {
	// if (difference.getSource().equals(DifferenceSource.RIGHT)) {
	// updateCopyActions(difference, true);
	// } else if (difference.getSource().equals(DifferenceSource.LEFT)) {
	// updateCopyActions(difference, false);
	// } else {
	// visualisationDiffToolBar.getCopyLeftTorRight().setEnabled(false);
	// visualisationDiffToolBar.getCopyRightToLeft().setEnabled(false);
	// }
	// }
	//
	// /**
	// * Update copy Actins (CopyLeftTorRight|copyRightToLeft).
	// *
	// * @param difference
	// * the difference element
	// * @param isRight
	// * if source is Right so true , else if source is Left so false
	// */
	// public void updateCopyActions(Diff difference, boolean isRight) {
	// if (isRight) {
	// visualisationDiffToolBar.getCopyLeftTorRight().setEnabled(isRight);
	// visualisationDiffToolBar.getCopyRightToLeft().setEnabled(!isRight);
	// visualisationDiffToolBar.getCopyLeftTorRight().setCurrentSelection(difference);
	// } else if (!isRight) {
	// visualisationDiffToolBar.getCopyLeftTorRight().setEnabled(isRight);
	// visualisationDiffToolBar.getCopyRightToLeft().setEnabled(!isRight);
	// visualisationDiffToolBar.getCopyRightToLeft().setCurrentSelection(difference);
	// }
	// }

	@Override
	protected void checkSubclass() {
	}

	public DifferencesView getAncestorView() {
		return ancestorView;
	}

	public DifferencesView getLeftView() {
		return leftView;
	}

	public DifferencesView getRightView() {
		return rightView;
	}

	public VisualisationDiffToolBar getVisualisationDiffToolBar() {
		return visualisationDiffToolBar;
	}

}
