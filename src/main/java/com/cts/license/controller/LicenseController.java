package com.cts.license.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.license.model.License;
import com.cts.license.service.LicenseService;
import com.cts.license.utils.UserContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/organization/{organizationId}/license")
public class LicenseController {

	@Autowired
	private LicenseService licenseService;

	@RolesAllowed({ "ADMIN", "USER" })
	@RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
	public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {

		License license = licenseService.getLicense(licenseId, organizationId, "");
		license.add(
				linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId()))
						.withSelfRel(),
				linkTo(methodOn(LicenseController.class).createLicense(license)).withRel("createLicense"),
				linkTo(methodOn(LicenseController.class).updateLicense(license)).withRel("updateLicense"),
				linkTo(methodOn(LicenseController.class).deleteLicense(license.getLicenseId()))
						.withRel("deleteLicense"));

		return ResponseEntity.ok(license);
	}

	@RolesAllowed({ "ADMIN", "USER" })
	@RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
	public License getLicensesWithClient(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId, @PathVariable("clientType") String clientType) {
		log.info("Retrieving license and organization using client type {}", clientType);
		return licenseService.getLicense(licenseId, organizationId, clientType);
	}

	@RolesAllowed({ "ADMIN", "USER" })
	@PutMapping
	public ResponseEntity<License> updateLicense(@RequestBody License request) {
		log.info("Updating license {}", request);
		return ResponseEntity.ok(licenseService.updateLicense(request));
	}

	@RolesAllowed({ "ADMIN", "USER" })
	@PostMapping
	public ResponseEntity<License> createLicense(@RequestBody License request) {
		log.info("Creating license {}", request);
		return ResponseEntity.ok(licenseService.createLicense(request));
	}

	@RolesAllowed({ "ADMIN" })
	@DeleteMapping(value = "/{licenseId}")
	public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") String licenseId) {
		log.info("Deleting license {}", licenseId);
		return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
	}

	@RolesAllowed({ "ADMIN", "USER" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<License> getLicenses(@PathVariable("organizationId") String organizationId) throws TimeoutException {
		log.info("Retrieving Licenses for organization {}", organizationId);
		log.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext()
				.getCorrelationId());
		return licenseService.getLicensesByOrganization(organizationId);
	}

}
