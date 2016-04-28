package dk.statsbiblioteket.user.tra.apt;


import dk.statsbiblioteket.user.tra.model.Item;

import java.nio.file.Path;

public class EditionItem implements Item {
    private final Path path;

    public EditionItem(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
