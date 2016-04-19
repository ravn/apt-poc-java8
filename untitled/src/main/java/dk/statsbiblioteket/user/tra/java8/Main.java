package dk.statsbiblioteket.user.tra.java8;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

public class Main {

    public static void main(String[] args) throws Exception {
        MainComponent mc = DaggerMain_MainComponent.builder().build();
        System.out.println(mc.messageGetter().call());
    }

    @Component(modules = MainModule.class)
    interface MainComponent {
        Callable<String> messageGetter();
        Configuration configurationGetter();
    }
}

@Module
class MainModule {
    public static final String NAME = "NAME";

    @Provides
    Callable<String> provideMessageGetter(@Named(NAME) String name) {
        return () -> "Hello World - " + name;
    }

    @Provides
    @Named(NAME)
    String provideName(Configuration configuration) {
        return configuration.getOrDefault(NAME, "default name");
    }


    //@Singleton
    @Provides
    Configuration provideConfiguration() {
        Configuration configuration = new Configuration();
        // Copy system properties.
        for (String p : System.getProperties().stringPropertyNames()) {
            configuration.put(p, System.getProperty(p));
        }
        return configuration;
    }
}
