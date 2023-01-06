package com.cofinprobootcamp.backend.role;

public enum UserPrivileges {
    /*
     * Endpoint "/api/v1/roles"
     */
    ROLES_GET_BY_ID_SELF,
    ROLES_GET_BY_ID_ANY,
    ROLES_GET_ALL,

    /*
     * Endpoint "/api/v1/profiles"
     */
    PROFILES_POST_NEW_SELF,
    PROFILES_POST_NEW_ANY,
    PROFILES_PATCH_BY_ID_SELF,
    PROFILES_DELETE_BY_ID_SELF,
    PROFILES_GET_BY_ID_SELF,
    PROFILES_PATCH_BY_ID_ANY,
    PROFILES_DELETE_BY_ID_ANY,
    PROFILES_GET_BY_ID_ANY,
    PROFILES_GET_ALL,
    PROFILES_EXPERTISES_GET_ALL,

    /*
     * Endpoint "/api/v1/skills"
     */
    SKILLS_GET_ALL,

    /*
     * Endpoint "/api/v1/job-titles"
     */
    JOB_TITLES_GET_ALL,
    JOB_TITLES_POST_NEW,

    /*
     * Endpoint "/api/v1/users"
     */
    USERS_POST_NEW,
    USERS_DELETE_BY_ID,
    USERS_GET_BY_ID,
    USERS_GET_ALL;
}
