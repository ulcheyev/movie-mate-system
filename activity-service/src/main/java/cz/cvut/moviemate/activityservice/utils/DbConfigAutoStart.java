package cz.cvut.moviemate.activityservice.utils;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DbConfigAutoStart extends AbstractCassandraConfiguration {

    @Value("${spring.cassandra.contact-points:localhost:9042}")
    private String contactPoints;

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${spring.cassandra.replication-factor:1}")
    private int replicationFactor;

    @Bean
    public CassandraTemplate cassandraTemplate(CqlSession session, CassandraConverter converter) {
        return new CassandraTemplate(session, converter);
    }

    @Override
    public String getContactPoints() {
        return contactPoints;
    }

    @Override
    public String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                .createKeyspace(keyspaceName).ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication(replicationFactor);
        return Arrays.asList(specification);
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{
                "cz.cvut.moviemate.activityservice.rating",
                "cz.cvut.moviemate.activityservice.preference",
        };
    }

}