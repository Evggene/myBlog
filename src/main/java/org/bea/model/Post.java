package org.bea.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bea.db.entity.CommentEntity;
import org.bea.db.entity.ParagraphEntity;
import org.bea.db.entity.TagEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private UUID id;
    private String title;
    private String imagePath;
    private String textPreview;
    @Builder.Default
    private List<ParagraphEntity> textParts = new ArrayList<>();
    private int likesCount;
    @Builder.Default
    private List<TagEntity> tags = new ArrayList<>();
    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();
}
