package org.sweatshop.format_server;

import io.dropwizard.Configuration;
import lombok.EqualsAndHashCode;
import lombok.Value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.sweatshop.format_server.config.FilesConfig;

@Value
@EqualsAndHashCode(callSuper=false)
public class FormatServerConfiguration extends Configuration {
    @NotEmpty String template;
    @NotEmpty String defaultName;
    @NotNull FilesConfig filesConfig;

    @JsonCreator
    public FormatServerConfiguration(
            @JsonProperty("template") String template, @JsonProperty("defaultName") String defaultName,
            @JsonProperty("filesConfig") FilesConfig filesConfig)
    {
        this.template = template;
        this.defaultName = defaultName;
        this.filesConfig = filesConfig;
    }
}
