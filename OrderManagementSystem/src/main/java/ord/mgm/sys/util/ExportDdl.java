/**
 * 
 */
package ord.mgm.sys.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumSet;

import javax.persistence.Entity;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author priya
 */
public class ExportDdl {

	private static final Logger logger = LoggerFactory.getLogger(ExportDdl.class);

	private static final String MYSQL_DIALECT = "org.hibernate.dialect.MySQLDialect";

	final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("This will generate a DDL file for your database from the entities.");

		try {
			if (args == null || args.length < 1) {
				String path = getUserInput("Please enter the path where to store the DLL file.",
						System.getProperty("user.home"));
				if (!path.endsWith("/")) {
					path = path + "/";
				}

				String filename = "config_mysql" + ".ddl";

				final MetadataSources metadata = new MetadataSources(
						new StandardServiceRegistryBuilder().applySetting("hibernate.dialect", MYSQL_DIALECT).build());
				final Reflections reflections = new Reflections("ord.mgm.sys.entity");
				for (Class<?> cl : reflections.getTypesAnnotatedWith(Entity.class)) {
					logger.trace("Adding class: {}", cl.getName());
					metadata.addAnnotatedClass(cl);
				}

				final EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.SCRIPT);
				final SchemaExport export = new SchemaExport();
				export.setDelimiter(";");
				export.setFormat(true);
				export.setOutputFile(path + filename);
				export.create(targetTypes, metadata.buildMetadata());
				System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
				System.out.println("DDL file written to: " + path + filename);
				System.out.println("So long and thanks for all the fish.");
				System.exit(0);
			}
		} catch (Exception e) {

		}

	}

	private static String getUserInput(final String msg, final String defaultValue) throws IOException {
		logger.info(msg);
		if (defaultValue != null) {
			System.out.println("Enter nothing to default to: " + defaultValue);
		}
		System.out.print("Input: ");
		final String input = br.readLine();
		if (input != null && !input.isEmpty()) {
			return input;
		} else if (defaultValue != null) {
			return defaultValue;
		}
		return getUserInput(msg, defaultValue);
	}

}
