package dk.statsbiblioteket.user.tra.apt;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 */
public interface ItemSelectionPredicate extends Predicate<Stream<Item>> {
}
