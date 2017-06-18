package app.config;

import app.auth.SnapshotFilter;
import app.model.DbSnapshot;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "app")
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public StorageConfig storageConfig() {return new StorageConfig();}

    @Bean
    public Filter snapshotFilter(){
        return new SnapshotFilter();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
//        registry.addMapping("/**").allowedOrigins("http://localhost:1000");
//        registry.addMapping("/**").allowedOrigins("http://elixir.ionutrobert.com").allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Bean
    public DataSource getDataSource() {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(this.getTargetDataSources());
        return routingDataSource;
    }

    private Map<Object, Object> getTargetDataSources(){
        HashMap<Object, Object> targetDataSources = new HashMap<>();
        for (DbSnapshot dbSnapshot : DbSnapshotHolder.SNAPSHOTS) {
            final DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(env.getProperty(dbSnapshot.getProperty("driver-class-name")));
            dataSource.setUrl(env.getProperty(dbSnapshot.getProperty("url")));
            dataSource.setUsername(env.getProperty(dbSnapshot.getProperty("username")));
            dataSource.setPassword(env.getProperty(dbSnapshot.getProperty("password")));
            targetDataSources.put(dbSnapshot.getKey(), dataSource);
        }
        return targetDataSources;
    }

}
