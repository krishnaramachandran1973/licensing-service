package com.cts.license.utils;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class UserContext {

	public static final String CORRELATION_ID = "tmx-correlation-id";

	private String correlationId = new String();

}
