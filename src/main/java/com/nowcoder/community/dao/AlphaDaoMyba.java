package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;

@Repository
public class AlphaDaoMyba implements Aldao{

    @Override
    public String select() {
        return "mybaties";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
