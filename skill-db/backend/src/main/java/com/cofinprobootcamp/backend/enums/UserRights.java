package com.cofinprobootcamp.backend.enums;

//TODO
/**
 * Define all user rights
 * Discuss in Sprint 2.0
 */
public enum UserRights {
    /**
     * User is allowed to create profiles for themselves
     */
    SELF_CREATE,
    /**
     * User is allowed to delete profiles for themselves
     */
    SELF_DELETE,
    /**
     * User is allowed to create profiles for all users
     */
    ANY_CREATE,
    /**
     * User is allowed to delete profiles for all users
     */
    ANY_DELETE
}
