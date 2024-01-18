package com.kjm.auth.keycloakauth.provider;

import java.sql.Connection;
import java.util.List;

import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.kjm.auth.keycloakauth.provider.CustomUserStorageProviderConstants.*;

// custom provider 공급자 생성
public class CustomUserStorageProviderFactory implements UserStorageProviderFactory<CustomUserStorageProvider> {
    private static final Logger log = LoggerFactory.getLogger(CustomUserStorageProviderFactory.class);
    protected final List<ProviderConfigProperty> configMetadata;

    public CustomUserStorageProviderFactory() {
        log.info("[I24] CustomUserStorageProviderFactory created");

        // create confing metadata
        configMetadata = ProviderConfigurationBuilder.create()
                .property()
                .name(CONFIG_KEY_JDBC_DRIVER)
                .label("JDBC Driver Class")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("org.h2.Driver")
                .helpText("Fully qualified class name of the JDBC driver")
                .add()
                .property()
                .name(CONFIG_KEY_JDBC_URL)
                .label("JDBC URL")
                .type(ProviderConfigProperty.STRING_TYPE)
                .defaultValue("jdbc:h2:mem:customdb")
                .helpText("JDBC URL used to connect to the user database")
                .add()
                .property()
                .name(CONFIG_KEY_DB_USERNAME)
                .label("Database User")
                .type(ProviderConfigProperty.STRING_TYPE)
                .helpText("Username used to connect to the database")
                .add()
                .property()
                .name(CONFIG_KEY_DB_PASSWORD)
                .label("Database Password")
                .type(ProviderConfigProperty.STRING_TYPE)
                .helpText("Password used to connect to the database")
                .secret(true)
                .add()
                .property()
                .name(CONFIG_KEY_VALIDATION_QUERY)
                .label("SQL Validation Query")
                .type(ProviderConfigProperty.STRING_TYPE)
                .helpText("SQL query used to validate a connection")
                .defaultValue("select 1")
                .add()
                .build();
    }

    /*
     * 실제 공급자 구현체를 생성하는 메소드
     * - keycloak은 모든 트랜잭션에 대해 create() 메서드를 호출해서 keycloaksession과 componentmodel을
     * 전달함(트랜잭션 : 사용자 저장소에 대한 액세스가 필요한 모든 작업을 의미함)
     * - 어느 시점에서 keycloak은 특정 영역에 대해 구성된 모든 사용자 저장소를 호출해서 자격 증명의 유효성을 검증함
     * - 따라서 create 메서드가 항상 호출되므로 이 시점에서는 비용이 많이 드는 초기화 작업을 수행하지 않는게 좋음
     * 
     */
    @Override
    public CustomUserStorageProvider create(KeycloakSession ksession, ComponentModel model) {
        return new CustomUserStorageProvider(ksession, model);
    }

    /*
     * keycloak이 관리 페이지에 표시할 이 공급자의 고유 식별자를 반환함
     * 
     */
    @Override
    public String getId() {
        log.info("[I69] getId()");
        return "custom-user-provider";
    }

    // Configuration support methods
    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configMetadata;
    }

    @Override
    public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config)
            throws ComponentValidationException {

        try (Connection c = DbUtil.getConnection(config)) {
            log.info("[I84] Testing connection...");
            c.createStatement().execute(config.get(CONFIG_KEY_VALIDATION_QUERY));
            log.info("[I92] Connection OK !");
        } catch (Exception ex) {
            log.warn("[W94] Unable to validate connection: ex={}", ex.getMessage());
            throw new ComponentValidationException("Unable to validate database connection", ex);
        }
    }

    @Override
    public void onUpdate(KeycloakSession session, RealmModel realm, ComponentModel oldModel, ComponentModel newModel) {
        log.info("[I94] onUpdate()");
    }

    @Override
    public void onCreate(KeycloakSession session, RealmModel realm, ComponentModel model) {
        log.info("[I99] onCreate()");
    }
}
