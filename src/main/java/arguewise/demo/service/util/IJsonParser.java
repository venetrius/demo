package arguewise.demo.service.util;

import com.google.gson.JsonObject;

public interface IJsonParser {
    public <T> T parseJson(String jsonString, Class<T> clazz);

    // TODO refactor, don't reference implementation details (JsonObject)
    public JsonObject parse(String jsonString);
}
