//package com.plewa.github;
//
//import com.github.tomakehurst.wiremock.WireMockServer;
//import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
//import org.springframework.context.ApplicationContextInitializer;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.event.ContextClosedEvent;
//
//public class WireMockInitializer implements ApplicationContextInitializer {
//    @Override
//    public void initialize(ConfigurableApplicationContext applicationContext) {
//        WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
//        wireMockServer.start();
//
//        applicationContext.addApplicationListener(applicationContext -> {
//            if (applicationContext instanceof ContextClosedEvent) {
//                wireMockServer.stop();
//            }
//        });
//    }
//}
