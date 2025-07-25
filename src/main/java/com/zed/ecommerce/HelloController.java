package com.zed.ecommerce;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final Logger logger = LogManager.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String sayHello() {
        logger.info("Info level log example");
        logger.debug("Debug level log example");
        logger.error("Error level log example", new Exception("Example exception"));
        return "Hello, World!";
    }
}
