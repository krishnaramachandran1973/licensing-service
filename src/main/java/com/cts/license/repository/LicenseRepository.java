package com.cts.license.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cts.license.model.License;

public interface LicenseRepository extends CrudRepository<License, Long> {
	public List<License> findByOrganizationId(String organizationId);

	public License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);

}
