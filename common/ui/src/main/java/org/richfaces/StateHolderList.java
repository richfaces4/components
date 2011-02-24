/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.richfaces;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.google.common.collect.Lists;

/**
 * @author Nick Belaevski
 * 
 */
public class StateHolderList extends AbstractList<Object> implements StateHolder {

    private boolean tranzient = false;
    
    private ArrayList<Object> backingList = Lists.newArrayListWithCapacity(2);
    
    public Object saveState(FacesContext context) {
        Object[] savedState = new Object[backingList.size()];

        boolean hasNonNullState = false;
        
        for (int i = 0; i < savedState.length; i++) {
            Object state = UIComponentBase.saveAttachedState(context, backingList.get(i));
            savedState[i] = state;
            
            if (state != null) {
                hasNonNullState = true;
            }
        }
        
        if (hasNonNullState) {
            return savedState;
        } else {
            return null;
        }
    }

    public void restoreState(FacesContext context, Object stateObject) {
        if (stateObject != null) {
            Object[] state = (Object[]) stateObject;
            
            backingList.ensureCapacity(state.length);
            
            for (int i = 0; i < state.length; i++) {
                backingList.add(UIComponentBase.restoreAttachedState(context, state[i]));
            }
        }
    }

    public boolean isTransient() {
        return tranzient;
    }

    public void setTransient(boolean newTransientValue) {
        this.tranzient = newTransientValue;
    }
    
    public boolean contains(Object o) {
        return backingList.contains(o);
    }

    public Iterator<Object> iterator() {
        return backingList.iterator();
    }

    public boolean add(Object e) {
        return backingList.add(e);
    }

    public boolean remove(Object o) {
        return backingList.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return backingList.containsAll(c);
    }

    public boolean addAll(Collection<? extends Object> c) {
        return backingList.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Object> c) {
        return backingList.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return backingList.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return backingList.retainAll(c);
    }

    public void clear() {
        backingList.clear();
    }

    public Object set(int index, Object element) {
        return backingList.set(index, element);
    }

    public void add(int index, Object element) {
        backingList.add(index, element);
    }

    public Object remove(int index) {
        return backingList.remove(index);
    }

    public int indexOf(Object o) {
        return backingList.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return backingList.lastIndexOf(o);
    }

    public ListIterator<Object> listIterator() {
        return backingList.listIterator();
    }

    public ListIterator<Object> listIterator(int index) {
        return backingList.listIterator(index);
    }

    public List<Object> subList(int fromIndex, int toIndex) {
        return backingList.subList(fromIndex, toIndex);
    }

    @Override
    public Object get(int index) {
        return backingList.get(index);
    }

    @Override
    public int size() {
        return backingList.size();
    }

}
