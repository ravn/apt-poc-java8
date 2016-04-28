package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;

public class EditionTask implements Function<EditionItem, Long> {

    @Override
    public Long apply(EditionItem item) {
        try {
            return Files.list(item.getPath()).count();
        } catch (Exception e) {
            throw new RuntimeException(item.getPath().toString(), e);
        }
    }
}
