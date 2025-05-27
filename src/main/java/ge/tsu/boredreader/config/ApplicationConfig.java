package ge.tsu.boredreader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties
public class ApplicationConfig {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @PostConstruct
    public void init() {
        logger.info("=================================================");
        logger.info("♿♿♿     APP IS LAUNCHING     ♿♿♿");
        logger.info("Active Profile: {}", activeProfile);
        logger.info("=================================================");
    }

    @Configuration
    @Profile("dev")
    static class DevConfig {
        private static final Logger logger = LoggerFactory.getLogger(DevConfig.class);

        @PostConstruct
        public void init() {
            logger.info("\n- Dev Active");
            logger.info("\n- H2 Console enabled ");
        }
    }

    @Configuration
    @Profile("prod")
    static class ProdConfig {
        private static final Logger logger = LoggerFactory.getLogger(ProdConfig.class);

        @PostConstruct
        public void init() {
            logger.info("- Prod Active");
            logger.info("- H2 Console disabled");
        }
    }
}