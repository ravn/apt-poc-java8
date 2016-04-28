package dk.statsbiblioteket.user.tra.apt;


import java.nio.file.Path;

public class FileSystemRepository {

    public FileSystemRepository(Path root) {
        this.root = root;
    }

    public Path getRoot() {
        return root;
    }

    protected Path root;
}
