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

import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.ui2.actions.menu.filtering.DifferenceFilter;
import org.eclipse.emf.compare.ui2.actions.menu.grouping.DifferenceGroupProvider;
import org.eclipse.emf.compare.ui2.input.EMFCompareInput;
import org.eclipse.emf.compare.ui2.sync.SelectionEventBroker;
import org.eclipse.emf.compare.ui2.utilities.Utilities;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

/**
 * An instance of this class is a workbench editor that represents the EMF Compare UI.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class EMFCompareEditor extends EditorPart {

	/**
	 * The Editor's ID.
	 */
	public static final String ID = "org.eclipse.emf.compare.ui2.EMFCompareEditor"; //$NON-NLS-1$

	/**
	 * SelectionEvent Broker.
	 */
	private SelectionEventBroker eventBroker = new SelectionEventBroker();

	/**
	 * The StructuralDiffView component.
	 * 
	 * @see StructViewPane
	 */
	private StructuralDiffPane structDifferencePane;

	/**
	 * The difference filter to be applied on our viewers.
	 */
	private final DifferenceFilter differenceFilter = new DifferenceFilter();

	/**
	 * The VisualisationDiffView component.
	 * 
	 * @see DiffViewPane
	 */
	private DiffViewPane diffViewPane;

	/**
	 * Changes when there is a change on the editor ;
	 */
	private boolean isDirty;

	private boolean fIsLeftDirty;

	private boolean fIsRightDirty;

	private Diff currentDifference;

	/**
	 * Constructor.
	 */
	public EMFCompareEditor() {
		super();
		Utilities.registerEditorPart(this);
		fIsLeftDirty = false;
		fIsRightDirty = false;
	}

	/**
	 * Create contents of the editor part.
	 * 
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot be null)
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FormLayout());
		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
		sashForm.setSashWidth(1);
		FormData fdSashForm = new FormData();
		fdSashForm.top = new FormAttachment(0);
		fdSashForm.left = new FormAttachment(0);
		fdSashForm.bottom = new FormAttachment(100);
		fdSashForm.right = new FormAttachment(100);
		sashForm.setLayoutData(fdSashForm);
		createStructDiffPane(sashForm);
		createDifferenceViewPane(sashForm);
		eventBroker.addSelectionProvider(structDifferencePane.getTreeViewer());
		eventBroker.addSelectionChangedListener(diffViewPane);
		eventBroker.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof StructuredSelection) {
					Object selectedElement = ((StructuredSelection)event.getSelection()).getFirstElement();
					if (selectedElement instanceof Diff) {
						currentDifference = (Diff)selectedElement;
					}
				}
			}
		});
		Utilities.registerStructuralView(structDifferencePane);
		sashForm.setWeights(new int[] {1, 3 });
	}

	/**
	 * Creates a StructuralDiffViewPane in order to visualise the sutructural differences between the
	 * elements.
	 * 
	 * @param structDiffContainer
	 *            the container of the Pane.
	 */
	public void createStructDiffPane(SashForm structDiffContainer) {
		structDifferencePane = new StructuralDiffPane(structDiffContainer, this, getComparison());
		differenceFilter.install(structDifferencePane.getTreeViewer());

	}

	/**
	 * Creates a difference View Pane {@linkplain DiffViewPane}
	 * 
	 * @param diffPaneContainer
	 *            the difference view pane container.
	 */
	public void createDifferenceViewPane(SashForm diffPaneContainer) {
		diffViewPane = new DiffViewPane(diffPaneContainer, SWT.NONE);
		diffViewPane.setInputProvider(getEditorInput());
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		fireDirtyPropertyChange(false);
		if (fIsRightDirty) {
			if (((EMFCompareInput)getEditorInput()).getRight() instanceof Resource) {
				Resource rightResource = (Resource)((EMFCompareInput)getEditorInput()).getRight();
				try {
					rightResource.save(Maps.newConcurrentMap());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.setSite(site);
		super.setInput(input);
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	public void fireDirtyPropertyChange(boolean diry) {
		isDirty = diry;
		firePropertyChange(PROP_DIRTY);
	}

	/**
	 * Return the dirty state of the left side of the viewer.
	 * 
	 * @return the dirty state of the left side of the viewer
	 */
	protected boolean isLeftDirty() {
		return fIsLeftDirty;
	}

	/**
	 * Return the dirty state of the right side of the viewer.
	 * 
	 * @return the dirty state of the right side of the viewer
	 */
	protected boolean isRightDirty() {
		return fIsRightDirty;
	}

	/**
	 * Sets the dirty state of the left side of this viewer. If the new value differs from the old all
	 * registered listener are notified with a <code>PropertyChangeEvent</code> with the property name
	 * <code>CompareEditorInput.DIRTY_STATE</code>.
	 * 
	 * @param dirty
	 *            the state of the left side dirty flag
	 */
	public void setLeftDirty(boolean dirty) {
		if (isLeftDirty() != dirty) {
			fIsLeftDirty = dirty;
		}
	}

	/**
	 * Sets the dirty state of the right side of this viewer. If the new value differs from the old all
	 * registered listener are notified with a <code>PropertyChangeEvent</code> with the property name
	 * <code>CompareEditorInput.DIRTY_STATE</code>.
	 * 
	 * @param dirty
	 *            the state of the right side dirty flag
	 */
	public void setRightDirty(boolean dirty) {
		if (isRightDirty() != dirty) {
			fIsRightDirty = dirty;
		}
	}

	// FIXME ! THINK TO ADD IT TO COMPARE CONFIGURATION
	/**
	 * Return the an instance of StructuralDiffView Pane .
	 * 
	 * @return structuralView instance
	 */
	public StructuralDiffPane getStructDiffViewPane() {
		return structDifferencePane;
	}

	// FIXME ! THINK TO ADD IT TO COMPARE CONFIGURATION

	/**
	 * Get an instance of the DifferenceViewPane
	 * 
	 * @return {@link DiffViewPane}
	 */
	public DiffViewPane getDifferenceViewPane() {
		return diffViewPane;
	}

	/**
	 * Group Differences by their kinf of change.
	 * 
	 * @param diff
	 *            The Differences group provider of the
	 */
	public void group(DifferenceGroupProvider diff) {
		structDifferencePane.getTreeViewer().setInput(diff);
		structDifferencePane.getTreeViewer().refresh();
	}

	/**
	 * Returns the comparison displayed by this editor.
	 * 
	 * @return The comparison displayed by this editor.
	 */
	private Comparison getComparison() {
		return ((EMFCompareInput)getEditorInput()).getComparison();
	}

	/**
	 * Get the differenceFilter.
	 * 
	 * @return the differenceFilter
	 */
	public DifferenceFilter getDifferenceFilter() {
		return differenceFilter;
	}

	/**
	 * Selects the next or previous DiffElement as compared to the currently selected one.
	 * 
	 * @param next
	 *            True if we seek the next DiffElement, False for the previous.
	 */
	public void navigate(boolean next) {
		List<Diff> differences = getComparison().getDifferences();
		if (differences.isEmpty()) {
			return;
		}
		Diff found = findNext(next, differences, currentDifference);
		setStructureSelection(found);
		while (currentDifference != found) {
			found = findNext(next, differences, found);
			setStructureSelection(found);
		}
	}

	/**
	 * @param next
	 * @param differences
	 */
	private Diff findNext(boolean next, List<Diff> differences, Diff currentDiff) {
		final Diff diff;
		ListIterator<Diff> iterator = differences.listIterator();
		if (currentDiff == null) {
			diff = iterator.next();
		} else {
			while (iterator.hasNext()) {
				Diff difference = iterator.next();
				if (difference.equals(currentDiff)) {
					break;
				}
			}
			if (next) {
				if (iterator.hasNext()) {
					diff = iterator.next();
				} else {
					diff = differences.get(0);
				}
			} else {
				if (iterator.hasPrevious()) {
					diff = iterator.previous();
				} else {
					diff = differences.get(differences.size() - 1);
				}
			}
		}
		return diff;
	}

	private void setStructureSelection(Diff difference) {
		structDifferencePane.getTreeViewer().setSelection(new StructuredSelection(difference));
	}

}
