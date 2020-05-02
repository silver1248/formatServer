package org.sweatshop.format_server;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.file.Path;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FormatServerConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName;

    @NotNull
    private Path headerFile;

    @NotNull
    private Path footerFile;

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    @JsonProperty
    public Path getHeaderFile() {
        return headerFile;
    }

    @JsonProperty
    public void setHeaderFile(Path header) {
        this.headerFile = header;
    }

    @JsonProperty
    public Path getFooterFile() {
        return footerFile;
    }

    @JsonProperty
    public void setFooterFile(Path footer) {
        this.footerFile = footer;
    }
}
