package hellojpa;

import jakarta.persistence.*;

@Entity(name="MEMBER")
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    /*
    * 외래 키로 조인을 사용해서 연관된 테이블을 찾는 테이블 방식
    @Column(name = "TEAM_ID") // relation
    private Long teamId;
    */

    /* 연관된 객체를 찾는 객체 참조 방식.
    * Member: Many
    * Team: one
    * */
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩 전략
    @JoinColumn(name = "TEAM_ID")
    private Team team;

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

    public Team getTeam() {
        return team;
    }

//    public void setTeam(Team team) {
    public void changeTeam(Team team) { // changeTeam으로 메서드명 변경하여 가독성 확보
        this.team = team;
        team.getMembers().add(this); // team을 set하는 시점에 양쪽에 값을 설정(연관관계 편의 메소드)
    }
}
