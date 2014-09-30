package com.foxinmy.weixin4j.msg.out;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.msg.model.Music;
import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.xml.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复音乐消息
 * 
 * @className MusicMessage
 * @author jy.hu
 * @date 2014年3月23日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B.9E.E5.A4.8D.E9.9F.B3.E4.B9.90.E6.B6.88.E6.81.AF">回复音乐消息</a>
 * @see com.foxinmy.weixin4j.msg.model.Music
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.msg.BaseMessage#toXml()
 */
public class MusicMessage extends BaseMessage {

	private static final long serialVersionUID = 4384403772658796395L;

	public MusicMessage(BaseMessage inMessage) {
		super(MessageType.music, inMessage);
	}

	@XStreamAlias("Music")
	private Music music;

	public void pushMusic(String mediaId) {
		this.music = new Music(mediaId);
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	@Override
	public String toXml() {
		XStream xstream = getXStream();
		xstream.aliasField("MediaId", Music.class, "musicUrl");
		xstream.aliasField("Title", Music.class, "title");
		xstream.aliasField("Description", Music.class, "desc");
		xstream.aliasField("HQMusicUrl", Music.class, "hqMusicUrl");
		xstream.aliasField("ThumbMediaId", Music.class, "thumbMediaId");
		return xstream.toXML(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[MusicMessage ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,music=").append(music.toString());
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
