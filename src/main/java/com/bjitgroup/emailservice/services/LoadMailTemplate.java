package com.bjitgroup.emailservice.services;

import com.bjitgroup.emailservice.constants.MessageConstant;
import com.bjitgroup.emailservice.exception.EmailException;
import com.bjitgroup.emailservice.services.impl.SmtpMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public interface LoadMailTemplate {
    static final Logger log = LoggerFactory.getLogger(SmtpMailService.class);

    /**
     * Loads an HTML template file and processes it with dynamic content if provided otherwise it processes the default template
     *
     * @param htmlTemplateFile The file to be validated.
     * @throws EmailException If the file fails size or type validation.
     */
    default String loadHtmlTemplate(File htmlTemplateFile, Map<String, Object> objectMap) {
        try {
            byte[] templateBytes;
            if (htmlTemplateFile != null && htmlTemplateFile.exists() && !ObjectUtils.isEmpty(objectMap)) {
                // Process HTML template with dynamic content
                return processHtmlTemplate(htmlTemplateFile, objectMap);
            } else if (htmlTemplateFile != null && htmlTemplateFile.exists()) {
                // Load HTML content from the custom template file without dynamic content
                templateBytes = Files.readAllBytes(htmlTemplateFile.toPath());
            } else {
                // Load HTML content from the default template file
                String templateName = "welcome.html";
                // Load HTML content from the template file in the resources/templates directory
                ClassPathResource resource = new ClassPathResource("templates/" + templateName);
                templateBytes = StreamUtils.copyToByteArray(resource.getInputStream());
            }
            return new String(templateBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(MessageConstant.unsuccessful_template_loading, e);
            throw new RuntimeException(MessageConstant.unsuccessful_template_loading, e);
        }
    }

    private String processHtmlTemplate(File htmlTemplateFile, Map<String, Object> objectMap) {
        try {
            TemplateEngine templateEngine = new TemplateEngine();
            String template = Files.readString(htmlTemplateFile.toPath(), StandardCharsets.UTF_8);
            Context context = new Context();
            context.setVariables(objectMap);

            return templateEngine.process(template, context);
        } catch (IOException e) {
            log.error("Error loading HTML template file", e);
            throw new RuntimeException("Error loading HTML template file", e);
        }
    }
}
