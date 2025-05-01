package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.PostDao;
import org.bea.db.entity.PostAggregate;
import org.bea.db.repository.PostAggregateRepository;
import org.bea.dto.PageOfPostsResponse;
import org.bea.dto.PostsAndPageInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindPostHandler {

    private final PostAggregateRepository postAggregateRepository;
    private final PostDao postDao;

    public PostsAndPageInfo findByTags(String search, Integer postSize, Integer pageNumber) {
        var customPostSize = postSize == null ? 10 : postSize;
        var customSearch = search == null ? "" : search;
        var customPageNumber = pageNumber == null ? 0 : pageNumber - 1;
        List<PostAggregate> result = new ArrayList<>();
        long count = 0;
        if (customSearch.isBlank()) {
            result = postAggregateRepository.findAll(customPageNumber * customPostSize, customPostSize);
            count = postDao.getCount();
        } else {
        }
        var pageResult = buildPageOfPosts(count, customPageNumber, customPostSize);
        return new PostsAndPageInfo(result, pageResult);
    }

    private PageOfPostsResponse buildPageOfPosts(long count, int customPageNumber, int customPostSize) {
        return PageOfPostsResponse.builder()
                .count(count)
                .postSize(customPostSize)
                .pageNumber(customPageNumber + 1)
                .build();
    }
}
