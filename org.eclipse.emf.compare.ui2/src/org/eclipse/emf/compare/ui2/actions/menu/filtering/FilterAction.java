package org.eclipse.emf.compare.ui2.actions.menu.filtering;

import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

/**
 * This class represents the filter action, it acts corresponding to the given filter Type.
 * 
 * @author <a href="mailto:bouanani.meher@obeo.fr">Bouanani Maher</a>
 */
public class FilterAction extends Action {

	/**
	 * The Filter type (ADD, MOVE, REMOVE or CHANGE).
	 */
	private DifferenceKind filterType;

	/**
	 * The Filter that will be applyed by the action.
	 */
	private DifferenceFilter differenceFilter;

	/**
	 * Constructor.
	 * 
	 * @param filterType
	 *            represents the kind of the choosed filter.
	 * @see DifferenceKind
	 */
	public FilterAction(String actionName, DifferenceKind filterType, DifferenceFilter differenceFilter) {
		super(actionName, IAction.AS_CHECK_BOX);
		this.filterType = filterType;
		this.differenceFilter = differenceFilter;
	}

	@Override
	public void run() {

		if (isChecked()) {
			differenceFilter.addFilter(filterType);
		} else {
			differenceFilter.removeFilter(filterType);
		}
	}
}
