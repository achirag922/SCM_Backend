package com.scm.backend.config;

import com.scm.backend.entities.Contact;
import com.scm.backend.entities.User;
import com.scm.backend.projections.UserProjection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
public class RestConfig {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer(){
        return new RepositoryRestConfigurer() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
                config.setBasePath("/api");
                config.exposeIdsFor(User.class, Contact.class);
                config.getProjectionConfiguration().addProjection(UserProjection.class);
            }
        };
    }

}
