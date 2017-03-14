package concrete;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

@Component
public class DataSourceConfig {

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
		dbBuilder.setType(EmbeddedDatabaseType.HSQL);
		dbBuilder.setName("concretedb");
		dbBuilder.addScript("create-db.sql");
		EmbeddedDatabase db = dbBuilder.build();
		return db;
	}

}
