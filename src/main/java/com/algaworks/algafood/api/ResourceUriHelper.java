package com.algaworks.algafood.api;

import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpHeaders.LOCATION;

@UtilityClass
public class ResourceUriHelper {

    public static void addUriInResponseHeader(Object resourceId) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceId).toUri();

        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();

        response.setHeader(LOCATION, uri.toString());
    }
}
