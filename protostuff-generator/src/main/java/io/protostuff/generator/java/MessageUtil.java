package io.protostuff.generator.java;

import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.Oneof;
import io.protostuff.generator.Formatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Custom message properties for java code generator.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class MessageUtil {

    private MessageUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static boolean hasFields(Message message) {
        return !message.getFields().isEmpty();
    }

    /**
     * Returns a list of bit fields used for field presence checks.
     */
    public static List<String> bitFieldNames(Message message) {
        int fieldCount = message.getFieldCount();
        if (fieldCount == 0) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        int n = (fieldCount - 1) / 32 + 1;
        for (int i = 0; i < n; i++) {
            result.add("__bitField" + i);
        }
        return result;
    }

    public static String getOneofEnumClassName(Oneof oneof) {
        String name = oneof.getName();
        return Formatter.toPascalCase(name) + "Case";
    }

    /**
     * Returns a "not set" name for one-of enum constant.
     */
    public static String getOneofNotSetConstantName(Oneof oneof) {
        String name = oneof.getName();
        String underscored = Formatter.toUnderscoreCase(name);
        return Formatter.toUpperCase(underscored) + "_NOT_SET";
    }

    public static String getOneofCaseGetterName(Oneof oneof) {
        String name = oneof.getName();
        return "get" + Formatter.toPascalCase(name) + "Case";
    }

    public static String getOneofCaseCleanerName(Oneof oneof) {
        String name = oneof.getName();
        return "clear" + Formatter.toPascalCase(name);
    }

    public static String getOneofFieldName(Oneof oneof) {
        String name = oneof.getName();
        return Formatter.toCamelCase(name) + "__";
    }

    public static String getOneofCaseFieldName(Oneof oneof) {
        String name = oneof.getName();
        return Formatter.toCamelCase(name) + "Case__";
    }
}
