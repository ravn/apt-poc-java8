package dk.statsbiblioteket.user.tra.apt;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BatchTask implements Function<BatchItem, String> {
    @Override
    public String apply(BatchItem batchItem) {
        EditionTask editionTask = new EditionTask();
        List<Long> result = batchItem.editionItems().map(editionTask).collect(Collectors.toList());
        return result.toString();
    }
}
