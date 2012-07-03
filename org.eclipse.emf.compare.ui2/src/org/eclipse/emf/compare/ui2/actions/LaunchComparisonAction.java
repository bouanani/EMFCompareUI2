/*******************************************************************************
 * Copyright (c) 2000, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Obeo - Tweaking for EMF Compare
 *******************************************************************************/
package org.eclipse.emf.compare.ui2.actions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.ui2.input.EMFCompareInput;
import org.eclipse.emf.compare.ui2.utilities.EMFCompareConfiguration;
import org.eclipse.emf.compare.ui2.utilities.Utilities;
import org.eclipse.emf.compare.ui2.viewers.EMFCompareEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * An instance of this class will launch the comparison action, during this process an EMFCompareInput will be
 * created in order to be the input of the different elements contained by The EMF Compare Editor which will
 * represent the UI of our pluguin.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
@SuppressWarnings("restriction")
public class LaunchComparisonAction implements IWorkbenchWindowActionDelegate {

	/**
	 * Active Workbench Window.
	 */
	private IWorkbenchWindow activeWindow;

	/**
	 * Active Workbench Page.
	 */
	private IWorkbenchPage page;

	/**
	 * list of Notifier.
	 */
	private List<Notifier> selectionAsList = Lists.newArrayList();

	/**
	 * This represents the IEditorInput of the Editor.
	 */
	private EMFCompareInput input;

	/**
	 * A table of Selected resources from the workbench.
	 */
	private IResource[] selectedResource;

	/**
	 * Active Selection in the workbench.
	 */
	private ISelection activeSelection;

	/**
	 * Lunch the comparison process and the EMF Compare UI.
	 * 
	 * @see org.eclipse.ui.IActionDelegate.run
	 * @param action
	 *            Selecting the compare with Menu.
	 */

	@SuppressWarnings("restriction")
	public void run(IAction action) {
		if (selectionAsList.size() >= 2) {
			final Notifier notifier1 = selectionAsList.get(0);
			final Notifier notifier2 = selectionAsList.get(1);
			final Notifier notifier3;

			if (selectionAsList.size() > 2) {
				notifier3 = selectionAsList.get(2);
				input = new EMFCompareInput(notifier1, notifier2, notifier3);
				// A Dialog to select the ancestor between three Element.
				boolean ok = input.selectAncestorDialog(activeSelection, PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell());
				input.setComparisonElement(EMFCompare.compare(input.getLeft(), input.getRight(), input
						.getAncestor()));
				EMFCompareConfiguration.fThreeWay = true;
				Utilities.registerInput(input);
			} else {
				notifier3 = null;
				input = new EMFCompareInput(notifier1, notifier2, notifier3);
				Comparison comparison = EMFCompare.compare(notifier1, notifier2);
				input.setComparisonElement(comparison);
				EMFCompareConfiguration.fThreeWay = false;
				Utilities.registerInput(input);
			}
			if (activeWindow != null) {
				page = activeWindow.getActivePage();
			} else {
				page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			}
			try {
				if (input != null) {
					page.openEditor(input, EMFCompareEditor.ID);
				} else {
					System.out.println("INPUT IS NULL ");
					// FIXME ! we should create an Exception when the the editor encoutner a problem
				}
			} catch (PartInitException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Notifies this action delegate that the selection in the workbench has changed.
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(IAction, ISelection).
	 * @param action
	 *            the action proxy that handles presentation portion of the action
	 * @param selection
	 *            the current selection, or <code>null</code> if there is no selection.
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		selectionAsList.clear();
		selectedResource = Utilities.getResources(selection);
		activeSelection = selection;
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			@SuppressWarnings("unchecked")
			final Iterator<Object> selectionIterator = structuredSelection.iterator();
			while (selectionIterator.hasNext()) {
				final Object next = selectionIterator.next();
				if (next instanceof EObject) {
					selectionAsList.add((EObject)next);
				} else if (next instanceof IFile) {
					final ResourceSet resourceSet = new ResourceSetImpl();
					try {
						final Resource resource = resourceSet.createResource(URI.createURI(((IFile)next)
								.getLocation().toString()), ((IFile)next).getContentDescription()
								.getContentType().getId());
						resource.load(((IFile)next).getContents(), Maps.newHashMap());
						selectionAsList.add(resource);

					} catch (IOException e) {
						e.printStackTrace();
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * To Initialize the window workbench.
	 * 
	 * @param window
	 *            the workbench window
	 */
	public void init(IWorkbenchWindow window) {
	}

	/**
	 * Disposes this action delegate. The implementor should unhook any references to itself so that garbage
	 * collection can occur.
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}
}
