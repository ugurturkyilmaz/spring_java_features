package com.example.demo.logging;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LoggingServiceLog4J {
    private static final Level NOTICE = Level.forName("NOTICE", 350);
    private static final Level PM_KEY_NOT_FOUND = Level.forName("PM_KEY_NOT_FOUND", 380);

    //@Slf4j loglamasında yeni level yok, Log4j2'de var. slf4j default loglama, onu exclude ettik gradle'da.
    public void log(String message) {
        log.info("{} info log4j2", message);
        log.warn("{} warn log4j2", message);
        log.error("{} error log4j2", message);
        log.log(NOTICE, "{} notice log4j2", message);
        log.log(PM_KEY_NOT_FOUND, "{} PM_KEY_NOT_FOUND log4j2", message);
        log.debug("{} debug log4j2", message);
        log.trace("{} trace log4j2", message);

    }
}
