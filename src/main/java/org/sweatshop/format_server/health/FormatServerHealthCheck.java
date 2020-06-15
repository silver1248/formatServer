package org.sweatshop.format_server.health;

import com.codahale.metrics.health.HealthCheck;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper=false)
public class FormatServerHealthCheck extends HealthCheck {
    String template;

    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if (saying.indexOf("TEST") == -1) {
            return Result.unhealthy("template doesn't include a name");
        } else {
            return Result.healthy();
        }
    }
}
