package dk.statsbiblioteket.user.tra.apt;


import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Function;
import java.util.stream.Stream;

public class LLOBatchItemStreamFactory implements Function<ItemQuery, Stream<BatchItem>> {

    public LLOBatchItemStreamFactory(FileSystemRepository repository) {
        this.repository = repository;
    }

    FileSystemRepository repository;

    @Override
    public Stream<BatchItem> apply(ItemQuery itemQuery) {
        try {
            return getBatchItemStream(itemQuery);
        } catch (IOException e) {
            throw new RuntimeException("apply()", e);
        }
    }

    private Stream<BatchItem> getBatchItemStream(ItemQuery itemQuery) throws IOException {
        return Files.list(repository.getRoot())
                .filter(p -> Files.isDirectory(p))
                .filter(p -> p.getFileName().toString().matches("^\\d+-\\d+$"))
            //    .filter(itemQuery)
                .peek(System.err::println)
                .map(BatchItem::new);
    }
}
