package org.vang.john.hotelbooking.dto;

public class SearchBuildingDTO {

	private String smoking = "Any";

	private String occupancy = "Any";

	public String getSmoking() {
		return smoking;
	}

	public void setSmoking(String smoking) {
		this.smoking = smoking;
	}

	public String getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}

	public boolean isYesSmoking() {
		return this.smoking.compareToIgnoreCase("yes") == 0;

	}

	public boolean isAnySmoking() {
		return this.smoking.compareToIgnoreCase("any") == 0;

	}

	public boolean isDoubleOccupancy() {
		return this.occupancy.compareToIgnoreCase("double") == 0;

	}

	public boolean isAnyOccupancy() {
		return this.occupancy.compareToIgnoreCase("any") == 0;

	}

}
