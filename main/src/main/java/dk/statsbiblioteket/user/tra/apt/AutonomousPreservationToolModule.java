package dk.statsbiblioteket.user.tra.apt;

/**
 *
 */

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import java.util.concurrent.Callable;

import static dk.statsbiblioteket.user.tra.apt.AutonomousPreservationToolMain.USER_HOME;

@Module
public class AutonomousPreservationToolModule {

    @Provides
    Callable<String> provideMessageGetter(@Named(USER_HOME) String name) {
        return () -> "Hello World - " + name;
    }

    @Provides
    @Named(USER_HOME)
    String provideName() {
        return AutonomousPreservationToolMain.staticConfigurationMap.getOrDefault(USER_HOME, "default name");
    }


}
