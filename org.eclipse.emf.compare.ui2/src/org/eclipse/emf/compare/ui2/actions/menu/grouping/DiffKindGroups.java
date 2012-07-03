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

import com.google.common.collect.Lists;

import java.util.List;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;

/**
 * Group All Difference By Kind ;
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class DiffKindGroups {

	/**
	 * List of Difference Groups.
	 */
	protected List<DifferenceGroupElement> differenceGroups;

	/**
	 * Constructor.
	 */
	public DiffKindGroups() {
		differenceGroups = Lists.newArrayList();
	}

	/**
	 * Add a difference Group Element to the list of groups
	 * 
	 * @param name
	 *            {@link String} Name of the group
	 * @param kind
	 *            {@link DifferenceKind} Kkind of the group
	 */
	public void addDifferenceGroupElement(String name, DifferenceKind kind) {
		DifferenceGroupElement diffGroupElement = new DifferenceGroupElement(name);
		diffGroupElement.setGroupKind(kind);
		differenceGroups.add(diffGroupElement);
	}

	/**
	 * Add a Difference to it's convinient Group
	 * 
	 * @param diff
	 *            {@link Diff} Difference Element
	 */
	public void addDifferenceElement(Diff diff) {
		for (DifferenceGroupElement groupElement : differenceGroups) {
			if (groupElement.getGroupKind().equals(diff.getKind())) {
				groupElement.addElement(diff);
			}
		}
	}

	/**
	 * Return All the Difference Groups
	 * 
	 * @return a List<{@link DifferenceGroupElement}>
	 */
	public List<DifferenceGroupElement> getElements() {
		return differenceGroups;
	}

	/**
	 * Return the Difference of a given Difference Group Element
	 * 
	 * @param diffGroup
	 *            the Given Difference Group {@link DifferenceGroupElement}
	 * @return a List<{@link Diff}>
	 */
	public List<Diff> getGroupElementsChildren(DifferenceGroupElement diffGroup) {
		int index = differenceGroups.indexOf(diffGroup);
		return differenceGroups.get(index).getElements();
	}

	/**
	 * Check out if the kind of difference already exists or not
	 * 
	 * @param difference
	 *            {@link Diff}
	 * @return true if the kind exists and false if not
	 */
	public boolean containKind(Diff difference) {
		boolean result = false;
		for (DifferenceGroupElement groupElement : differenceGroups) {
			if (groupElement.getGroupKind().equals(difference.getKind())) {
				result = true;
				break;
			} else {
				result = false;
				continue;
			}
		}
		return result;
	}

	public class DifferenceGroupElement {
		/**
		 * The Group name.
		 */
		String groupName;

		/**
		 * The of all the differences inside this group.
		 */
		DifferenceKind groupKind;

		/**
		 * List of differences in the group.
		 */
		List<Diff> differences;

		DifferenceGroupElement(String name) {
			groupName = name;
			differences = Lists.newArrayList();

		}

		public List<Diff> getElements() {
			return differences;
		}

		private boolean addElement(Diff element) {
			return differences.add(element);
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String name) {
			groupName = name;
		}

		/**
		 * Get The Kind of the Group
		 * 
		 * @return {@link DifferenceKind}
		 */
		private DifferenceKind getGroupKind() {
			return groupKind;
		}

		/**
		 * Set the Kind of the Group.
		 * 
		 * @param kind
		 */
		private void setGroupKind(DifferenceKind kind) {
			groupKind = kind;
		}

		@Override
		public String toString() {
			return groupName;
		}
	}
}
