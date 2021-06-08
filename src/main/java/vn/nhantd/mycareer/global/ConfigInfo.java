package vn.nhantd.mycareer.global;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class ConfigInfo {

    private static Config config = ConfigFactory.parseFile(new File("conf.properties"));
    public static final int SERVICE_PORT = config.getInt("service.port");

    // Druid Config
    public static String DRUID_URL = config.getString("druid.url");

    // Druid Basic Authentication
    public static String DRUID_AUTH_USERNAME = config.getString("druid.auth.username");
    public static String DRUID_AUTH_PASSWORD = config.getString("druid.auth.password");

    public static String DRUID_TABLE_TRACKING_EVENT_ANALYTIC = config.getString("druid.table.tracking.event.analytic");
}