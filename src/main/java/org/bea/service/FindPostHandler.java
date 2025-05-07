package org.bea.service;

import lombok.RequiredArgsConstructor;
import org.bea.db.dao.PostDao;
import org.bea.db.dao.TagDao;
import org.bea.db.dao.TagsToPostDao;
import org.bea.db.entity.TagEntity;
import org.bea.db.repository.PostRepository;
import org.bea.dto.PageOfPostsResponse;
import org.bea.dto.PostsAndPageInfo;
import org.bea.model.Post;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindPostHandler {

    private final PostRepository postRepository;
    private final PostDao postDao;
    private final TagDao tagDao;
    private final TagsToPostDao tagsToPostDao;

    public PostsAndPageInfo findPreviewModeByTags(String searchRaw, Integer postSizeRaw, Integer pageNumberRaw) {
        var postSize = postSizeRaw == null ? 10 : postSizeRaw;
        var search = searchRaw == null ? "" : searchRaw;
        var pageNumber = pageNumberRaw == null ? 0 : pageNumberRaw - 1;

        List<Post> result;
        long count;
        if (search.isBlank()) {
            result = postRepository.findAllPreviewMode(pageNumber * postSize, postSize);
            count = postDao.getCount();
        } else {
            var tags = handleTagsRaw(search);
            result = postRepository.findByTagPreviewMode(tags, pageNumber * postSize, postSize);
            count = tagsToPostDao.countPostsByTags(tags);
        }
        var pageResult = buildPageOfPosts(count, pageNumber, postSize, searchRaw);
        return new PostsAndPageInfo(result, pageResult);
    }

    private List<TagEntity> handleTagsRaw(String search) {
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
