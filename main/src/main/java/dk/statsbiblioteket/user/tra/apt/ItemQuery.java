package dk.statsbiblioteket.user.tra.apt;

import java.nio.file.Path;
import java.util.function.Predicate;

public interface ItemQuery extends Predicate<Path> {
    @Override
    boolean test(Path path);
}
