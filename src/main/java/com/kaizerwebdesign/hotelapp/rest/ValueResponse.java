package com.kaizerwebdesign.hotelapp.rest;

public class ValueResponse {
	private String topic;
	private String value;
	public ValueResponse(String topic, String value) {
		this.topic = topic;
		this.value = value;
	}
	public ValueResponse() {
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
