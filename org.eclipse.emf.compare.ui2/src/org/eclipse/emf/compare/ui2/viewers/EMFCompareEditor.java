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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.compare.ui2.input.EMFCompareInput;
import org.eclipse.emf.compare.ui2.sync.SelectionEventBroker;
import org.eclipse.emf.compare.ui2.utilities.Utilities;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
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
	private SelectionEventBroker broker = new SelectionEventBroker();

	/**
	 * The Editor's Input.
	 */
	private IEditorInput input;

	/**
	 * The StructuralDiffView component.
	 * 
	 * @see StructViewPane
	 */
	private StructuralDiffPane structuralView;

	/**
	 * The VisualisationDiffView component.
	 * 
	 * @see DiffViewPane
	 */
	private DiffViewPane visualisationDiffView;

	/**
	 * Changes when there is a change on the editor ;
	 */
	private boolean isDirty;

	private boolean fIsLeftDirty;

	private boolean fIsRightDirty;

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
		structuralView = new StructuralDiffPane(sashForm, SWT.NONE);
		Tree tree = structuralView.getStrucDifftreeViewer().getTree();
		FormData structuralFormView = new FormData();
		structuralFormView.top = new FormAttachment(structuralView.getTooBar(), 6);
		structuralFormView.right = new FormAttachment(100);
		structuralFormView.bottom = new FormAttachment(100);
		structuralFormView.left = new FormAttachment(structuralView.getTooBar(), 0, SWT.LEFT);
		tree.setLayoutData(structuralFormView);
		visualisationDiffView = new DiffViewPane(sashForm, SWT.NONE);
		// Set the input provider of the views
		structuralView.setInputProvider(input);
		visualisationDiffView.setInputProvider(input);
		// Register the EMFCompareEditor as selection Listner
		broker.addSelectionProvider(structuralView.getStrucDifftreeViewer());
		broker.addSelectionChangedListener(visualisationDiffView);
		Utilities.registerStructuralView(structuralView);
		sashForm.setWeights(new int[] {1, 3 });
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		fireDirtyPropertyChange(false);
		if (fIsRightDirty) {
			if (((EMFCompareInput)input).getRight() instanceof Resource) {
				Resource rightResource = (Resource)((EMFCompareInput)input).getRight();
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
	public void init(IEditorSite site, IEditorInput inputs) throws PartInitException {
		this.input = inputs;
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

	public IEditorInput getInput() {
		return this.input;
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
	public StructuralDiffPane getStructuralView() {
		return structuralView;
	}

	// FIXME ! THINK TO ADD IT TO COMPARE CONFIGURATION

	/**
	 * Get an instance of the DifferenceViewPane
	 * 
	 * @return {@link DiffViewPane}
	 */
	public DiffViewPane getVisualisationDiffView() {
		return visualisationDiffView;
	}
}
