package com.bnpp.ism.technicalcomponents.application.model.component;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TechnicalComponent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "name", column = @Column(name = "vendor_name")),
			@AttributeOverride(name = "version", column = @Column(name = "vendor_version")),
			@AttributeOverride(name = "description", column = @Column(name = "vendor_description")),
			@AttributeOverride(name = "availableDate", column = @Column(name = "vendor_availableDate")),
			@AttributeOverride(name = "deprecatedDate", column = @Column(name = "vendor_deprecatedDate")),
			@AttributeOverride(name = "unavailableDate", column = @Column(name = "vendor_unavailableDate")) })
	ComponentVersionInfo vendorInformations;

	public ComponentVersionInfo getVendorInformations() {
		return vendorInformations;
	}

	public void setVendorInformations(ComponentVersionInfo vendorInformations) {
		this.vendorInformations = vendorInformations;
	}

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "name", column = @Column(name = "local_name")),
			@AttributeOverride(name = "version", column = @Column(name = "local_version")),
			@AttributeOverride(name = "description", column = @Column(name = "local_description")),
			@AttributeOverride(name = "availableDate", column = @Column(name = "local_availableDate")),
			@AttributeOverride(name = "deprecatedDate", column = @Column(name = "local_deprecatedDate")),
			@AttributeOverride(name = "unavailableDate", column = @Column(name = "local_unavailableDate")) })
	ComponentVersionInfo localInformations;

	public ComponentVersionInfo getLocalInformations() {
		return localInformations;
	}

	public void setLocalInformations(ComponentVersionInfo localInformations) {
		this.localInformations = localInformations;
	}

	@Column
	private boolean forceObsolete = false;

	public boolean isForceObsolete() {
		return forceObsolete;
	}

	public void setForceObsolete(boolean forceObsolete) {
		this.forceObsolete = forceObsolete;
	}

	@ManyToOne
	ObsolescenceStrategy obscolescenceStrategy;
}
