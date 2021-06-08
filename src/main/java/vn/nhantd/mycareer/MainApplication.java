package vn.nhantd.mycareer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import vn.nhantd.mycareer.global.ConfigInfo;

import java.util.Properties;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {

        Properties prop = new Properties();
        prop.put("server.port", ConfigInfo.SERVICE_PORT);

        SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder()
                .sources(MainApplication.class)
                .properties(prop);
        applicationBuilder.run(args);

    }
}
