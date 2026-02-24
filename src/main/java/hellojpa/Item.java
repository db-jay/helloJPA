package hellojpa;

import jakarta.persistence.*;

@Entity
// 1. JOINED 전략: 부모/자식 테이블을 분리하고 조회 시 조인한다. 정규화에 유리하지만 조인이 늘어난다.
//@Inheritance(strategy = InheritanceType.JOINED)

// 2. SINGLE_TABLE 전략: 하나의 테이블에 자식 컬럼을 모두 저장한다. 조회 성능이 좋지만 null 컬럼이 많아질 수 있다.
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)


// 3. TABLE_PER_CLASS 전략: 자식마다 테이블을 따로 만들고 부모 공통 컬럼도 각 테이블에 중복 저장한다. UNION 조회 비용이 크다.
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

// DTYPE 컬럼: 어떤 자식 엔티티 타입인지 구분하는 값(Discriminator)을 저장한다. (보통 JOINED/SINGLE_TABLE에서 사용)
@DiscriminatorColumn(name = "DTYPE")
//public class Item {
public abstract class Item{
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
