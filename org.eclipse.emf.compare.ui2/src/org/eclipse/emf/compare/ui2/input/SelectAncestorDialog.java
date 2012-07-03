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

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * A personalized MessageDialog on which in case of three-way compare, this class asks the user which resource
 * to use as the ancestor. This class is retrieved from ResourceCompare.class
 * </p>
 * 
 * @see org.eclipse.compare.internal.SelectAncestorDialog
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class SelectAncestorDialog extends MessageDialog {

	/**
	 * List of selected resources.
	 */
	private IResource[] theResources;

	/**
	 * The ancestor resources.
	 */
	private IResource ancestorResource;

	/**
	 * The left resources.
	 */
	private IResource leftResource;

	/**
	 * The right resources.
	 */
	private IResource rightResource;

	/**
	 * lists of buttons.
	 */
	private Button[] buttons;

	/**
	 * A listener to detect the chosen action.
	 */
	private SelectionListener selectionListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			Button selectedButton = (Button)e.widget;
			if (!selectedButton.getSelection()) {
				return;
			}
			for (int i = 0; i < 3; i++) {
				if (selectedButton == buttons[i]) {
					pickAncestor(i);
				}
			}
		}
	};

	/**
	 * A message dialog that permit the user to select the Ancestor.
	 * 
	 * @param parentShell
	 *            Workbench window's shell.
	 * @param theResources
	 *            list of the selected resources
	 */

	@SuppressWarnings("restriction")
	public SelectAncestorDialog(Shell parentShell, IResource[] theResources) {
		super(parentShell, "Select Common Ancestor", null,
				"Which resource would you like to use as the common ancestor in the three-way compare?",
				MessageDialog.QUESTION, new String[] {IDialogConstants.OK_LABEL,
						IDialogConstants.CANCEL_LABEL, }, 0);
		this.theResources = theResources;
	}

	@Override
	@SuppressWarnings("restriction")
	protected Control createCustomArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		buttons = new Button[3];
		for (int i = 0; i < 3; i++) {
			buttons[i] = new Button(composite, SWT.RADIO);
			buttons[i].addSelectionListener(selectionListener);
			buttons[i].setText(NLS.bind("''{0}''", theResources[i].getFullPath().toPortableString()));
			buttons[i].setFont(parent.getFont());
			// set initial state
			buttons[i].setSelection(i == 0);
		}
		pickAncestor(0);
		return composite;
	}

	/**
	 * Pick the ancestor.
	 * 
	 * @param index
	 *            reference of the selected Notifier in the list
	 */
	private void pickAncestor(int index) {
		ancestorResource = theResources[index];
		if (index == 0) {
			leftResource = theResources[1];
		} else {
			leftResource = theResources[0];
		}
		if (index == 2) {
			rightResource = theResources[1];
		} else {
			rightResource = theResources[2];
		}
	}

	public IResource getAncestorResource() {
		return ancestorResource;
	}

	public void setAncestorResource(IResource ancestorResource) {
		this.ancestorResource = ancestorResource;
	}

	public IResource getLeftResource() {
		return leftResource;
	}

	public void setLeftResource(IResource leftResource) {
		this.leftResource = leftResource;
	}

	public IResource getRightResource() {
		return rightResource;
	}

	public void setRightResource(IResource rightResource) {
		this.rightResource = rightResource;
	}

}
