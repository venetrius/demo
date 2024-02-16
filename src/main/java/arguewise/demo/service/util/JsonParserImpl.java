package arguewise.demo.service.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class JsonParserImpl implements IJsonParser {
    @Autowired
    private Gson gson;

    public <T> T parseJson(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    @Override
    public JsonObject parse(String jsonString) {
        JsonParser parser = new JsonParser();
        return parser.parse(jsonString).getAsJsonObject();
    }


}
