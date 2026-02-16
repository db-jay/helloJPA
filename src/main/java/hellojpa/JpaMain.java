
package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        // 1. EntityManagerFactory 생성 - 애플리케이션 전체에서 하나만 생성해서 공유
        // "hello"는 persistence.xml에서 정의한 persistence-unit 이름
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 2. EntityManager 생성 - 실제 DB와 연결하고 작업을 수행하는 객체
        // 쓰레드 간에 공유하면 안되고, 사용 후 반드시 닫아야 함
        EntityManager em = emf.createEntityManager();

        // 3. 트랜잭션 시작 - JPA에서 모든 데이터 변경은 트랜잭션 내에서 실행되어야 함
        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션 시작

        try {
            Team team = new Team();
            team.setName("teamA");
//            team.getMembers().add(member); // team에서 member추가 (mapped by = readonly = 결과 null)  연관관계의 주인에 값 설정해야함 !! 중요 !!
            em.persist(team);

            Member member = new Member();
            member.setName("memberA");
//            member.setTeam(team.getId()); // pk id 불러오기 (테이블 방식)
            member.changeTeam(team); // 연관관계 편의 메소드를 통해 양방향 매핑 실행 (중요)
            em.persist(member);

            em.flush();
            em.clear();

            /*
            * 객체 형식 참조 예시 (Team(n) <-> Member (1))
            Member findMember = em.find(Member.class, member.getId());
            // 테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다
            // Long findTeamId = findMember.getTeamId();

            // 객체는 참조를 사용해서 연관된 객체를 찾는다.
            // Team findTeam = findMember.getTeam();
            // System.out.println("findTeam = " + findTeam.getName());

            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println("[m] = " + m.getName());
            }
            */

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}