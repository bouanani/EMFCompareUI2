package org.eclipse.emf.compare.ui2.actions.menu.filtering;

import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.ui2.utilities.Utilities;
import org.eclipse.emf.compare.ui2.viewers.StructuralDiffPane;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

/**
 * This class represents the filter action, it acts corresponding to the given filter Type.
 * 
 * @author <a href="mailto:bouanani.meher@obeo.fr">Bouanani Maher</a>
 */
public class FilterActionType extends Action {

	/**
	 * The Filter type (ADD, MOVE, REMOVE or CHANGE).
	 */
	private Object fitlerType;

	/**
	 * The Filter that will be applyed by the action.
	 */
	private CompareDiffFilter compareDiffFilter;

	/**
	 * Constructor.
	 * 
	 * @param actionType
	 *            represents the kind of the choosed filter.
	 * @see DifferenceKind
	 */
	public FilterActionType(Object actionType) {
		super(getActionName(actionType), IAction.AS_CHECK_BOX);
		fitlerType = actionType;
	}

	/**
	 * Put a name for the action according to the type of the selected filter.
	 * 
	 * @param filterAction
	 *            is A {@link DifferenceKind}
	 * @return Name of the action.
	 */
	private static String getActionName(Object filterAction) {
		String name = "";
		if (filterAction.equals(DifferenceKind.ADD)) {
			name = "Added Elements";
		} else if (filterAction.equals(DifferenceKind.MOVE)) {
			name = "Moved Elements";
		} else if (filterAction.equals(DifferenceKind.DELETE)) {
			name = "Removed Elements";
		} else if (filterAction.equals(DifferenceKind.CHANGE)) {
			name = "Changed Elements";
		}
		return name;
	}

	@Override
	public void run() {
		compareDiffFilter = ((StructuralDiffPane)Utilities.getStructuralView()).getFilter();
		if (isChecked()) {
			compareDiffFilter.addFilter(fitlerType);
		} else {
			compareDiffFilter.removeFilter(fitlerType);
		}
	}
}
