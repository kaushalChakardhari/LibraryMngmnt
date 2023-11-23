package com.example.LibraryMngmnt.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
//import javax.persistence.*;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private long rollNo;

    @Column(name="created_date")
    private Timestamp createdDate;
    @Column(name="updated_date")
    private Timestamp updatedDate;

    public String getFirstName() {
        return firstName;
    }



    public void setFirstName(String title) {
        this.firstName=title;
        ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // India's time zone
        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
        updatedDate = Timestamp.valueOf(currentDateTime.toLocalDateTime());
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName=lastName;
        ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // India's time zone
        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
        updatedDate = Timestamp.valueOf(currentDateTime.toLocalDateTime());
    }
    public Long getRollNo(){
        return rollNo;
    }
    public void setRollNo(Long rollNo){
        this.rollNo=rollNo;
        ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // India's time zone
        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
        updatedDate = Timestamp.valueOf(currentDateTime.toLocalDateTime());
    }
    @PrePersist
    protected void onCreate() {

        ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // India's time zone
        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
        createdDate = Timestamp.valueOf(currentDateTime.toLocalDateTime());
        updatedDate = createdDate;
    }

    @PreUpdate
    protected void onUpdate() {
        ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // India's time zone
        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
        updatedDate = Timestamp.valueOf(currentDateTime.toLocalDateTime());
    }

}
