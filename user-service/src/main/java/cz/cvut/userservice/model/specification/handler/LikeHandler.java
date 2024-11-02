package cz.cvut.userservice.model.specification.handler;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

public class LikeHandler<T> extends Handler<T> {

    @Override
    public Predicate handle(From<T, ?> root, String key, String value, CriteriaBuilder cb) {
        Expression<String> lowerKey = keyToLowerKey(root, key, cb);
        return cb.like(lowerKey, "%" + value + "%");
    }
}