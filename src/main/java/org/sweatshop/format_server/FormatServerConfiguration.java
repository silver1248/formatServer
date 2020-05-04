package org.sweatshop.format_server;

import io.dropwizard.Configuration;
import lombok.EqualsAndHashCode;
import lombok.Value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.file.Path;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper=false)
public class FormatServerConfiguration extends Configuration {
    @NotEmpty String template;
    @NotEmpty String defaultName;
    @NotNull Path headerFile;
    @NotNull Path footerFile;
    @NotNull Path filesDir;

    @JsonCreator
    public FormatServerConfiguration(
            @JsonProperty("template") String template, @JsonProperty("defaultName") String defaultName,
            @JsonProperty("headerFile") Path headerFile, @JsonProperty("footerFile") Path footerFile,
            @JsonProperty("filesDir") Path filesDir)
    {
        this.template = template;
        this.defaultName = defaultName;
        this.headerFile = headerFile;
        this.footerFile = footerFile;
        this.filesDir = filesDir;
    }
}
