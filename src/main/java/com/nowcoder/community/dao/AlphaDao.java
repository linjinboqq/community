package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;
@Repository
public class AlphaDao implements Aldao {
    @Override
    public String select() {
        System.out.println("h");
        return "hiber";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
