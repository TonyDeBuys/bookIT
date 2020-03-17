package za.co.pbtgroup.bookit.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import za.co.pbtgroup.bookit.Constants;

/**
 * @author Tony De Buys
 *
 */
@Configuration
public class SpringWebConfig extends WebMvcConfigurationSupport {

	public final static String[] WEB_RESOURCES = {"bootstrap/",
									              "chartjs/",
									              "gui/",
									              "css/",
									        	  "datatables/",
									        	  "datepicker/",
									        	  "font-awesome/",
									        	  "img/",
									        	  "jqTree/",
									        	  "jquery/",
									        	  "jquery-ui/",
									        	  "js/",
									        	  "viz/",
									        	  "nicEdit/",
									        	  "popper/"
									             };

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		List<String> handlers = new ArrayList<>();
		List<String> locations = new ArrayList<>();

       registry.addResourceHandler("/webjars/**")
         .addResourceLocations("classpath:/META-INF/resources/webjars/");

		for (String resource: WEB_RESOURCES) {
			String handler = resource;
			String location = "classpath:/static/" + resource;

			if (!handler.startsWith("/")) {
				handler = "/" + handler;
			}
			if (handler.endsWith("/")) {
				handler += "**";
			}

			handlers.add(handler);
			locations.add(location);
		}

		registry.addResourceHandler(handlers.toArray(new String[handlers.size()]))
				.addResourceLocations(locations.toArray(new String[locations.size()]));
	}

    // To handle multipart forms (file uploads)
    // Based on: https://www.mkyong.com/spring/spring-mvc-file-upload-example-commons-fileupload/
	// NOTE: Without the name parameter on the bean the uploaded file doesn't error, but return's 0 uploaded files
	// Found reference here: https://stackoverflow.com/questions/26118099/how-to-config-commonsmultipartresolver-in-spring4-without-xml-to-upload-file

	// NOTE: I found that uploading a file larger than the limited filesize lets the browser upload the file several times and then crashes with
	// a ERR_CONNECTION_RESET error.  To fix this I've changed the server.xml as per this article:
	//                https://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
	// (See the application.properties file)

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver();
        cmr.setMaxUploadSize((Constants.UPLOAD_FILE_SIZE_LIMIT_MB * 1024 * 1024) * 2);
        cmr.setMaxUploadSizePerFile(Constants.UPLOAD_FILE_SIZE_LIMIT_MB * 1024 * 1024); //bytes

        return cmr;
    }

	// Websites for above:
	//     https://stackoverflow.com/questions/34155393/tam-webseal-spring-pre-authentication
	//     http://romiawasthy.blogspot.com/2012/04/spring-security-single-sign-on-emulator.html

}
