package org.bea.db.dao;

import org.bea.model.Comment;
import org.bea.model.Paragraph;

import java.util.UUID;

public interface ParagraphDao {

    void update(Paragraph comment);
    void delete(UUID id);
    Paragraph setIdAndInsert(Paragraph paragraph);
}
