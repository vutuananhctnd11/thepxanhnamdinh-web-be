package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    Integer id;

    @Column(name = "role_name", nullable = false)
    String roleName;

    @OneToMany(mappedBy = "role")
    List<User> users;


}
