package com.scm.backend.projections;

import com.scm.backend.entities.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "user_projection", types = {User.class})
public interface UserProjection {

    String getName();
    String getEmail();
    String getAbout();
}
