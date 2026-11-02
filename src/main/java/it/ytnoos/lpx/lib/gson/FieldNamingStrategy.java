package it.ytnoos.lpx.lib.gson;

import java.lang.reflect.Field;

public interface FieldNamingStrategy {

    String translateName(Field field);
}
