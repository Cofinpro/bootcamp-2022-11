package com.cofinprobootcamp.backend.role;

import com.cofinprobootcamp.backend.enums.UserNamespaceRights;
import com.cofinprobootcamp.backend.enums.UserOperationRights;
import com.cofinprobootcamp.backend.enums.UserScopeRights;
import com.cofinprobootcamp.backend.role.dto.RoleCreateInDTO;

import java.util.*;

public class RoleDirector {
    public static Role CreateInDTOToEntity(RoleCreateInDTO inDTO) {
        return Role.builder()
                .name(inDTO.shortName())
                .descriptiveName(inDTO.descriptiveName())
                .userRights(flattenMapOfRights(convertStringMapToEnumMap(inDTO.userRights())))
                .build();
    }

    public static Role roleFromSpecification(String shortname, String descriptiveName, Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> userRights) {
        return Role.builder()
                .name(shortname)
                .descriptiveName(descriptiveName)
                .userRights(flattenMapOfRights(userRights))
                .build();
    }

    public static Map<String, Map<String, String>> roleUserRightsToDTOMap(Role role) {
        Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> enumMap = buildStructuredMapOfRightsFromList(role.getUserRights());
        Map<String, Map<String, String>> stringMap = new HashMap<>();
        for (var namespace : enumMap.entrySet()) {
            Map<String, String> innerMap = new HashMap<>();
            for (var operationsAndScope : namespace.getValue().entrySet()) {
                innerMap.put(operationsAndScope.getKey().toString(), operationsAndScope.getValue().toString());
            }
            stringMap.put(namespace.getKey().toNamespaceString(), innerMap);
        }
        return stringMap;
    }

    public static Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> convertStringMapToEnumMap(Map<String, Map<String, String>> stringMap) {
        Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> enumMap = new EnumMap<>(UserNamespaceRights.class);
        if (stringMap == null) {
            return enumMap;
        }
        for (var namespace : stringMap.entrySet()) {
            Map<UserOperationRights, UserScopeRights> innerMap = new EnumMap<>(UserOperationRights.class);
            for (var operationsAndScope : namespace.getValue().entrySet()) {
                innerMap.put(
                        UserOperationRights.fromString(operationsAndScope.getKey()),
                        UserScopeRights.fromString(operationsAndScope.getValue())
                );
            }
            if (namespace.getValue() != null) {
                enumMap.put(UserNamespaceRights.fromString(namespace.getKey()), innerMap);
            }
        }
        return enumMap;
    }

    public static List<UserRight> flattenMapOfRights(Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> mapOfRights) {
        List<UserRight> listOfRights = new LinkedList<>();
        for (var namespaceMapping : mapOfRights.entrySet()) {
            for (var operationsAndScope : namespaceMapping.getValue().entrySet()) {
                UserRight userRight = new UserRight();
                userRight.setNamespace(namespaceMapping.getKey());
                userRight.setOperation(operationsAndScope.getKey());
                userRight.setScope(operationsAndScope.getValue());
                listOfRights.add(userRight);
            }
        }
        return listOfRights;
    }

    public static Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> buildStructuredMapOfRightsFromList(List<UserRight> userRightsList) {
        Map<UserNamespaceRights, Map<UserOperationRights, UserScopeRights>> mapOfRights = new EnumMap<>(UserNamespaceRights.class);
        for (var namespaceRight : UserNamespaceRights.values()) {
            List<UserRight> filteredList = userRightsList.stream()
                    .filter(userRight -> namespaceRight.equals(userRight.getNamespace()))
                    .toList();
            if (filteredList.isEmpty()) {
                mapOfRights.put(namespaceRight, generateDefaults());
            } else {
                mapOfRights.put(namespaceRight, buildInnerMap(filteredList));
            }
        }
        return mapOfRights;
    }

    public static Map<UserOperationRights, UserScopeRights> buildInnerMap(List<UserRight> filteredList) {
        Map<UserOperationRights, UserScopeRights> innerMap = generateDefaults();
        for (var right : filteredList) {
            innerMap.put(right.getOperation(), right.getScope());
        }
        return innerMap;
    }

    public static Map<UserOperationRights, UserScopeRights> generateDefaults() {
        Map<UserOperationRights, UserScopeRights> defaultMap = new EnumMap<>(UserOperationRights.class);
        for (String constant : UserOperationRights.getAllDefinedValuesAsString()) {
            defaultMap.put(UserOperationRights.fromString(constant), UserScopeRights.NONE);
        }
        return defaultMap;
    }
}
