/**
 * 
 */
package org.overlord.gadgets.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Jeff Yu
 */
@Entity
@Table(name="GS_WIDGET_PREF")
public class WidgetPreference implements Serializable{

	private static final long serialVersionUID = -1839969672969289874L;
	
	@Id @GeneratedValue
	@Column(name="WIDGET_PREF_ID")
	private long id;
	
	@Column(name="WIDGET_PREF_NAME")
	private String name;
	
	@Column(name="WIDGET_PREF_VALUE")
	private String value;
	
	@ManyToOne
	private Widget widget;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}
	
	

}
