package org.bea.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bea.db.dao.LikeDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeActionHandler {

    private final LikeDao likeDao;

    @Getter
    public enum LikeActionType {
        INCREMENT(true),
        DECREMENT(false);

        private final boolean value;
        private static final Map<Boolean, LikeActionType> BY_VALUE = new HashMap<>();

        static {
            for (LikeActionType type : values()) {
                BY_VALUE.put(type.value, type);
            }
        }

        LikeActionType(boolean v) {
            this.value = v;
        }

        public static LikeActionType fromBoolean(boolean value) {
            return BY_VALUE.get(value);
        }
    }

    public void handle(UUID postId, boolean isIncrement) {
        var action = LikeActionType.fromBoolean(isIncrement);
        likeDao.incDec(postId, action);
    }
}
