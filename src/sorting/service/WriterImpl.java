package sorting.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WriterImpl implements Writer {
    @Override
    public void write(List<String> entities, String fileName) {
        try{
            Files.write(Paths.get(fileName), entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeBuilder(List<StringBuilder> entities, String fileName) {
        try{
            Files.write(Paths.get(fileName), entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
