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
package org.overlord.gadgets.web.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 27/04/12
 */
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = 2412181515947943577L;

    private List<T> resultSet;

    private int totalResults;

    private int pageSize;

    private int offset;

    public PageResponse(List<T> resultset, int totalResults) {
        this.resultSet = resultset;
        this.totalResults = totalResults;
        this.pageSize = 10;
        this.offset = 0;
    }


    public List<T> getResultSet() {
        return resultSet;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNumberOfPages() {
        if (pageSize == 0) {
            return 0;
        }
        int numberOfPages = totalResults / pageSize;
        if (totalResults % pageSize > 0) {
            numberOfPages++;
        }
        return numberOfPages;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCurrentPage() {
        if (isFirstPage()) {
            return 1;
        }
        return (offset / pageSize) + 1;
    }

    private boolean isFirstPage() {
        return offset == 0 || pageSize == 0 || totalResults < pageSize;
    }

}
