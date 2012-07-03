package org.eclipse.emf.compare.ui2.sync;

import com.google.common.collect.Lists;

import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class SelectionEventBroker {
	private List<ISelectionChangedListener> listeners = Lists.newArrayList();

	private void fireSelectionChangedEvent(SelectionChangedEvent event) {
		for (ISelectionChangedListener listener : listeners) {
			listener.selectionChanged(event);
		}
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	public void addSelectionProvider(ISelectionProvider provider) {
		provider.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				fireSelectionChangedEvent(event);
			}
		});
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}
}
