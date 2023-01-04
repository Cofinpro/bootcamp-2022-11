package com.cofinprobootcamp.backend.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
class ExpertisesTest {

    @Test
    void toFullNameString() {
    }

    public static Stream<Arguments> fromFullNameStringSource() {
            return Stream.of(Arguments.of("Management",Expertises.MGMT),
                    Arguments.of("Fach",Expertises.SPEC),
                    Arguments.of("Technologie",Expertises.TECH),
                    Arguments.of("Lennart",Expertises.UNDEFINED));
    }

    @ParameterizedTest
    @MethodSource("fromFullNameStringSource")
    void fromFullNameString(String fullName, Expertises expertises) {
        assertThat(Expertises.fromFullNameString(fullName)).isEqualTo(expertises);
    }
}
