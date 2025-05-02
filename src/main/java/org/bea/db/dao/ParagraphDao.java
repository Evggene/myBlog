package org.bea.db.dao;

import org.bea.db.entity.Paragraph;

import java.util.UUID;

public interface ParagraphDao {

    void update(Paragraph comment);
    void delete(UUID id, String byColumn);
    Paragraph setIdAndInsert(Paragraph paragraph);
}
