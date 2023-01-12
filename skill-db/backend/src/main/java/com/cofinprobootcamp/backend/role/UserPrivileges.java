package com.cofinprobootcamp.backend.role;

import java.util.Arrays;
import java.util.Set;

public enum UserPrivileges {
    /*
     * Endpoint "/api/v1/roles"
     */
    ROLES_GET_BY_ID$SELF(
            "Der Nutzer ist dazu berechtigt, Detailinformationen zu seiner eigenen Rolle einzusehen."
    ),
    ROLES_GET_BY_ID$ANY(
            "Der Nutzer ist dazu berechtigt, Detailinformationen zu jeder Rolle über dessen Kennung einzusehen."
    ),
    ROLES_GET_ALL(
            "Der Nutzer ist dazu berechtigt, eine Übersicht aller bestehenden Rollen einzusehen."
    ),
    ROLES_BY_ID_GET_USERS_ALL(
            "Der Nutzer ist dazu berechtigt, eine Übersicht aller Nutzernamen einzusehen, die eine bestimmte Rolle innehaben."
    ),

    /*
     * Endpoint "/api/v1/profiles"
     */
    PROFILES_POST_NEW$SELF(
            "Der Nutzer ist dazu berechtigt, ein neues Profil für sich selbst anzulegen."
    ),
    PROFILES_POST_NEW$ANY(
            "Der Nutzer ist dazu berechtigt, für jeden Nutzer neue Profile anzulegen (d.h. für sich selbst und auch für andere)."
    ),
    PROFILES_PATCH_BY_ID$SELF(
            "Der Nutzer ist dazu berechtigt, sein eigenes Profil zu bearbeiten."
    ),
    PROFILES_DELETE_BY_ID$SELF(
            "Der Nutzer ist dazu berechtigt, sein eigenes Profil zu löschen."
    ),
    PROFILES_GET_BY_ID$SELF(
            "Der Nutzer ist dazu berechtigt, sein eigenes Profil einzusehen."
    ),
    PROFILES_PATCH_BY_ID$ANY(
            "Der Nutzer ist dazu berechtigt, jedes beliebige Profil über dessen Kennung zu bearbeiten."
    ),
    PROFILES_DELETE_BY_ID$ANY(
            "Der Nutzer ist dazu berechtigt, jedes beliebige Profil über dessen Kennung zu löschen."
    ),
    PROFILES_GET_BY_ID$ANY(
            "Der Nutzer ist dazu berechtigt, jedes beliebige Profil über dessen Kennung einzusehen."
    ),
    PROFILES_GET_ALL(
            "Der Nutzer ist dazu berechtigt, eine Übersicht mit minimalen Informationen über alle Profile einzusehen."
    ),
    PROFILES_EXPERTISES_GET_ALL(
            "Der Nutzer ist dazu berechtigt, sich eine Übersicht aller bestehenden Primärkompetenzen anzeigen zu lassen."
    ),
    PROFILES_EXPORT_GET_ALL(
            "Der Nutzer ist dazu berechtigt, alle Profile mit Detailinformationen zu exportieren."
    ),
    PROFILES_IMPORT_POST_NEW(
            "Der Nutzer ist dazu berechtigt, neue Profile per CSV-Import anzulegen."
    ),

    /*
     * Endpoint "/api/v1/skills"
     */
    SKILLS_GET_ALL(
            "Der Nutzer ist dazu berechtigt, sich eine Übersicht aller bisher hinterlegten Fertigkeiten anzeigen zu lassen."
    ),

    /*
     * Endpoint "/api/v1/job-titles"
     */
    JOB_TITLES_GET_ALL(
            "Der Nutzer ist dazu berechtigt, sich eine Übersicht aller bestehenden Jobtitel anzeigen zu lassen."
    ),
    JOB_TITLES_POST_NEW(
            "Der Nutzer ist dazu berechtigt, einen neuen Jobtitel anzulegen."
    ),

