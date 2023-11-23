package com.example.LibraryMngmnt.model;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
//import javax.persistence.*;

@Entity
@Table(name = "Books")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Book {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String title;

    @Column
    private String author;
    @Column(name="created_date")
    private Timestamp createdDate;
    @Column(name="updated_date")
    private Timestamp updatedDate;

    public String getTitle() {
        return title;
    }



    public void setTitle(String title) {
       this.title=title;
        ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // India's time zone
        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
        updatedDate = Timestamp.valueOf(currentDateTime.toLocalDateTime());
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author=author;
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
