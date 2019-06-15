import java.util.Locale;

public class Lang {
    private String code;
    private String name;
    private int index;
    private Locale locale;

    public Lang(int index,String code, String name, Locale locale){
        this.code=code;
        this.name=name;
        this.locale=locale;
        this.index=index;
    }

    public int getIndex() {
        return index;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
