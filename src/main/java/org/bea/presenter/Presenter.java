package org.bea.presenter;

import org.bea.dto.PostRequest;
import org.bea.model.Post;

public interface Presenter<E, D> {

    D toView(E entity);
}
