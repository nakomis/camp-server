package io.brooklyn.camp.test.mock.web;

import io.brooklyn.camp.impl.PlatformComponentTemplate;


public class MockAppServerComponent {

    public static final PlatformComponentTemplate TEMPLATE = 
        PlatformComponentTemplate.builder()
            .name(MockAppServerComponent.class.getCanonicalName())
            .description("Mock Application Server")
            .build();

}