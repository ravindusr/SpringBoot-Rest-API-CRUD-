package edu.icet.crm.student.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private int id;
    private String name;
    private String email;
    private String age;
    private String contact;
    private String address;
    private String guardianName;
    private String guardianEmail;
    private String guardianContact;
    private String guardianAddress;
}
