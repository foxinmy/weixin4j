package com.foxinmy.weixin4j.xml;

import java.util.Iterator;
import java.util.Map;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class Map2ObjectConverter extends MapConverter {

	public Map2ObjectConverter(Mapper mapper) {
		super(mapper);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Map<?, ?> map = (Map<?, ?>) source;
		for (Iterator<?> iterator = map.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iterator.next();
			ExtendedHierarchicalStreamWriterHelper.startNode(writer, entry
					.getKey().toString(), entry.getClass());
			writer.setValue(entry.getValue().toString());
			writer.endNode();
		}
	}
}
