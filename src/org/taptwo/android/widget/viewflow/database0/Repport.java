package org.taptwo.android.widget.viewflow.database0;

public class Repport {

	public Repport() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Repport(int service_request_id, String title, String status,
			int service_code, String service_name, String description,
			String requested_datetime, String updated_datetime,Float lon,
			Float lat, String adresse) {
		super();
		this.service_request_id = service_request_id;
		this.title = title;
		this.status = status;
		this.service_code = service_code;
		this.service_name = service_name;
		this.description = description;
		this.requested_datetime = requested_datetime;
		this.updated_datetime = updated_datetime;
		this.lon = lon;
		this.lat = lat;
		this.adresse = adresse;
	}
	private int service_request_id;
	private String title;
	private String status;
	private int service_code;
	private String service_name;
	
	private String description;
	private String requested_datetime;
	private String updated_datetime; 
	private Float lon;
	private Float lat;
	private String adresse;
	public int getService_request_id() {
		return service_request_id;
	}
	public void setService_request_id(int service_request_id) {
		this.service_request_id = service_request_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getService_code() {
		return service_code;
	}
	public void setService_code(int service_code) {
		this.service_code = service_code;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRequested_datetime() {
		return requested_datetime;
	}
	public void setRequested_datetime(String requested_datetime) {
		this.requested_datetime = requested_datetime;
	}
	public String getUpdated_datetime() {
		return updated_datetime;
	}
	public void setUpdated_datetime(String updated_datetime) {
		this.updated_datetime = updated_datetime;
	}
	
	public Float getLon() {
		return lon;
	}
	public void setLon(Float lon) {
		this.lon = lon;
	}
	public Float getLat() {
		return lat;
	}
	public void setLat(Float lat) {
		this.lat = lat;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	@Override
	public String toString() {
		return "Report [service_request_id=" + service_request_id + ", title="
				+ title + ", status=" + status + ", service_code="
				+ service_code + ", service_name=" + service_name
				+ ", description=" + description + ", requested_datetime="
				+ requested_datetime + ", updated_datetime=" + updated_datetime
				+ ", lon=" + lon + ", lat=" + lat + ", adresse=" + adresse
				+ "]";
	}
}

