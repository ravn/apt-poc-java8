package dk.statsbiblioteket.user.tra.apt;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 *
 */
public class AutonomousPreservationToolTest {
    @Test
    public void simpleRun() throws Exception {
        List<String> in = Arrays.asList("1","2","3");
        Assert.assertEquals(in, in.stream().flatMap(k->Stream.of(k)).collect(Collectors.toList()));
    }

}