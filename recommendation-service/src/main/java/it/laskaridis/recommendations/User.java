package it.laskaridis.recommendations;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // for jackson
public class User {
    private String name;
    private Integer age;
}
