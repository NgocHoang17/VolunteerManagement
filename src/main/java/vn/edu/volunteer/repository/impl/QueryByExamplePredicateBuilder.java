package vn.edu.volunteer.repository.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.util.ReflectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class QueryByExamplePredicateBuilder {

    public static <T> Predicate getPredicate(Root<T> root, CriteriaBuilder cb, Example<T> example) {
        List<Predicate> predicates = new ArrayList<>();
        T probe = example.getProbe();
        ExampleMatcher matcher = example.getMatcher();

        ReflectionUtils.doWithFields(probe.getClass(), field -> {
            field.setAccessible(true);
            Object value = field.get(probe);
            if (value != null) {
                String propertyName = field.getName();
                
                if (!matcher.isIgnoredPath(propertyName)) {
                    ExampleMatcher.PropertySpecifier specifier = null;
                    try {
                        specifier = matcher.getPropertySpecifiers().getForPath(propertyName);
                    } catch (Exception ignored) {
                        // Property has no specific matcher
                    }
                    
                    predicates.add(getPredicateForProperty(root, cb, propertyName, value, specifier));
                }
            }
        });

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private static <T> Predicate getPredicateForProperty(Root<T> root, CriteriaBuilder cb,
                                                        String propertyName, Object value,
                                                        ExampleMatcher.PropertySpecifier specifier) {
        Path<Object> path = root.get(propertyName);
        
        if (specifier != null) {
            ExampleMatcher.StringMatcher stringMatcher = specifier.getStringMatcher();
            if (stringMatcher != null) {
                switch (stringMatcher) {
                    case STARTING:
                        return cb.like(path.as(String.class), value.toString() + "%");
                    case ENDING:
                        return cb.like(path.as(String.class), "%" + value.toString());
                    case CONTAINING:
                        return cb.like(path.as(String.class), "%" + value.toString() + "%");
                    case EXACT:
                    default:
                        return cb.equal(path, value);
                }
            }
        }
        
        return cb.equal(path, value);
    }
} 