package it.ytnoos.lpx.compat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ReflectionNames {

    private static final String SEPARATOR = "\b";

    private static final Map<String, String> LEGACY_NAMES = new HashMap<>();

    private static final Map<String, String> CLASS_NAMES = new HashMap<>();

    private static final Map<String, String> ORIGINAL_CLASSES = new HashMap<>();

    private static final Map<String, String> METHOD_NAMES = new HashMap<>();

    private static final Map<String, String> FIELD_NAMES = new HashMap<>();

    private static final Map<String, String> CACHE = new ConcurrentHashMap<>();

    static {
        try {
            read(
                    "legacy-names.tsv",
                    parts ->
                            LEGACY_NAMES.put(
                                    parts[0],
                                    new String(
                                            Base64.getDecoder().decode(parts[1]),
                                            StandardCharsets.UTF_8)));
            read(
                    "relocations.tsv",
                    parts -> {
                        if (parts[0].equals("C")) {
                            CLASS_NAMES.put(parts[1], parts[2]);
                            ORIGINAL_CLASSES.put(parts[2], parts[1]);
                        } else if (parts[0].equals("M")) {
                            String key = parts[1];
                            METHOD_NAMES.put(key.substring(0, key.indexOf(')') + 1), parts[2]);
                        } else if (parts[0].equals("F")) {
                            FIELD_NAMES.put(parts[1], parts[2]);
                        }
                    });
        } catch (IOException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private ReflectionNames() {}

    private interface LineReader {

        void accept(String[] parts);
    }

    private static void read(String name, LineReader consumer) throws IOException {
        InputStream stream = ReflectionNames.class.getResourceAsStream(name);
        if (stream == null) throw new IOException("Missing reflection compatibility data: " + name);
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null; )
                consumer.accept(line.split("\t", -1));
        }
    }

    private static byte[] digest(String input) {
        try {
            return MessageDigest.getInstance("SHA-512")
                    .digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException(exception);
        }
    }

    private static String legacy(String input) {
        String result =
                CACHE.computeIfAbsent(
                        input,
                        key -> {
                            String encoded =
                                    LEGACY_NAMES.get(new BigInteger(digest(key)).toString(36));
                            if (encoded == null) return "";
                            byte[] mask = digest(key + "SHA-512");
                            char[] decoded = encoded.toCharArray();
                            for (int index = 0; index < decoded.length; index++)
                                decoded[index] ^= (char) mask[index % mask.length];
                            return new String(decoded);
                        });
        return result.isEmpty() ? null : result;
    }

    public static String className(String name) {
        if (name == null || name.isEmpty()) return name;
        String internal = name.replace('.', '/');
        int dimensions = internal.lastIndexOf('[') + 1;
        String element = internal.substring(dimensions);
        if (dimensions > 0 && element.length() == 1) return name;
        boolean descriptor = element.startsWith("L") && element.endsWith(";");
        if (descriptor) element = element.substring(1, element.length() - 1);
        String translated = legacy(element + SEPARATOR);
        if (translated == null) translated = element;
        translated = CLASS_NAMES.getOrDefault(translated, translated);
        String result =
                internal.substring(0, dimensions)
                        + (descriptor ? "L" : "")
                        + translated
                        + (descriptor ? ";" : "");
        return name.indexOf('.') >= 0 ? result.replace('/', '.') : result;
    }

    private static String originalType(Class<?> type) {
        if (type.isArray()) return "[" + descriptor(type.getComponentType());
        if (type.isPrimitive()) return descriptor(type);
        String internal = type.getName().replace('.', '/');
        return ORIGINAL_CLASSES.getOrDefault(internal, internal);
    }

    private static String descriptor(Class<?> type) {
        if (type.isArray()) return originalType(type);
        if (!type.isPrimitive()) return "L" + originalType(type) + ";";
        if (type == void.class) return "V";
        if (type == boolean.class) return "Z";
        if (type == byte.class) return "B";
        if (type == char.class) return "C";
        if (type == short.class) return "S";
        if (type == int.class) return "I";
        if (type == long.class) return "J";
        if (type == float.class) return "F";
        return "D";
    }

    public static String methodName(String name, Class<?> owner, Class<?>[] parameters) {
        if (owner == null) return name;
        StringBuilder suffix = new StringBuilder(SEPARATOR).append(name).append(SEPARATOR);
        StringBuilder signature = new StringBuilder("(");
        for (Class<?> parameter : parameters) {
            suffix.append(originalType(parameter)).append(SEPARATOR);
            signature.append(descriptor(parameter));
        }
        signature.append(')');
        String result = resolveMethod(owner, name, suffix.toString(), signature.toString());
        return result == null ? name : result;
    }

    private static String resolveMethod(
            Class<?> owner, String name, String suffix, String signature) {
        String originalOwner = originalType(owner);
        String target = legacy(originalOwner + suffix);
        if (target == null) target = name;
        String renamed = METHOD_NAMES.get(originalOwner + "." + target + signature);
        if (renamed != null) return renamed;
        if (!target.equals(name)) return target;
        if (owner.getSuperclass() != null) {
            String result = resolveMethod(owner.getSuperclass(), name, suffix, signature);
            if (result != null) return result;
        }
        for (Class<?> parent : owner.getInterfaces()) {
            String result = resolveMethod(parent, name, suffix, signature);
            if (result != null) return result;
        }
        return null;
    }

    public static String fieldName(Class<?> owner, String name) {
        if (owner == null) return name;
        String originalOwner = originalType(owner);
        String target = legacy(originalOwner + SEPARATOR + name);
        if (target == null) target = name;
        String renamed = FIELD_NAMES.get(originalOwner + "." + target);
        if (renamed != null) return renamed;
        if (!target.equals(name)) return target;
        if (owner.getSuperclass() != null) {
            String inherited = fieldName(owner.getSuperclass(), name);
            if (!inherited.equals(name)) return inherited;
        }
        for (Class<?> parent : owner.getInterfaces()) {
            String inherited = fieldName(parent, name);
            if (!inherited.equals(name)) return inherited;
        }
        return name;
    }
}
