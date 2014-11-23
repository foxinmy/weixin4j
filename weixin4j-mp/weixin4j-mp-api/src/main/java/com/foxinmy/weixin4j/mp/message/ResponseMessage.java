package com.foxinmy.weixin4j.mp.message;

import org.apache.commons.lang3.StringUtils;

import com.foxinmy.weixin4j.model.BaseMsg;
import com.foxinmy.weixin4j.mp.converter.TextConverter;
import com.foxinmy.weixin4j.msg.model.Article;
import com.foxinmy.weixin4j.msg.model.Base;
import com.foxinmy.weixin4j.msg.model.News;
import com.foxinmy.weixin4j.msg.model.Responseable;
import com.foxinmy.weixin4j.util.ClassUtil;
import com.foxinmy.weixin4j.xml.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 被动消息
 * <p>
 * <font color="red">回复图片等多媒体消息时需要预先上传多媒体文件到微信服务器,
 * 假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试</font>
 * </p>
 * 
 * @className ResponseMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.model.Text
 * @see com.foxinmy.weixin4j.msg.model.Image
 * @see com.foxinmy.weixin4j.msg.model.Voice
 * @see com.foxinmy.weixin4j.msg.model.Video
 * @see com.foxinmy.weixin4j.msg.model.Music
 * @see com.foxinmy.weixin4j.msg.model.News
 * @see com.foxinmy.weixin4j.msg.model.Trans
 * @see <a href=
 *      "http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF"
 *      >回复被动消息</a>
 */
@XStreamAlias("xml")
public class ResponseMessage extends BaseMsg {

	private static final long serialVersionUID = 7761192742840031607L;

	protected final static XStream xmlStream = XStream.get();
	static {
		Class<?>[] classes = ClassUtil.getClasses(Base.class.getPackage())
				.toArray(new Class[0]);
		xmlStream.autodetectAnnotations(true);
		xmlStream.processAnnotations(classes);
		xmlStream.processAnnotations(ResponseMessage.class);
		xmlStream.registerConverter(new TextConverter());

		xmlStream.omitField(BaseMsg.class, "msgId");
		xmlStream.alias("item", Article.class);
		xmlStream.addImplicitCollection(News.class, "articles");
		xmlStream.aliasSystemAttribute(null, "class");
	}
	private String attach; // 附加数据
	private final Base box;

	public ResponseMessage(Base box) {
		this(box, null);
	}

	public ResponseMessage(Base box, BaseMsg inMessage) {
		this(box, inMessage.getFromUserName(), inMessage.getToUserName());
	}

	public ResponseMessage(Base box, String toUserName, String fromUserName) {
		super(box.getMediaType().name(), toUserName, fromUserName);
		this.box = box;
	}

	public String getAttach() {
		return attach;
	}

	public Base getBox() {
		return box;
	}

	/**
	 * 消息对象转换为微信服务器接受的xml格式消息 </br> <font
	 * color="color">需Responseable标识,否则返回空</font>
	 * 
	 * @see com.foxinmy.weixin4j.msg.model.Responseable
	 * @return xml字符串
	 */
	public String toXml() {
		// check responseable
		if (!(box instanceof Responseable)) {
			return "";
		}
		String boxAlias = StringUtils.capitalize(getMsgType());
		XStreamAlias alias = box.getClass().getAnnotation(XStreamAlias.class);
		if (alias != null) {
			boxAlias = alias.value();
		}
		xmlStream.aliasField(boxAlias, ResponseMessage.class, "box");
		if (box instanceof News) {
			attach = Integer.toString(((News) box).getArticles().size());
			xmlStream.aliasField("ArticleCount", ResponseMessage.class,
					"attach");
		}
		return xmlStream.toXML(this);
	}

	@Override
	public String toString() {
		return "ResponseMessage [box=" + box + ", getToUserName()="
				+ getToUserName() + ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + "]";
	}
}
