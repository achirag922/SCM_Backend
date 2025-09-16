package com.scm.backend.repositories;

import com.scm.backend.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "user-contacts", collectionResourceRel = "user-contacts")
@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    List<Contact> findByEmailContainingIgnoreCase(String email);

    @RestResource(exported = false)
    List<Contact> findByPhoneNumberContainingIgnoreCase(String phoneNumber);

    @RestResource(path = "by-name", rel = "by-name")
    List<Contact> findByNameContainingIgnoreCase(String name);
}
