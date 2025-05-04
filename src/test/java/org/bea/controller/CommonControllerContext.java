package org.bea.controller;

import org.bea.config.DataSourceConfigurationTest;
import org.bea.config.ServiceConfiguration;
import org.bea.config.WebConfigurationTest;
import org.bea.service.CommonServiceContext;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitConfig(classes = {DataSourceConfigurationTest.class, WebConfigurationTest.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application.properties")
public class CommonControllerContext {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}
