package cz.cvut.userservice.model.specification;

public record SearchCriteria(
        String key,
        SearchOperation operation,
        Object value
) {}
