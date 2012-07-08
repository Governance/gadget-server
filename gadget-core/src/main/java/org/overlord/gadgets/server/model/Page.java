/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-11, Red Hat Middleware LLC, and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.overlord.gadgets.server.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 4/04/12
 */
@Entity
@Table(name="GS_PAGE")
public class Page implements Serializable{

	private static final long serialVersionUID = -3949196421050038288L;

	@Id
    @GeneratedValue
    @Column(name="PAGE_ID")
    private long id;

    @Column(name="PAGE_COLUMNS")
    private long columns;

    @Column(name="PAGE_NAME")
    private String name;

    @Column(name="PAGE_ORDER")
    private long pageOrder;

    @OneToMany(orphanRemoval = true, mappedBy = "page", fetch = FetchType.EAGER)
    private List<Widget> widgets = new ArrayList<Widget>();

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getColumns() {
        return columns;
    }

    public void setColumns(long columns) {
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Widget> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<Widget> widgets) {
        this.widgets = widgets;
    }

    public long getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(long pageOrder) {
        this.pageOrder = pageOrder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