    /*
     * Endpoint "/api/v1/users"
     */
    USERS_POST_NEW$SELF(
            "Der Nutzer ist dazu berechtigt, einen neuen Nutzeraccount mit seiner eigenen Mail-Adresse mit der Rolle 'Nutzer' anzulegen."
    ),
    USERS_POST_NEW$ANY(
            "Der Nutzer ist dazu berechtigt, einen neuen Nutzeraccount mit beliebiger Rolle anzulegen."
    ),
    USERS_DELETE_BY_ID$SELF(
            "Der Nutzer ist dazu berechtigt, seinen eigenen Nutzeraccount über dessen Kennung zu löschen."
    ),
    USERS_DELETE_BY_ID$ANY(
            "Der Nutzer ist dazu berechtigt, einen beliebigen Nutzeraccount über dessen Kennung zu löschen."
    ),
    USERS_GET_BY_ID$SELF(
            "Der Nutzer ist dazu berechtigt, seinen eigenen Nutzeraccount über dessen Kennung einzusehen."
    ),
    USERS_GET_BY_ID$ANY(
            "Der Nutzer ist dazu berechtigt, einen beliebigen Nutzeraccount über dessen Kennung einzusehen."
    ),
    USERS_BY_ID_GET_PROFILE$SELF(
            "Der Nutzer ist dazu berechtigt, die Kennung seines eigenen Profils abzurufen."
    ),
    USERS_BY_ID_GET_PROFILE$ANY(
            "Der Nutzer ist dazu berechtigt, die Kennung des Profils von jedem Nutzer abzurufen."
    ),
    USERS_BY_ID_GET_PROFILE_EXISTS$SELF(
            "Der Nutzer ist dazu berechtigt, zu überprüfen, ob er selbst bereits über ein Profil verfügt."
    ),
    USERS_BY_ID_GET_PROFILE_EXISTS$ANY(
            "Der Nutzer ist dazu berechtigt, für jeden Nutzer zu überprüfen, ob dieser bereits über ein Profil verfügt."
    ),
    USERS_GET_ALL(
            "Der Nutzer ist dazu berechtigt, sich eine Übersicht aller bestehenden Nutzer anzeigen zu lassen."
    ),
    USERS_BY_ID_PATCH_ROLE_BY_ID(
            "Der Nutzer ist dazu berechtigt, die Rolle aller bestehenden Nutzer zu ändern."
    ),
    /*
     * Images endpoint
     */
    IMAGES_GET_BY_ID(
            "Der Nutzer ist dazu berechtigt, alle Bilder einzusehen."
    ),
    IMAGES_DELETE_BY_ID$SELF(
            "Der Nutzer ist dazu berichtigt, sein eigenes Profilbild zu löschen."
    ),

    IMAGES_DELETE_BY_ID$ANY(
            "Der Nutzer ist dazu berechtigt, alle Profilbilder zu löschen."
    ),

    /*
     * Fallback
     */
    UNDEFINED("undefined");

    /*
     * Zwischengespeichertes Array, da RolesEnum.values() in public Methoden benötigt wird
     * und sonst bei jedem Call neu konstruiert werden müsste.
     */
    private static final UserPrivileges[] values;

    /*
     * Statische Initialisierung von values über interne Enum Methode values().
     * Das nach außen gegebene Array enthält den UNDEFINED Wert nicht.
     */
    static {
        UserPrivileges[] tmp = UserPrivileges.values();
        values = new UserPrivileges[tmp.length - 1];
        System.arraycopy(tmp, 0, values, 0, tmp.length - 1);
    }

    private final String privilegeDetailsDescription;

    UserPrivileges(String privilegeDetailsDescription) {
        this.privilegeDetailsDescription = privilegeDetailsDescription;
    }

    public String getPrivilegeDetailsDescription() {
        return this.privilegeDetailsDescription;
    }

    public static Set<UserPrivileges> getAllValidUserPrivileges() {
        return Set.of(values);
    }

    public static UserPrivileges fromIdentifier(String identifier) {
        if (identifier == null || identifier.isBlank()) {
            return UserPrivileges.UNDEFINED;
        }
        return Arrays.stream(values)
                .filter(privileges -> privileges.name().equals(identifier))
                .findFirst()
                .orElse(UserPrivileges.UNDEFINED);
    }
}
