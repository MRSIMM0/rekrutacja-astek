package src.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static <T> T getRequestToClass(InputStream request, Class<T> type)  {
            Reader r = new InputStreamReader(request);
            Gson gson  = new GsonBuilder().create();
            return gson.fromJson(r,type);

    }

}
