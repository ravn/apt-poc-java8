package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Repository;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;


public class FileBackedMemoryRepositoryTest {

    // String root = "/Users/ravn/Downloads/llo/standard pakker til repo/avis/Fjerritslev avis/B400027132187-RT1";
    String root = "/home/tra/ownCloud/2016-02-29/llo/standard pakker til repo/avis/Fjerritslev avis/B400027132187-RT1";

    @Test
    public void test1() throws Exception {
        Path rootPath = Paths.get(this.root);

        FileBackedMemoryRepository<TestFileItem, TestEvent> repository = null;
        repository = new FileBackedMemoryRepository(rootPath, (p, id, repo) -> new TestFileItem((Path) p, (String) id, (Repository) repo));

        //Assert.assertEquals("", repository.itemStream().collect(toList()));
    }


    private class TestFileItem extends FileItem {
        private final Path path;
        private final String id;
        private final Repository repository;

        public TestFileItem(Path path, String id, Repository repository) {
            super(path);
            this.path = path;
            this.id = id;
            this.repository = repository;
        }

        @Override
        public String toString() {
            return id;
        }
    }


    private class TestEvent implements Event {
    }
}