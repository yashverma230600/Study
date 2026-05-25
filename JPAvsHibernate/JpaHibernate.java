package JPAvsHibernate;
//explain jpa vs hibernate with simple example
public class JpaHibernate {
    public static void main(String[] args) {
        System.out.println("JPA (Java Persistence API) is a specification for managing relational data in Java applications.");
        System.out.println("Hibernate is an implementation of the JPA specification, providing additional features and optimizations.");
        System.out.println("In simple terms, JPA defines the rules and interfaces for working with databases, while Hibernate provides the actual implementation of those rules.");
    }

    // ========== Entity Class (Same for both JPA & Hibernate) ==========
    /*
    @Entity
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        // getters and setters
    }
    */

    // ========== JPA Example (Standard API) ==========
    /*
    // Uses EntityManager (JPA interface)
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("myUnit");
    EntityManager em = emf.createEntityManager();

    em.getTransaction().begin();
    User user = new User();
    user.setName("Yash");
    em.persist(user);                    // JPA method
    em.getTransaction().commit();

    // Query using JPQL (JPA standard query language)
    List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
    em.close();
    */

    // ========== Hibernate Example (Hibernate-specific API) ==========
    /*
    // Uses Session (Hibernate's own interface)
    SessionFactory sf = new Configuration().configure().buildSessionFactory();
    Session session = sf.openSession();

    session.beginTransaction();
    User user = new User();
    user.setName("Yash");
    session.save(user);                  // Hibernate-specific method
    session.getTransaction().commit();

    // Query using Hibernate Criteria API (Hibernate-specific)
    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<User> cq = cb.createQuery(User.class);
    cq.from(User.class);
    List<User> users = session.createQuery(cq).getResultList();
    session.close();
    */

    // ========== Key Difference Summary ==========
    // JPA:       EntityManagerFactory → EntityManager → persist()
    // Hibernate: SessionFactory       → Session       → save()
    //
    // JPA is the SPECIFICATION (like an interface)
    // Hibernate is the IMPLEMENTATION (like a class implementing that interface)
    //
    // If you use JPA API → you can switch to EclipseLink, OpenJPA, etc.
    // If you use Hibernate API → you're locked to Hibernate
}
