package com.example.prokids.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "role_name")
    private String roleName;

}
