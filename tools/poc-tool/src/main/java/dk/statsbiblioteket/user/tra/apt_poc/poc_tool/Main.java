package dk.statsbiblioteket.user.tra.apt_poc.poc_tool;

import dk.statsbiblioteket.medieplatform.autonomous.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

import static dk.statsbiblioteket.medieplatform.autonomous.ConfigConstants.DOMS_PIDGENERATOR_URL;
import static dk.statsbiblioteket.medieplatform.autonomous.ConfigConstants.DOMS_URL;

/**
 *
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world - args = " + Arrays.asList(args));
        //new TreeMap(getenv()).forEach((a, b) -> System.out.println(a + ": " + b));
        //new TreeMap(System.getProperties()).forEach((a, b) -> System.out.println(a + ": " + b));

        EventTrigger.Query query = new EventTrigger.Query();
        //query.getFutureEvents().add("Metadata_Archived");
        query.getPastSuccessfulEvents().add("Data_Received");

        int pageSize = 10;
        String summaLocation = "http://localhost:58608/newspapr/sbsolr/";

        ItemFactory<Item> itemFactory = id -> new Item(id);
        PremisManipulatorFactory premisManipulatorFactory = new PremisManipulatorFactory<>(PremisManipulatorFactory.TYPE,
                itemFactory);

        Properties properties = System.getProperties();
        properties.setProperty(DOMS_PIDGENERATOR_URL, "http://localhost:7880/pidgenerator-service");
        properties.setProperty(DOMS_URL, "http://localhost:7880/fedora");

        DomsEventStorageFactory<Item> domsEventStorageFactory = new DomsEventStorageFactory<>();
        domsEventStorageFactory.setFedoraLocation(properties.getProperty(DOMS_URL));
        domsEventStorageFactory.setPidGeneratorLocation(properties.getProperty(DOMS_PIDGENERATOR_URL));
        //domsEventStorageFactory.setUsername(properties.getProperty(DOMS_USERNAME));
        //domsEventStorageFactory.setPassword(properties.getProperty(DOMS_PASSWORD));
        domsEventStorageFactory.setItemFactory(itemFactory);

        DomsEventStorage domsEventStorage = domsEventStorageFactory.createDomsEventStorage();
        SBOIEventIndex index = new SBOIEventIndex(
                summaLocation,
                premisManipulatorFactory,
                domsEventStorage,
                pageSize
        );

        boolean details = true;

        Iterator iterator = index.search(details, query);
        while (iterator.hasNext()) {
            System.out.println(">> " + iterator.next());
        }
    }
}
