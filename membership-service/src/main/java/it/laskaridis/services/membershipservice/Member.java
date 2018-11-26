package it.laskaridis.services.membershipservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String name;
    private String email;
    private String mobilePhone;
    private String homePhone;
    private String businessPhone;
}
