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
    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserPreference[")
               .append("needToEdit=>" + needToEdit);
        builder.append("{");
        for (UserPreferenceSetting setting : data) {
            builder.append(setting);
        }
        builder.append("}");
        builder.append("]");
        return builder.toString();
    }

    /*
    * enum representing all of the valid OpenSocial preference data types
    */
    public static enum Type {
        STRING("STRING"),
        BOOL("BOOL"),
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
        private String displayValue;
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public void setDisplayValue(String displayValue) {
            this.displayValue = displayValue;
        }
    }
    
    public static class UserPreferenceSetting {
        
        private Type type;
        
        private String name;
        
        private List<Option> enumOptions = new ArrayList<Option>();
        
        private List<String> listOptions = new ArrayList<String>();

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

        public void addEnumOption(Option option) {
            this.enumOptions.add(option);
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
        
        
		public List<String> getListOptions() {
			return listOptions;
		}

		public void setListOptions(List<String> listOptions) {
			this.listOptions = listOptions;
		}
		
		public void addListOption(String option) {
			this.listOptions.add(option);
		}

		public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
        
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("UserPreferenceSetting[")
                   .append(" type=>" + type)
                   .append(" name=>" + name)
                   .append(" defaultValue=>" + defaultValue)
                   .append(" displayName=>" + displayName)
                   .append(" isRequired=>" + isRequired)
                   .append(" enumOption size => " + enumOptions.size())
                   .append(" listOption size => " + listOptions.size())
                   .append("]");
            return builder.toString();
        }
        
    }
    
    

}
