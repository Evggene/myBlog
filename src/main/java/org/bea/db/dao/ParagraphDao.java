package org.bea.db.dao;

import org.bea.db.entity.ParagraphEntity;

import java.util.UUID;

public interface ParagraphDao {

    void delete(UUID id, String byColumn);
    ParagraphEntity setIdAndInsert(ParagraphEntity paragraphEntity);
}
