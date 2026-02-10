package hellojpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // JPA를 사용하는 애구나~ 인식
public class Member {

    @Id
    private Long id;
    private String name;

    // JPA 필수: 기본 생성자 (protected 또는 public)
    public Member() {}

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
