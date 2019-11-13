package space.harbour.java.hw7;

public interface Jsonable {
    boolean writeJsonToFile(String filename);
    Jsonable readFromJsonFile(String filename);
}


