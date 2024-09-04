package org.example.ote.jwtspringsample.controller;

import org.example.ote.jwtspringsample.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ HelloController.class, TokenController.class })
@Import(SecurityConfig.class)
public class HelloControllerTests {

    @Autowired
    MockMvc mvc;

    @Test
    void rootWhenAuthenticatedThenSaysHelloUser() throws Exception {
        MvcResult result = this.mvc.perform(post("/token")
                        .with(httpBasic("user", "0000")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        this.mvc.perform(get("/hello")
                        .header("Authorization", "Bearer " + token))
                .andExpect(content().string("Hello, user!"));
    }

    @Test
    void rootWhenAuthenticatedThenSaysAdminUser() throws Exception {
        MvcResult result = this.mvc.perform(post("/token")
                        .with(httpBasic("user", "0000")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        this.mvc.perform(get("/admin")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void rootWhenAuthenticatedThenSaysAdmin() throws Exception {
        MvcResult result = this.mvc.perform(post("/token")
                        .with(httpBasic("admin", "1111")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        this.mvc.perform(get("/admin")
                        .header("Authorization", "Bearer " + token))
                .andExpect(content().string("Hello, admin!"));
    }

    @Test
    void rootWhenAuthenticatedThen404() throws Exception {
        MvcResult result = this.mvc.perform(post("/token")
                        .with(httpBasic("user", "0000")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        this.mvc.perform(get("/wrong")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void rootWhenUnauthenticatedThen401() throws Exception {
        this.mvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void tokenWhenBadCredentialsThen401() throws Exception {
        this.mvc.perform(post("/token"))
                .andExpect(status().isUnauthorized());
    }
}