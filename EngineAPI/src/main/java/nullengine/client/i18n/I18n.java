package nullengine.client.i18n;

public final class I18n {

    public final static String translation(String key) {
        return LocaleManager.INSTANCE.translation(key);
    }
}
