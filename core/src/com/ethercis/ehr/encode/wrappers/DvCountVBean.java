/*
 * Copyright (c) 2015 Christian Chevalley
 * This file is part of Project Ethercis
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
package com.ethercis.ehr.encode.wrappers;

import com.ethercis.ehr.encode.CompositionSerializer;
import com.ethercis.ehr.encode.DataValueAdapter;
import org.openehr.rm.datatypes.quantity.DvCount;

import java.util.HashMap;
import java.util.Map;

public class DvCountVBean extends DataValueAdapter implements I_VBeanWrapper {

	public DvCountVBean(DvCount c) {
		this.adaptee = c;
	}
	
	@Override
	public Map<String, Object> getFieldMap() throws Exception {
		Map<String, Object>map = new HashMap<String, Object>();
		
		map.put("magnitude", ((DvCount)adaptee).getMagnitude());
		
		return map;
	}

    @Override
    public DvCount parse(String value, String... defaults) {
        adaptee = ((DvCount)adaptee).parse(value);
        return (DvCount)adaptee;
    }

    public static DvCount getInstance(Map<String, Object> attributes){
        Object value = attributes.get(CompositionSerializer.TAG_VALUE);

        if (value == null)
            throw new IllegalArgumentException("No value in attributes");

        if (value instanceof DvCount) return (DvCount)value;

        if (value instanceof Map) {
            Map<String, Object> valueMap = (Map) value;
            Integer magnitude = ((Double) valueMap.get("magnitude")).intValue();
            return new DvCount(magnitude);
        }
        throw new IllegalArgumentException("Could not get instance");
    }
}
