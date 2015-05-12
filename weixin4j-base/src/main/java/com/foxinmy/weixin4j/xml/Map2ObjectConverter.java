package com.foxinmy.weixin4j.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.foxinmy.weixin4j.util.StringUtil;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * Map转换为实体对象
 * 
 * @className Map2ObjectConverter
 * @author jy
 * @date 2015年3月29日
 * @since JDK 1.7
 * @see
 */
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
			if (entry.getValue() == null) {
				continue;
			}
			String value = entry.getValue().toString();
			if (StringUtil.isBlank(value)) {
				continue;
			}
			ExtendedHierarchicalStreamWriterHelper.startNode(writer, entry
					.getKey().toString(), entry.getClass());
			writer.setValue(value);
			writer.endNode();

		}
	}
}
