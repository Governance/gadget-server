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

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 8/04/12
 */
public class PageModel{

    private long id;
    
    private String name;
    
    private long order;
    
    private long columns;

    private List<WidgetModel> models = new ArrayList<WidgetModel>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WidgetModel> getModels() {
        return models;
    }

    public void addModel(WidgetModel model) {
        models.add(model);
    }

    public void setModels(List<WidgetModel> models) {
        this.models = models;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public long getColumns() {
        return columns;
    }

    public void setColumns(long columns) {
        this.columns = columns;
    }
}
