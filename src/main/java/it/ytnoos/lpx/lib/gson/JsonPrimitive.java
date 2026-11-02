package it.ytnoos.lpx.lib.gson;

import it.ytnoos.lpx.lib.gson.internal.$Gson$Preconditions;
import it.ytnoos.lpx.lib.gson.internal.LazilyParsedNumber;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class JsonPrimitive extends JsonElement {

    private static final Class<?>[] PRIMITIVE_TYPES =
            new Class[] {
                int.class,
                long.class,
                short.class,
                float.class,
                double.class,
                byte.class,
                boolean.class,
                char.class,
                Integer.class,
                Long.class,
                Short.class,
                Float.class,
                Double.class,
                Byte.class,
                Boolean.class,
                Character.class
            };

    private Object value;

    public JsonPrimitive(Boolean matches) {
        this.setValue(matches);
    }

    public JsonPrimitive(Number number) {
        this.setValue(number);
    }

    public JsonPrimitive(String text) {
        this.setValue(text);
    }

    public JsonPrimitive(Character character) {
        this.setValue(character);
    }

    JsonPrimitive(Object result) {
        this.setValue(result);
    }

    JsonPrimitive deepCopy() {
        return this;
    }

    void setValue(Object value) {
        if (value instanceof Character) {
            char value2 = (Character) value;
            this.value = String.valueOf(value2);
        } else {
            $Gson$Preconditions.checkArgument(
                    value instanceof Number || isPrimitiveOrString(value));
            this.value = value;
        }
    }

    public boolean isBoolean() {
        return this.value instanceof Boolean;
    }

    @Override
    Boolean getAsBooleanWrapper() {
        return (Boolean) this.value;
    }

    @Override
    public boolean getAsBoolean() {
        return this.isBoolean()
                ? this.getAsBooleanWrapper()
                : Boolean.parseBoolean(this.getAsString());
    }

    public boolean isNumber() {
        return this.value instanceof Number;
    }

    @Override
    public Number getAsNumber() {
        return this.value instanceof String
                ? new LazilyParsedNumber((String) this.value)
                : (Number) this.value;
    }

    public boolean isString() {
        return this.value instanceof String;
    }

    @Override
    public String getAsString() {
        if (this.isNumber()) {
            return this.getAsNumber().toString();
        } else {
            return this.isBoolean() ? this.getAsBooleanWrapper().toString() : (String) this.value;
        }
    }

    @Override
    public double getAsDouble() {
        return this.isNumber()
                ? this.getAsNumber().doubleValue()
                : Double.parseDouble(this.getAsString());
    }

    @Override
    public BigDecimal getAsBigDecimal() {
        return this.value instanceof BigDecimal
                ? (BigDecimal) this.value
                : new BigDecimal(this.value.toString());
    }

    @Override
    public BigInteger getAsBigInteger() {
        return this.value instanceof BigInteger
                ? (BigInteger) this.value
                : new BigInteger(this.value.toString());
    }

    @Override
    public float getAsFloat() {
        return this.isNumber()
                ? this.getAsNumber().floatValue()
                : Float.parseFloat(this.getAsString());
    }

    @Override
    public long getAsLong() {
        return this.isNumber()
                ? this.getAsNumber().longValue()
                : Long.parseLong(this.getAsString());
    }

    @Override
    public short getAsShort() {
        return this.isNumber()
                ? this.getAsNumber().shortValue()
                : Short.parseShort(this.getAsString());
    }

    @Override
    public int getAsInt() {
        return this.isNumber()
                ? this.getAsNumber().intValue()
                : Integer.parseInt(this.getAsString());
    }

    @Override
    public byte getAsByte() {
        return this.isNumber()
                ? this.getAsNumber().byteValue()
                : Byte.parseByte(this.getAsString());
    }

    @Override
    public char getAsCharacter() {
        return this.getAsString().charAt(0);
    }

    private static boolean isPrimitiveOrString(Object result) {
        if (result instanceof String) {
            return true;
        }
        Class<?> classValue = result.getClass();
        Class[] classs = PRIMITIVE_TYPES;
        int value = PRIMITIVE_TYPES.length;
        for (int index = 0; index < value; index++) {
            if (classs[index].isAssignableFrom(classValue)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (this.value == null) {
            return 31;
        } else if (isIntegral(this)) {
            long value2 = this.getAsNumber().longValue();
            return (int) (value2 ^ value2 >>> 32);
        } else if (this.value instanceof Number) {
            long value3 = Double.doubleToLongBits(this.getAsNumber().doubleValue());
            return (int) (value3 ^ value3 >>> 32);
        } else {
            return this.value.hashCode();
        }
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (result != null && this.getClass() == result.getClass()) {
            JsonPrimitive jsonPrimitive = (JsonPrimitive) result;
            if (this.value == null) {
                return jsonPrimitive.value == null;
            } else if (isIntegral(this) && isIntegral(jsonPrimitive)) {
                return this.getAsNumber().longValue() == jsonPrimitive.getAsNumber().longValue();
            } else if (this.value instanceof Number && jsonPrimitive.value instanceof Number) {
                double value2 = this.getAsNumber().doubleValue();
                double value3 = jsonPrimitive.getAsNumber().doubleValue();
                return value2 == value3 || Double.isNaN(value2) && Double.isNaN(value3);
            } else {
                return this.value.equals(jsonPrimitive.value);
            }
        } else {
            return false;
        }
    }

    private static boolean isIntegral(JsonPrimitive jsonPrimitive) {
        Number number;
        return !(jsonPrimitive.value instanceof Number)
                ? false
                : (number = (Number) jsonPrimitive.value) instanceof BigInteger
                        || number instanceof Long
                        || number instanceof Integer
                        || number instanceof Short
                        || number instanceof Byte;
    }
}
