package ord.mgm.sys;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({ "ord.mgm.sys.config" })
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ord.mgm.sys.config.repository")
@Profile("live")
public class LiveConfig {

	private static final String schemaMode = "none";

	private static final String generateDdl = "false";

	private static final String dbType = "MYSQL";

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public JndiObjectFactoryBean jndiDataSource() {
		final JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName("jndi/a1config");
		jndiObjectFactoryBean.setResourceRef(true);
		jndiObjectFactoryBean.setLookupOnStartup(false);
		jndiObjectFactoryBean.setProxyInterface(DataSource.class);
		return jndiObjectFactoryBean;
	}

	@Bean
	@DependsOn("jndiDataSource")
	public DataSource dataSource() {
		final LazyConnectionDataSourceProxy dataSourceProxy = new LazyConnectionDataSourceProxy();
		dataSourceProxy.setTargetDataSource((DataSource) jndiDataSource().getObject());
		return dataSourceProxy;
	}

	@Bean(name = "transactionManager")
	/*
	 * Spring calls getObject() on the FactoryBean to obtain the
	 * EntityManagerFactory.
	 */
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(false);
		adapter.setGenerateDdl(Boolean.parseBoolean(generateDdl));
		adapter.setDatabase(Database.valueOf(dbType.toUpperCase()));

		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", schemaMode);

		final LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource());
		emfb.setPackagesToScan("ord.mgm.sys.entity");
		emfb.setJpaProperties(properties);
		emfb.setJpaVendorAdapter(adapter);

		return emfb;
	}
}