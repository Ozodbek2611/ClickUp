package uz.pdp.online.clickup.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uz.pdp.online.clickup.user.entity.User;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {

    @Bean
    public AuditorAware<User> auditorAware() {
        return new AuditorAwareImpl();
    }
}
