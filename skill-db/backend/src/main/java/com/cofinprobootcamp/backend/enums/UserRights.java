package com.cofinprobootcamp.backend.enums;

//TODO Define all user rights
public enum UserRights {
    /**
     * User is allowed to create profiles for themselves
     */
    SELF_CREATE,
    /**
     * User is allowed to edit profiles for themselves
     */
    SELF_EDIT,
    /**
     * User is allowed to create profiles for all users
     */
    ANY_CREATE,
    /**
     * User is allowed to edit profiles for all users
     */
    ANY_EDIT
}
