
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
            // === CREATE (생성) ===
            // 새로운 Member 객체 생성

            // === 비영속 상태 ===
//            Member memberA = new Member(150L, "A");
//            Member memberB = new Member(160L, "B");

            // 영속성 컨텍스트에 Member 객체 저장 (아직 DB에 저장되지 않음)
            // commit() 시점에 실제 INSERT SQL이 실행됨

            // 영속성 컨텍스트를 직접 플러시 (잘 사용하지 않음)
            // em.flush();

            // 준영속 상태로 전환
            // em.detach(member) // 특정 엔티티만 준영속 상태로 전환
            // em.clear()        // 영속성 컨텍스트를 완전히 초기화
            // em.close()        // 영속성 컨텍스트를 종료

            // === 영속성 컨텍스트 추가 상태 ===
            /*
            System.out.println("== before persist ==");
            em.persist(member);
            System.out.println("== after persist ==");

            Member findMember1 = em.find(Member.class, 100L);
            Member findMember2 = em.find(Member.class, 100L);
            System.out.println("result = " + (findMember1 == findMember2));
            */

            // === 트랜잭션을 지원하는 쓰기 지연(transactional write-behind) ===
//            em.persist(memberA);
//            em.persist(memberB);


            /*Member member = em.find(Member.class, 150L);
            member.setUsername("jay"); // JAVA collection 처럼 작동하기 때문에 .persist() 하지 않아도 됨*/



            Member memberA = new Member();
            memberA.setUsername("A");

            Member memberB = new Member();
            memberB.setUsername("B");

            Member memberC = new Member();
            memberC.setUsername("C");


            System.out.println("=======");
            em.persist(memberA);
//            em.persist(memberB);
//            em.persist(memberC);

            System.out.println("memberA = " + memberA.getId());
            System.out.println("memberB = " + memberB.getId());
            System.out.println("memberC = " + memberC.getId());
            System.out.println("=======");

            tx.commit();


            // === READ (조회) ===
            // 데이터베이스에서 ID가 3L인 Member 조회
            // 1차 캐시에 있으면 DB에 가지 않고 캐시에서 반환
//            Member findMember = em.find(Member.class, 3L);

            // 조회 결과 출력
//            System.out.println("findMember = " + findMember.getId());
//            System.out.println("findMember = " + findMember.getName());

            // === UPDATE (수정) ===
            // JPA의 변경 감지(Dirty Checking) 기능
            // 단순히 값을 변경하면 JPA가 자동으로 UPDATE SQL 생성
            // 별도의 update 메서드 호출 불필요!
//            findMember.setName("B");

            // === DELETE (삭제) ===
            // 엔티티 삭제 (현재 주석 처리됨)
            // commit() 시점에 DELETE SQL이 실행됨
//            em.remove(findMember);

            // 4. 트랜잭션 커밋 - 실제 SQL이 DB에 반영되는 시점
            // 이 시점에 INSERT, UPDATE, DELETE SQL이 DB로 전송됨
            tx.commit();

        } catch (Exception e) {
            // 예외 발생 시 오류 내용 출력
            e.printStackTrace();
            // 트랜잭션 롤백 - 모든 변경사항 취소
            tx.rollback();
        } finally {
            // 5. EntityManager 닫기 - 리소스 해제 (필수!)
            // finally 블록에서 반드시 닫아야 함
            em.close();
        }

        // 6. EntityManagerFactory 닫기 - 애플리케이션 종료 시점에 닫기
        // 내부적으로 커넥션 풀 등의 리소스 해제
        emf.close();
    }
}