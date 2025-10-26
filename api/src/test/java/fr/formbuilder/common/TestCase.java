package fr.formbuilder.common;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.util.Collection;
import java.util.function.Consumer;

@Value
@Builder
public class TestCase<T> {
    @NonNull
    String name;
    @NonNull
    T data;
    @NonNull
    Consumer<T> assertFn;

    void run() {
        try {
            assertFn.accept(data);
        } catch (AssertionError e) {
            throw new AssertionError(String.format("[ %s ] %s", name, e.getMessage()), e);
        }
    }

    public static <T> void assertAll(String suiteName, Collection<? extends TestCase<T>> cases) {
        Assertions.assertAll(
            suiteName,
            cases.stream().map(testCase -> (Executable) testCase::run)
        );
    }
}
