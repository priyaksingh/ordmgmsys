/**
 * 
 */
package ord.mgm.sys.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// TODO: Auto-generated Javadoc
/**
 * The Class UnitTestConfig.
 *
 * @author priya
 */
@Configuration
@ComponentScan({"ord.mgm.sys"})
@EnableTransactionManagement
@EnableJpaRepositories({"ord.mgm.sys.repository"})
@Profile("unittest")
@PropertySource("classpath:application.properties")
public class UnitTestConfig {
	
	/**
	 * Property sources placeholder configurer.
	 *
	 * @return the property sources placeholder configurer
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	/**
	 * Data source.
	 *
	 * @return the data source
	 */
	@Bean
	public DataSource dataSource() {
		return (new EmbeddedDatabaseBuilder()).setType(EmbeddedDatabaseType.H2).build();
	}
	
	/**
	 * H 2 web server.
	 *
	 * @return the org.h 2 .tools. server
	 * @throws SQLException the SQL exception
	 */
	@Bean (initMethod = "start", destroyMethod = "stop")
	public org.h2.tools.Server h2WebServer() throws SQLException{
		return org.h2.tools.Server.createWebServer("-trace","-web","-webAllowOthers","-webPort","9999");
	}
	
	/**
	 * Transaction manager.
	 *
	 * @param emf the emf
	 * @return the platform transaction manager
	 */
	@Bean(name="transactionManager")
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		final PlatformTransactionManager tm = new JpaTransactionManager(emf);
		((JpaTransactionManager)tm).setDefaultTimeout(600);
		return tm;
	}
	
	/**
	 * Entity manager factory.
	 *
	 * @return the local container entity manager factory bean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.H2);
		
		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.hbm2ddl.import_files", "import.sql");
		
		final LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource());
		emfb.setPackagesToScan("ord.mgm.sys.entity");
		emfb.setJpaProperties(properties);
		emfb.setJpaVendorAdapter(adapter);
		
		return emfb;
	}

}
