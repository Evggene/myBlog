package org.bea.presenter;

public interface Presenter<E, D> {

    D toView(E entity);
}
