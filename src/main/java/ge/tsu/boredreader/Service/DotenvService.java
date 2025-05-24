package ge.tsu.boredreader.Service;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DotenvService {

    private static final Logger logger = LoggerFactory.getLogger(DotenvService.class);
    private final String apiKey;

    public DotenvService() {
        this.apiKey = loadApiKey();
    }

    private String loadApiKey() {
        String envApiKey = getFromSystemEnv("ANTHROPIC_API_KEY");
        if (envApiKey != null && !envApiKey.isEmpty()) {
            logger.info("API key loaded from system environment variables");
            return envApiKey;
        }

        logger.error("ANTHROPIC_API_KEY not found in system environment variables");
        return null;
    }

    /**
     * Get environment variable from system environment
     * @param key the environment variable name
     * @return the value or null if not found
     */
    public String getFromSystemEnv(String key) {
        try {
            String value = System.getenv(key);
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }

            value = System.getProperty(key);
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }

            return null;
        } catch (Exception e) {
            logger.error("Error reading environment variable {}: {}", key, e.getMessage());
            return null;
        }
    }
}