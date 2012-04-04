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
package org.guvnor.sam.gadget.web.shared.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jeff Yu
 * @date: 4/04/12
 */
public class UserPreference {
    
    private List<UserPreferenceSetting> data = new ArrayList<UserPreferenceSetting>();
    
    private boolean needToEdit = false;
    
    public void addUserPreferenceSetting(UserPreferenceSetting setting) {
        data.add(setting);
    }
    
    public List<UserPreferenceSetting> getData() {
        return data;
    }

    public boolean isNeedToEdit() {
        return needToEdit;
    }

    public void setNeedToEdit(boolean needToEdit) {
        this.needToEdit = needToEdit;
    }

    /*
    * enum representing all of the valid OpenSocial preference data types
    */
    public static enum Type {
        STRING("STRING"),
        BOOLEAN("BOOL"),
        ENUM("ENUM"),
        LIST("LIST"),
        HIDDEN("HIDDEN");

        private final String dataType;
        private Type(String dataType) {
            this.dataType = dataType;
        }

        @Override
        public String toString() {
            return dataType;
        }
    }
    
    public static class Option {        
        private String displayName;
        private String value;

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
    
    public static class UserPreferenceSetting {
        
        private Type type;
        
        private String name;
        
        private List<Option> enumOptions;

        private String defaultValue;
        
        private boolean isRequired = false;

        private String displayName;

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Option> getEnumOptions() {
            return enumOptions;
        }

        public void setEnumOptions(List<Option> enumOptions) {
            this.enumOptions = enumOptions;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public boolean isRequired() {
            return isRequired;
        }

        public void setRequired(boolean required) {
            isRequired = required;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }
    
    

}
