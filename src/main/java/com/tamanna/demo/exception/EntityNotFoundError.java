package com.tamanna.demo.exception;

public class EntityNotFoundError extends Exception {

    public EntityNotFoundError(final Class entityClass, final Integer id) {
        super("No instance for " + entityClass.getSimpleName() + " with identifier " + id + " was found");
    }
}