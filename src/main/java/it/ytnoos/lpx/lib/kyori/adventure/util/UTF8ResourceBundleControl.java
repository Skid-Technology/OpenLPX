package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

@Deprecated
public final class UTF8ResourceBundleControl extends Control {

    private static final UTF8ResourceBundleControl INSTANCE = new UTF8ResourceBundleControl();

    @Deprecated
    @NotNull
    public static Control get() {
        return INSTANCE;
    }

    @Deprecated
    @NotNull
    public static Control utf8ResourceBundleControl() {
        return INSTANCE;
    }

    @Override
    public ResourceBundle newBundle(
            String text, Locale locale, String text2, ClassLoader classLoader, boolean matches)
            throws IllegalAccessException, InstantiationException, IOException {
        if (text2.equals("java.properties")) {
            String text3 = this.toBundleName(text, locale);
            String text4 = this.toResourceName(text3, "properties");
            InputStream inputStream = null;
            if (matches) {
                URL uRL;
                URLConnection uRLConnection;
                if ((uRL = classLoader.getResource(text4)) != null
                        && (uRLConnection = uRL.openConnection()) != null) {
                    uRLConnection.setUseCaches(false);
                    inputStream = uRLConnection.getInputStream();
                }
            } else {
                inputStream = classLoader.getResourceAsStream(text4);
            }
            if (inputStream != null) {
                InputStreamReader inputStreamReader =
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                PropertyResourceBundle propertyResourceBundle;
                try {
                    propertyResourceBundle = new PropertyResourceBundle(inputStreamReader);
                } catch (Throwable throwable) {
                    try {
                        inputStreamReader.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
                inputStreamReader.close();
                return propertyResourceBundle;
            } else {
                return null;
            }
        } else {
            return super.newBundle(text, locale, text2, classLoader, matches);
        }
    }
}
