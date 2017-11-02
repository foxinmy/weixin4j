package com.zone.weixin4j.qy.event;

import com.zone.weixin4j.message.event.EventMessage;
import com.zone.weixin4j.type.EventType;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * 异步任务事件完成通知
 * 
 * @className BatchjobresultMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月31日
 * @since JDK 1.6
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6#.E5.BC.82.E6.AD.A5.E4.BB.BB.E5.8A.A1.E5.AE.8C.E6.88.90.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">异步任务事件完成通知</a>
 */
public class BatchjobresultMessage extends EventMessage {

	private static final long serialVersionUID = 8014540441322209657L;

	public BatchjobresultMessage() {
		super(EventType.batch_job_result.name());
	}

	/**
	 * 任务信息
	 */
	@XmlElement(name = "BatchJob")
	private BatchJob batchJob;

	public BatchJob getBatchJob() {
		return batchJob;
	}

	/**
	 * 任务信息
	 * 
	 * @className BatchJob
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2015年4月1日
	 * @since JDK 1.6
	 * @see
	 */
	public static class BatchJob implements Serializable {
		private static final long serialVersionUID = -7520032656787156391L;
		/**
		 * 异步任务id，最大长度为64字符
		 */
		@XmlElement(name = "JobId")
		private String jobId;
		/**
		 * 操作类型，字符串，目前分别有： 1. sync_user(增量更新成员) 2. replace_user(全量覆盖成员) 3.
		 * invite_user(邀请成员关注) 4. replace_party(全量覆盖部门)
		 * 
		 * @see com.foxinmy.weixin4j.qy.type.BatchType
		 */
		@XmlElement(name = "JobType")
		private String jobType;
		/**
		 * 返回码
		 */
		@XmlElement(name = "ErrCode")
		private String ErrCode;
		/**
		 * 对返回码的文本描述内容
		 */
		@XmlElement(name = "ErrMsg")
		private String errMsg;

		public String getJobId() {
			return jobId;
		}

		public String getJobType() {
			return jobType;
		}

		public String getErrCode() {
			return ErrCode;
		}

		public String getErrMsg() {
			return errMsg;
		}

		@Override
		public String toString() {
			return "[jobId=" + jobId + ", jobType=" + jobType + ", ErrCode="
					+ ErrCode + ", errMsg=" + errMsg + "]";
		}
	}

	@Override
	public String toString() {
		return "BatchjobresultMessage [batchJob=" + batchJob + ", "
				+ super.toString() + "]";
	}
}
