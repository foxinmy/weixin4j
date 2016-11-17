package com.foxinmy.weixin4j.http.message;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.MimeType;
import com.foxinmy.weixin4j.util.Consts;

public abstract class AbstractMessageConverter implements MessageConverter {

	protected Charset charset = Consts.UTF_8;

	private List<MimeType> supportedMimeTypes;

	protected AbstractMessageConverter() {
		this.supportedMimeTypes = Collections.emptyList();
	}

	protected AbstractMessageConverter(MimeType supportedMimeType) {
		setSupportedMediaTypes(Collections.singletonList(supportedMimeType));
	}

	protected AbstractMessageConverter(MimeType... supportedMimeTypes) {
		setSupportedMediaTypes(Arrays.asList(supportedMimeTypes));
	}

	public void setSupportedMediaTypes(List<MimeType> supportedMimeTypes) {
		this.supportedMimeTypes = new ArrayList<MimeType>(supportedMimeTypes);
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	@Override
	public List<MimeType> supportedMimeTypes() {
		return Collections.unmodifiableList(this.supportedMimeTypes);
	}

	@Override
	public boolean canConvert(Class<?> clazz, HttpResponse response) {
		MimeType mimeType = MimeType.valueOf(response.getHeaders().getContentType());
		byte[] content = response.getContent();
		return supports(clazz, mimeType) || supports(clazz, content);
	}

	/**
	 * 满足其中一个supports
	 * 
	 * @param clazz
	 *            转换类型
	 * @param mimeType
	 *            媒体类型
	 * @return 支持标识
	 */
	protected boolean supports(Class<?> clazz, MimeType mimeType) {
		if (mimeType == null) {
			return true;
		}
		for (MimeType supportedMediaType : supportedMimeTypes()) {
			if (supportedMediaType.includes(mimeType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 满足其中一个supports
	 * 
	 * @param clazz
	 *            转换类型
	 * @param content
	 *            内容数据
	 * @return 支持标识
	 */
	protected abstract boolean supports(Class<?> clazz, byte[] content);

	@Override
	public <T> T convert(Class<? extends T> clazz, HttpResponse response) throws IOException {
		return convertInternal(clazz, response.getBody());
	}

	protected abstract <T> T convertInternal(Class<? extends T> clazz, InputStream body) throws IOException;
}
