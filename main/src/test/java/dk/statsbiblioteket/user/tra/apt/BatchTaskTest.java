package dk.statsbiblioteket.user.tra.apt;


import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class BatchTaskTest {
    @Test
    public void llobatchTest() {
        Function<ItemQuery, Stream<BatchItem>> f = new LLOBatchItemStreamFactory(new FileSystemRepository(Paths.get("/Users/ravn/Downloads/llo/standard pakker til repo/avis/Fjerritslev avis/B400027132187-RT1")));
        ItemQuery t = (s1, s2) -> true;

        EditionTask editionTask = new EditionTask();
        Stream<BatchItem> batchItemStream = f.apply(t);


        ArrayList expected = new ArrayList(asList(asList(46, 46), asList(34, 46)));
        List<List<Long>> collect = batchItemStream.map(b -> b.editionItems().map(editionTask).collect(Collectors.toList())).collect(Collectors.toList());
        //Assert.assertThat(collect, is(expected));
    }
}
