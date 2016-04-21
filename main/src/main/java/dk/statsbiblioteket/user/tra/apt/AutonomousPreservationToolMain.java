package dk.statsbiblioteket.user.tra.apt;

import java.io.FileReader;
import java.util.concurrent.Callable;

/**
 *
 */
public class AutonomousPreservationToolMain implements Callable<Integer> {

    public static final String USER_HOME = "user.home";
    public static final String USER_DIR = "user.dir";
    final private String[] args;

    public AutonomousPreservationToolMain(String[] args) {
        this.args = args;
    }

    public static void main(String[] args) throws Exception {
        try {
            int exitValue = new AutonomousPreservationToolMain(args).call();
            System.exit(exitValue);
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public static ConfigurationMap staticConfigurationMap;

    @Override
    public Integer call() throws Exception {
        ConfigurationMap configurationMap = new ConfigurationMap();
        configurationMap.addSystemProperties(USER_HOME, USER_DIR);
        configurationMap.addEnvironmentVariables("HOME");
        if (args.length > 0) {
            configurationMap.addPropertyFile(new FileReader(args[0]));
        }
        //AutonomousPreservationToolComponent component = DaggerAutonomousPreservationToolComponent.builder().build();

        staticConfigurationMap = configurationMap;

        return 0;
    }


}
