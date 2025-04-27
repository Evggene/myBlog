package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.model.Post;
import org.bea.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findAll(int offset) {
        return postRepository.findAll(offset);
    }

    public long getCount() {
        return postRepository.getCount();
    }

}
