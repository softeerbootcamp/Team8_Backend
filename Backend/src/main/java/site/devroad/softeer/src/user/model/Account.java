package site.devroad.softeer.src.user.model;

import java.sql.Timestamp;

public class Account {
    Long id;
    String name;
    Long roadMapId;
    String phone;
    String type;
    Timestamp createdAt;
    Timestamp updatedAt;

    public Account(Long id, String name, Long roadMapId, String phone, String type, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.roadMapId = roadMapId;
        this.phone = phone;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getRoadMapId() {
        return roadMapId;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
