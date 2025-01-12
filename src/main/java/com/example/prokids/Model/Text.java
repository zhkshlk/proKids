package com.example.prokids.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "texts")
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String text;


}
