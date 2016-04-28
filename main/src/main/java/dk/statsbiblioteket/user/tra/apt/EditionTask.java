package dk.statsbiblioteket.user.tra.apt;

import dk.statsbiblioteket.user.tra.model.Item;

import java.util.function.Function;

public class EditionTask implements Function<Item, Boolean> {
    @Override
    public Boolean apply(Item item) {
        throw new RuntimeException("not yet");
    }
}
