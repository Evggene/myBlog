package org.bea.db.dao;

import org.bea.db.entity.CommentEntity;
import org.bea.db.entity.ParagraphEntity;

import java.util.List;
import java.util.UUID;

public interface ParagraphDao {

    void delete(UUID id, String byColumn);
    ParagraphEntity setIdAndInsert(ParagraphEntity paragraphEntity);
    ParagraphEntity findById(UUID id);
    List<ParagraphEntity> findListById(UUID id);
}
