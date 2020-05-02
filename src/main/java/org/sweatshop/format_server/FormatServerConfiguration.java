package org.sweatshop.format_server;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

public class FormatServerConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName;

    @NotEmpty
    private String headerFile;

    @NotEmpty
    private String footerFile;

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
    public String getHeaderFile() {
        return headerFile;
    }

    @JsonProperty
    public void setHeaderFile(String header) {
        this.headerFile = header;
    }

    @JsonProperty
    public String getFooterFile() {
        return footerFile;
    }

    @JsonProperty
    public void setFooterFile(String footer) {
        this.footerFile = footer;
    }
}
