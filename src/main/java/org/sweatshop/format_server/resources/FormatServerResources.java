package org.sweatshop.format_server.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.sweatshop.format_server.api.FormatServerSaying;

import java.util.concurrent.atomic.AtomicLong;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FormatServerResources {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public FormatServerResources(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @Path("hello-world")
    @GET
    @Timed
    public FormatServerSaying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new FormatServerSaying(counter.incrementAndGet(), value);
    }

    /*
     * 1. make this return header+name+footer WITHOUT changing the one above
     * 2. make two files, src/main/resources/header.html & src/main/resources/footer.html and return what's in them with name in the middle
     * 3. have the paths of those files in the config.yml, and pass them through to here
     * 4. learn how to intercept the path, (from files/ so localhost:8080/files/a/b/c would mean find the dir/a/b/c) and print that out
     * 5. put files in a directory, and find the file at that relative path, and return header file contents, path file contents, footer file contents
     * 6. if the path doesn't exist, header, error, footer (you'll need to pass an error file path in as well)
     */
    public static String readLine(String fileName) throws FileNotFoundException, IOException {
        String fileText = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileText += line + "\n";
            }
         }
        return fileText.substring(0, fileText.length()-1);
    }
    
    @Path("hello-world2")
    @Produces(MediaType.TEXT_HTML)
    @GET
    @Timed
    public String sayHello2(@QueryParam("name") Optional<String> name) throws FileNotFoundException, IOException {
        final String value = String.format("%s %s %s", readLine("src/main/resources/header.html"), name.orElse(defaultName), readLine("src/main/resources/footer.html"));
        return value;
    }
}
