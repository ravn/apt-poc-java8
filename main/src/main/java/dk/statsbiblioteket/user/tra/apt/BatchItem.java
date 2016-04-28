package dk.statsbiblioteket.user.tra.apt;


import dk.statsbiblioteket.user.tra.model.Id;
import dk.statsbiblioteket.user.tra.model.Item;

import java.util.stream.Stream;

public class BatchItem implements Item {
    public Stream<Item> editionItems() {
        return Stream.empty();  // for now.
    }
}
