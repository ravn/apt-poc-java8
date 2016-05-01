package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Event;
import dk.statsbiblioteket.user.tra.model.Id;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;


public class FileBackedMemoryRepositoryTest {

    String root = "/Users/ravn/Downloads/llo/standard pakker til repo/avis/Fjerritslev avis/B400027132187-RT1";

    @Test
    public void test1() throws Exception {
        FileBackedMemoryRepository<TestFileItem, TestEvent> repository;
        Path rootPath = Paths.get(this.root);
        BiFunction<Path, String, TestFileItem> pathFunction = (p, id) -> new TestFileItem(p, id);
        repository = new FileBackedMemoryRepository(rootPath, pathFunction);

        Assert.assertEquals("", repository.itemStream().collect(toList()));
    }

    private class TestFileItem extends FileItem {
        private final String id;

        public TestFileItem(Path path, String id) {
            super(path);
            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }
    }



    private class TestEvent implements Event {}
}