package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name="MEMBER") // @Entity.name 사용할 엔티티 이름을 지정한다. (생략 시 기본 값. 생략 권장)
@Table(name="MEMBER")  // @Table.name은 엔티티와 매핑할 테이블 지정

@SequenceGenerator(
    name = "MEMBER_SEQ_GENERATOR",
    sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름,
    initialValue = 1,
    allocationSize = 50 // allocationSize = 50(default)
)

/*@TableGenerator(
    name = "MEMBER_SEQ_GENERATOR",
    table = "MY_SEQUENCES",
    pkColumnValue = "MEMBER_SEQ", allocationSize = 50
) */
public class Member {

    /* PK 매핑 */
    @Id
    /* GenerationType
    * IDENTITY: 데이터베이스에 위임 (Mysql)
    * SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용 (ORACLE)
    *  */
//    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR"
    )
/*    @GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "MEMBER_SEQ_GENERATOR"
    )*/

    private Long id;

    // 유니크 제약조건 추가
//    @Column(unique = true, length = 10)
//    private String name;

    /* @Column: 컬럼 매핑  */
    @Column(name = "name")
    private String username;
    private Integer age;

    /* @Enumerated: Enum 타입 사용 시 사용하는 어노테이션 */
    @Enumerated(EnumType.STRING) // enum은 절대 EnumType.ORDINAL를 사용하면 안되고 EnumType.STRING으로 사용해야함!!!
    private RoleType roleType;


    /* @Temporal: 날짜 타입 매핑 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    /* @Temporal 최신 타입 */
//    private LocalDate lastModifiedDate3;
//    private LocalDateTime lastModifiedDateTime2;

    /* @Lob: BLOB, CLOB 매핑 */
    @Lob
    private String description;

    // JPA 필수: 기본 생성자 (protected 또는 public)
    public Member() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
