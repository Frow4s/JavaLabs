import java.util.Locale;

public class LocaleManager {
    public static final Locale RU_LOCALE=new Locale("ru");
    public static final Locale EN_LOCALE=new Locale("en");
    public static final Locale NL_LOCALE=new Locale("nl");
    public static final Locale SV_LOCALE=new Locale("sv");

    public static Locale currentLang;

    public static Locale getCurrentLocale(){
        return currentLang;
    }

    public static void setCurrentLocale(Locale currentLang) {
        LocaleManager.currentLang = currentLang;
    }
}
