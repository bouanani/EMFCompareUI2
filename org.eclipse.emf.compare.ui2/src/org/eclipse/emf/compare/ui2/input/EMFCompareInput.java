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
package org.eclipse.emf.compare.ui2.input;

import com.google.common.collect.Maps;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.ui2.utilities.Utilities;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPersistableElement;

/**
 * Interface for objects used as input to a two-way or three-way compare viewer. It defines API for accessing
 * the three sides for the compare, and a name and image which is used when displaying the three way input in
 * the UI, for example, in a title bar.
 * <p>
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class EMFCompareInput extends AbstractEMFCompareInput {

	/**
	 * true if the Three-way compare is activated otherwise it's false.
	 */
	private boolean fThreeWay;

	/**
	 * The first selected Notifier (Resources in the Workbench).
	 */
	private Notifier left;

	/**
	 * The second selected Notifier (Resources in the Workbench).
	 */
	private Notifier right;

	/**
	 * The notifier's ancestor.
	 */
	private Notifier ancestor;

	/**
	 * {@link org.eclipse.emf.compare.Comparison<em>  Matched Resources</em>}.
	 */
	private Comparison comparison;

	/**
	 * The Resource Object that represents the ancestor.
	 */
	private IResource fAncestor;

	/**
	 * The Resource Object that represents the left notifier.
	 */
	private IResource fLeft;

	/**
	 * The Resource Object that represents the right notifier.
	 */
	private IResource fRight;

	/**
	 * EMFCompareInput constructor.
	 * 
	 * @param left
	 *            the first selected element
	 * @param right
	 *            the second selected element
	 * @param ancestor
	 *            the ancestor element (In case of three-way comparison)
	 */
	public EMFCompareInput(Notifier left, Notifier right, Notifier ancestor) {
		super();
		this.left = left;
		this.right = right;
		this.ancestor = ancestor;
	}

	public String getName() {
		return "EMF Compare 2.0";
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return null;
	}

	/**
	 * Not Yet Implemented.
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 * @param adapter
	 *            adapter
	 * @return object null
	 */
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public Notifier getOrigin() {
		return ancestor;
	}

	@Override
	public Notifier getLeft() {
		return left;
	}

	@Override
	public Notifier getRight() {
		return right;
	}

	@Override
	public void copy(boolean leftToRight) {
		// FIXME !
	}

	public Notifier getAncestor() {
		return ancestor;
	}

	public void setAncestor(Notifier ancestor) {
		this.ancestor = ancestor;
	}

	public void setLeft(Notifier left) {
		this.left = left;
	}

	public void setRight(Notifier right) {
		this.right = right;
	}

	/**
	 * Selecting the Ancestor from the 3 selected resource in case of three-way activation.
	 * 
	 * @param s
	 *            the ActiveSelection
	 * @param shell
	 *            Workbench window's shell.
	 * @return true if the Three-Way selection is activated, otherwise it return false
	 */
	// FIXME ! Selecting ancestor
	public boolean selectAncestorDialog(ISelection s, Shell shell) {
		// if (!showSelectAncestorDialog)
		// return showCompareWithOtherResourceDialog(shell, s);
		IResource[] selection = Utilities.getResources(s);
		fThreeWay = selection.length == 3;
		if (fThreeWay) {
			SelectAncestorDialog dialog = new SelectAncestorDialog(shell, selection);
			int code = dialog.open();
			if (code != Window.OK) {
				return false;
			}
			fAncestor = dialog.getAncestorResource();
			this.ancestor = getNotifierFromIResource(fAncestor);
			fLeft = dialog.getLeftResource();
			this.left = getNotifierFromIResource(fLeft);
			fRight = dialog.getRightResource();
			this.right = getNotifierFromIResource(fRight);
		}
		return true;
	}

	/**
	 * Get the Notifier object from the IResource Object.
	 * 
	 * @param iResource
	 *            the selected IResource
	 * @return Notifier a Notifier to represent the Selected Resource
	 */
	public Notifier getNotifierFromIResource(IResource iResource) {
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		Resource resource = null;
		try {
			resource = resourceSet.createResource(URI.createURI(((IFile)iResource).getLocation().toString()),
					((IFile)iResource).getContentDescription().getContentType().getId());
			resource.load(((IFile)iResource).getContents(), Maps.newHashMap());
			return resource;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return resource;
	}

	public Comparison getComparison() {
		return comparison;
	}

	/**
	 * Set the comparison elements.
	 * 
	 * @param comparisons
	 *            comparisons comparisonElements
	 */
	public void setComparisonElement(Comparison comparisons) {
		this.comparison = comparisons;
	}

}
