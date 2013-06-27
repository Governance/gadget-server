/*
 * 2012-3 Red Hat Inc. and/or its affiliates and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.gadgets.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author: Jeff Yu
 * @date: 17/01/12
 */
@Entity
@Table(name = "GS_USER")
public class User implements Serializable{

	private static final long serialVersionUID = -5843968472547315144L;

	@Id
    @GeneratedValue
    @Column(name= "ID")
    private long id;

    @Column(name="NAME")
    private String name;

    @Column(name="DISPLAY_NAME")
    private String displayName;

    @Column(name="EMAIL")
    private String email;

    @OneToMany(orphanRemoval = true, mappedBy = "user")
    private List<Page> pages = new ArrayList<Page>();

    @ManyToMany
    @JoinTable(name="GS_USER_GROUP", joinColumns = {
    		@JoinColumn(name="USER_ID")
    }, inverseJoinColumns = {
    		@JoinColumn(name="GROUP_ID")
    })
    private List<Group> groups = new ArrayList<Group>();

    @Column(name="USER_ROLE")
    private String role;

    @Column(name="CURR_PAGE_ID")
    private Long currentPageId = new Long(0);

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCurrentPageId() {
    	if (null == currentPageId)
    		currentPageId = new Long(0);
        return currentPageId;
    }

    public void setCurrentPageId(Long currentPageId) {
        this.currentPageId = currentPageId;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

	/**
	 * @return the groups
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
