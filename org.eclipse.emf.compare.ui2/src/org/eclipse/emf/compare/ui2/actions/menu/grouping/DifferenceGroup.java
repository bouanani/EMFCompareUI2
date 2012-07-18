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
package org.eclipse.emf.compare.ui2.actions.menu.grouping;

import java.util.List;

import org.eclipse.emf.compare.Diff;

/**
 * An interface used by EMF Compare to group differences.
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public interface DifferenceGroup {

	/**
	 * Return all the differences.
	 * 
	 * @return
	 */
	List<Diff> getDifferences();
}
