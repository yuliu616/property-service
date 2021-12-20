package com.yu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequestMapping("${property-service.api-base-url}/about")
@RestController
public class AboutController {

    private int randomId = new Random().nextInt(10000)%10000 +10000;

    @Value("${property-service.service-name}")
    protected String serviceName;

    @Value("${property-service.api-version}")
    protected String apiVersion;

    @Value("${property-service.api-base-url}")
    protected String apiBaseUrl;

    @Value("${property-service.version-description}")
    protected String versionDescription;

    @Value("${property-service.options.enable-debug-endpoint}")
    protected boolean enableDebugEndpoint;

    private static final Logger logger = LoggerFactory.getLogger(AboutController.class);

    @GetMapping("")
    public Map<String, Object> healthCheck(
            @RequestParam(value = "printLogTest", defaultValue = "false") boolean printLogTest,
            @RequestParam(value = "printLog", defaultValue = "true") boolean printLog)
    {
        if (printLogTest) {
            logger.trace("healthCheck endpoint invoked");
            logger.debug("healthCheck endpoint invoked");
            logger.info("healthCheck endpoint invoked");
            logger.warn("healthCheck endpoint invoked");
            logger.error("healthCheck endpoint invoked");
        } else if (printLog) {
            logger.info("healthCheck endpoint invoked");
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("apiVersion", this.apiVersion);
        map.put("apiBaseUrl", this.apiBaseUrl);
        map.put("serviceName", this.serviceName);
        map.put("currentTime", java.time.Instant.now());
        map.put("currentDate", java.time.LocalDate.now());
        if (enableDebugEndpoint) {
            map.put("description", this.versionDescription);
            map.put("instanceRandId", this.randomId);
        }
        return map;
    }

}
