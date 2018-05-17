package sq.news.admin.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceContext {

	// @Bean
	// JpaTransactionManager transactionManager(EntityManagerFactory
	// entityManagerFactory) {
	// JpaTransactionManager transactionManager = new JpaTransactionManager();
	// transactionManager.setEntityManagerFactory(entityManagerFactory);
	// return transactionManager;
	// }

	// @Bean
	// public SessionFactory entityManager(@Qualifier("entityManagerFactory")
	// EntityManagerFactory emf) {
	// return emf.unwrap(SessionFactory.class);
	// }
	@Bean
	@Qualifier(value = "entityManager")
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
		return entityManagerFactory.createEntityManager();
	}
	// Add the other beans here
}