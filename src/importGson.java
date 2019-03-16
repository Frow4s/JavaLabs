import Objects.Gryadka;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class importGson {
    public static Gryadka importJson(String path){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Gryadka Gryadka= gson.fromJson(path, Gryadka.class);
        return Gryadka;
    }
}
