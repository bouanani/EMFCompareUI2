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
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;

/**
 * Group All Difference By Kind ;
 * 
 * @author <a href="mailto:maher.bouanani@obeo.fr">Bouanani Maher</a>
 */
public class DiffKindGroups implements DifferenceGroupProvider {

	/**
	 * List of Difference Groups.
	 */
	private Map<DifferenceKind, DifferenceGroupElement> differenceGroups = Maps.newHashMap();

	/**
	 * Add a Difference to it's convinient Group
	 * 
	 * @param diff
	 *            {@link Diff} Difference Element
	 */
	public void add(Diff diff) {
		DifferenceGroupElement differenceElement = differenceGroups.get(diff.getKind());
		if (differenceElement == null) {
			differenceElement = new DifferenceGroupElement();
			differenceGroups.put(diff.getKind(), differenceElement);
		}
		differenceElement.add(diff);
	}

	/**
	 * Add All differences into the Difference Group
	 * 
	 * @param differences
	 */
	public void addAll(Collection<Diff> differences) {
		for (Diff diff : differences) {
			add(diff);
		}
	}

	/**
	 * Return All the Difference Groups
	 * 
	 * @return a List<{@link DifferenceGroupElement}>
	 */
	public Collection<? extends DifferenceGroup> getElements() {
		return differenceGroups.values();
	}

	private class DifferenceGroupElement implements DifferenceGroup {

		private String name;

		/**
		 * List of differences in the group.
		 */
		private List<Diff> differences = Lists.newArrayList();

		/**
		 * Get the list of all differences in the group.
		 * 
		 * @return The list of all differences in the group.
		 */
		public List<Diff> getDifferences() {
			return differences;
		}

		/**
		 * Add diff to this group.
		 * 
		 * @param element
		 */
		private void add(Diff element) {
			if (name == null) {
				name = computeName(element.getKind());
			}
			differences.add(element);
		}

		/**
		 * Compute the name of the group according to the kind of the given diff.
		 * 
		 * @param kind
		 *            {@link DifferenceKind}
		 * @return the name to be used for this group.
		 */
		private String computeName(DifferenceKind kind) {
			final String result;
			switch (kind) {
				case ADD:
					result = "added";
					break;
				case CHANGE:
					result = "changed";
					break;
				case MOVE:
					result = "moved";
					break;
				case DELETE:
					result = "deleted";
					break;
				default:
					result = "others";
			}
			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			if (name == null) {
				return "others";
			} else {
				return name;
			}
		}
	}
}
