package arguewise.demo.service.util;

public interface IJsonParser {
    public <T> T parseJson(String jsonString, Class<T> clazz);

}
