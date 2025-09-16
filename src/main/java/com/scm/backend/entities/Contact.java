package com.scm.backend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    @Lob
    private String description;
    private boolean favourite = false;
    private String websiteLink;
    private String linkedInLink;
    private String instagramLink;
    private String picture;
    private String cloudinaryImagePublicId;

    @ManyToOne
    private User user;

}
