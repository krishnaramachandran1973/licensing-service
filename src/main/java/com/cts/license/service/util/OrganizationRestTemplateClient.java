package com.cts.license.service.util;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cts.license.model.Organization;
import com.cts.license.repository.OrganizationRedisRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OrganizationRestTemplateClient {

	@Autowired
	private KeycloakRestTemplate restTemplate;

	@Autowired
	OrganizationRedisRepository redisRepository;

	public Organization getOrganization(String organizationId) {

		Organization organization = checkRedisCache(organizationId);
		if (organization != null) {
			log.debug("I have successfully retrieved an organization {} from the redis cache: {}", organizationId,
					organization);
			return organization;
		}

		log.debug("Unable to locate organization from the redis cache: {}.", organizationId);

		ResponseEntity<Organization> restExchange = restTemplate.exchange(
				"http://gateway-server/organization/v1/organization/{organizationId}", HttpMethod.GET, null,
				Organization.class, organizationId);

		organization = restExchange.getBody();
		if (organization != null) {
			cacheOrganizationObject(organization);
		}

		return restExchange.getBody();
	}

	private Organization checkRedisCache(String organizationId) {
		try {
			return redisRepository.findById(organizationId)
					.orElse(null);
		} catch (Exception ex) {
			log.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}",
					organizationId, ex);
			return null;
		}
	}

	private void cacheOrganizationObject(Organization organization) {
		try {
			//redisRepository.save(organization);
		} catch (Exception ex) {
			log.error("Unable to cache organization {} in Redis. Exception {}", organization.getId(), ex);
		}
	}

}
