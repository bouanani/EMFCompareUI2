package org.eclipse.emf.compare.ui2.sync;

import org.eclipse.emf.compare.ui2.viewers.DifferencesView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TreeItem;

public class EventHandler {
	/**
	 * The Ancestor View.
	 */
	private DifferencesView ancestor;

	/**
	 * The Left View.
	 */
	private DifferencesView leftSide;

	/**
	 * The Right View.
	 */
	private DifferencesView rightSide;

	public EventHandler(DifferencesView ancestor, DifferencesView left, DifferencesView right) {
		this.ancestor = ancestor;
		this.leftSide = left;
		this.rightSide = right;
		/*
		 * (non-java-doc)ScrollBar Synchrnizer
		 */
		ScrollBar ancestorScrollBar = ancestor.getResourceTreeViewer().getTree().getHorizontalBar();
		ScrollBar leftScrollBar = leftSide.getResourceTreeViewer().getTree().getHorizontalBar();
		ScrollBar rightScrollBar = rightSide.getResourceTreeViewer().getTree().getHorizontalBar();
		scrollBarSynchronizer(ancestorScrollBar, leftScrollBar, rightScrollBar);
		tabFolderSynchronizer(ancestor, left, right);
	}

	/**
	 * @param ancestorScrollBar
	 *            Ancestor Horizontal ScrollBar
	 * @param leftScrollBar
	 *            LeftSide Horizontal ScrollBar
	 * @param rightScrollBar
	 *            RighSide Horizontal ScrollBar
	 */
	private void scrollBarSynchronizer(final ScrollBar ancestorScrollBar, final ScrollBar leftScrollBar,
			final ScrollBar rightScrollBar) {

		ancestorScrollBar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				leftScrollBar.setSelection(ancestorScrollBar.getSelection());
				rightScrollBar.setSelection(ancestorScrollBar.getSelection());
			}
		});

		leftScrollBar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ancestorScrollBar.setSelection(leftScrollBar.getSelection());
				rightScrollBar.setSelection(leftScrollBar.getSelection());
			}
		});

		rightScrollBar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ancestorScrollBar.setSelection(rightScrollBar.getSelection());
				leftScrollBar.setSelection(rightScrollBar.getSelection());
			}
		});
	}

	/**
	 * TabFolder Synchronizer.
	 * 
	 * @param ancestor
	 *            The Ancestor Side
	 * @param leftSide
	 *            The Left Side
	 * @param rightSide
	 *            The Right Side
	 */
	private void tabFolderSynchronizer(final DifferencesView ancestor, final DifferencesView leftSide,
			final DifferencesView rightSide) {
		/*
		 * (not-java doc) Tab Selection Listener for the left right
		 */
		rightSide.getDiffViewTab().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TabItem item = (TabItem)e.item;
				ancestor.getDiffViewTab().setSelection(item.getParent().getSelectionIndex());
				leftSide.getDiffViewTab().setSelection(item.getParent().getSelectionIndex());
				// Synchronizing the properties Tab
				if (rightSide.getDiffViewTab().getSelectionIndex() == 1) {
					propertiesInputProviderSynchronizer(rightSide);
					propertiesInputProviderSynchronizer(leftSide);
					propertiesInputProviderSynchronizer(ancestor);
				}
			}
		});

		/*
		 * (not-java doc) Tab Selection Listener for the left view
		 */
		leftSide.getDiffViewTab().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TabItem item = (TabItem)e.item;
				ancestor.getDiffViewTab().setSelection(item.getParent().getSelectionIndex());
				rightSide.getDiffViewTab().setSelection(item.getParent().getSelectionIndex());
				if (leftSide.getDiffViewTab().getSelectionIndex() == 1) {
					propertiesInputProviderSynchronizer(rightSide);
					propertiesInputProviderSynchronizer(leftSide);
					propertiesInputProviderSynchronizer(ancestor);
				}
			}
		});

		/*
		 * (not-java doc) Tab Selection Listener for the ancestor vi
		 */
		ancestor.getDiffViewTab().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TabItem item = (TabItem)e.item;
				leftSide.getDiffViewTab().setSelection(item.getParent().getSelectionIndex());
				rightSide.getDiffViewTab().setSelection(item.getParent().getSelectionIndex());
				if (ancestor.getDiffViewTab().getSelectionIndex() == 1) {
					propertiesInputProviderSynchronizer(rightSide);
					propertiesInputProviderSynchronizer(leftSide);
					propertiesInputProviderSynchronizer(ancestor);
				}
			}
		});
	}

	private void propertiesInputProviderSynchronizer(DifferencesView diffView) {
		TreeItem[] treeItems = diffView.getResourceTreeViewer().getTree().getSelection();
		for (TreeItem treeItem : treeItems) {
			diffView.setTableInputProvider(treeItem.getData());
		}

	}

}
