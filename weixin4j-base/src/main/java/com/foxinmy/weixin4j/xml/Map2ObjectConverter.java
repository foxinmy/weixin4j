package com.foxinmy.weixin4j.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class Map2ObjectConverter extends MapConverter {

	public Map2ObjectConverter(Mapper mapper) {
		super(mapper);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Map<String, String> map = new HashMap<String, String>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			map.put(reader.getNodeName(), reader.getValue());
			reader.moveUp();
		}
		return map;
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Map<?, ?> map = (Map<?, ?>) source;
		for (Entry<?, ?> entry : map.entrySet()) {
			String value = (String) entry.getValue();
			if (StringUtils.isBlank(value)) {
				continue;
			}
			ExtendedHierarchicalStreamWriterHelper.startNode(writer, entry
					.getKey().toString(), entry.getClass());
			writer.setValue(value);
			writer.endNode();
		}
	}
}
