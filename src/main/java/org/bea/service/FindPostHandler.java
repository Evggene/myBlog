package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.model.PostAggregate;
import org.bea.db.repository.PostAggregateRepository;
import org.bea.dto.PageOfPostsResponse;
import org.bea.dto.PostsAndPageInfo;
import org.bea.db.entity.Tag;
import org.springframework.stereotype.Service;

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

        List<PostAggregate> result;
        long count;
        if (search.isBlank()) {
            result = postAggregateRepository.findAllPreviewMode(pageNumber * postSize, postSize);
            count = postDao.getCount();
        } else {
            var tags = handleTagsRaw(search);
            result = postAggregateRepository.findByTagPreviewMode(tags, pageNumber * postSize, postSize);
            count = tagDao.countPostsByTags(tags);
        }
        var pageResult = buildPageOfPosts(count, pageNumber, postSize, searchRaw);
        return new PostsAndPageInfo(result, pageResult);
    }

    private List<Tag> handleTagsRaw(String search) {
        var tagsName = Arrays.stream(search.split(" "))
                .distinct()
                .toList();
        return tagsName.stream()
                .map(tagDao::findByName)
                .toList();
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
