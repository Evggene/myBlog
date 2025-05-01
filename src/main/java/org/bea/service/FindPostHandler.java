package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.db.entity.PostAggregate;
import org.bea.db.repository.PostAggregateRepository;
import org.bea.dto.PageOfPostsResponse;
import org.bea.dto.PostsAndPageInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindPostHandler {

    private final PostAggregateRepository postAggregateRepository;
    private final PostDao postDao;
    private final TagDao tagDao;

    public PostsAndPageInfo findByTags(String searchRaw, Integer postSizeRaw, Integer pageNumberRaw) {
        var postSize = postSizeRaw == null ? 10 : postSizeRaw;
        var search = searchRaw == null ? "" : searchRaw;
        var pageNumber = pageNumberRaw == null ? 0 : pageNumberRaw - 1;

        List<PostAggregate> result = new ArrayList<>();
        long count = 0;
        if (search.isBlank()) {
            result = postAggregateRepository.findAll(pageNumber * postSize, postSize);
            count = postDao.getCount();
        } else {
            var tagsName = Arrays.stream(search.split(" "))
                    .distinct()
                    .toList();
            var tags = tagsName.stream()
                    .map(tagDao::findByName)
                    .toList();
            result = postAggregateRepository.findByTag(tags, pageNumber * postSize, postSize);
            count = tagDao.countPostsByTags(tags);
        }
        var pageResult = buildPageOfPosts(count, pageNumber, postSize, searchRaw);
        return new PostsAndPageInfo(result, pageResult);
    }

    private PageOfPostsResponse buildPageOfPosts(long count, int customPageNumber, int customPostSize, String searchRaw) {
        return PageOfPostsResponse.builder()
                .count(count)
                .postSize(customPostSize)
                .pageNumber(customPageNumber + 1)
                .search(searchRaw)
                .build();
    }
}
