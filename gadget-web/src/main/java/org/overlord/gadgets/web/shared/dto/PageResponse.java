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
