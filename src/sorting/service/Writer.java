package sorting.service;

import java.util.List;

public interface Writer {
     void write(List<String> entities, String fileName);

     void writeBuilder(List<StringBuilder> entities, String fileName);
}
