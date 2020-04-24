package com.stusys.cattan.stuinfo.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Long userid;

    private String name;

    private Long studentID;

    private String gender;

    private String IDtype;

    private Long ID;

    private String department;

    private String major;

    private String degree;

    private Date entrancedAt;

    private Date graduatedAt;

    private String nationality;

    private String censusRegistration;

    private String homeAdd;

    private String emergencyContact;
}
