package dk.statsbiblioteket.user.tra.apt;


import dk.statsbiblioteket.user.tra.model.Item;

import java.nio.file.Path;

public class FileItem implements Item {

    protected final Path path;

    public FileItem(Path path) {
        this.path = path;
    }

}
