package it.ytnoos.lpx.check;

import it.ytnoos.lpx.api.check.CheckResult;
import it.ytnoos.lpx.api.check.FlagInfo;
import it.ytnoos.lpx.api.check.options.CheckOptions;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.compat.ReflectionNames;

import java.lang.reflect.Field;
import java.util.List;

public abstract class RecipeCheck<T> extends AbstractCheck<T> {

    private static List<?> values;

    public RecipeCheck(ProtocolPlayer protocolPlayer, CheckOptions checkOptions) {
        super(protocolPlayer, checkOptions);
    }

    @Override
    public CheckResult check(ProtocolPlayer protocolPlayer, T t) {
        if (values == null) {
            return CheckResult.ALLOW;
        }
        int recipeId = this.getRecipeId(t);
        int size2 = values.size();
        if (recipeId >= 0 && recipeId < size2) {
            return CheckResult.ALLOW;
        }
        FlagInfo[] flagInfos = new FlagInfo[] {FlagInfo.of("recipeId", recipeId), null};
        flagInfos[1] = FlagInfo.of("displaysSize", size2);
        this.flagViolation(t, flagInfos);
        return CheckResult.DENY;
    }

    public abstract int getRecipeId(T t);

    static {
        try {
            Object result =
                    Class.forName("net.minecraft.server.MinecraftServer")
                            .getMethod("getServer")
                            .invoke(null);
            Class<?> classValue = result.getClass();
            Class[] classs = new Class[0];
            Class classValue2 = classValue;
            String text =
                    ReflectionNames.methodName((String) "getRecipeManager", classValue2, classs);
            Object result2 = classValue2.getMethod(text, classs).invoke(result);
            classValue = result2.getClass();
            Class classValue3 = classValue;
            String text2 = ReflectionNames.fieldName(classValue3, (String) "allDisplays");
            Field declaredField = classValue3.getDeclaredField(text2);
            declaredField.setAccessible(true);
            values = (List<?>) declaredField.get(result2);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
