weixin4j-server
===============

微信回调消息服务器
----------------
base on netty.

功能列表
-------
* `netty服务器`

* `消息分发`

* `消息拦截`(还需更多测试

如何使用
-------
###maven依赖(1.0.5,2015-08-13 released)

	<dependency>
	    <groupId>com.foxinmy</groupId>
	    <artifactId>weixin4j-server</artifactId>
	    <version>1.0.5</version>
	</dependency>
###编写服务启动类
明文模式并总是调试输出微信请求信息的服务启动类.

	public class MessageServerStartup{
		public static void main(String[] args) {
			new WeixinServerBootstrap("token").addHandler(
					DebugMessageHandler.global).startup();
		}
	}

密文模式的服务启动类

	public class MessageServerStartup{
		public static void main(String[] args) {
			new WeixinServerBootstrap("token","appid","aesKey").addHandler(
					DebugMessageHandler.global).startup();
		}
	}

只针对文本消息的服务启动类

	public class MessageServerStartup{
		public static void main(String[] args) {
			// 针对文本消息回复
			WeixinMessageHandler messageHandler = new MessageHandlerAdapter<TextMessage>() {
				@Override
				public WeixinResponse doHandle0(WeixinRequest request,
					TextMessage message) throws WeixinException {
						return new TextResponse("HelloWorld!");
				}
			};
			// 当消息类型为文本(text)时回复「HelloWorld」, 否则回复调试消息
			new WeixinServerBootstrap(token,appid, aesKey).addHandler(
					messageHandler, DebugMessageHandler.global).startup();
			}
		}
	}
使用拦截器处理消息请求的启动类

	public static void main(String[] args) {
		// 拦截所有请求
		WeixinMessageInterceptor interceptor = new MessageInterceptorAdapter() {
			@Override
			public boolean preHandle(ChannelHandlerContext context,
					WeixinRequest request, WeixinResponse response, Object message,
					WeixinMessageHandler handler) throws WeixinException {
				context.write(new TextResponse("所有消息被拦截了！"));
				return false;
			}
			@Override
			public void postHandle(ChannelHandlerContext context,
					WeixinRequest request, WeixinResponse response, WeixinResponse response,
					Object message, WeixinMessageHandler handler)
					throws WeixinException {
				System.err.println("preHandle返回为true,执行handler后");
			}
			@Override
			public void afterCompletion(ChannelHandlerContext context,
					WeixinRequest request, WeixinResponse response, Object message,
					WeixinMessageHandler handler, WeixinException exception)
					throws WeixinException {
				System.err.println("请求处理完毕");
			}
		};
		new WeixinServerBootstrap("token").addInterceptor(interceptor)
				.addHandler(BlankMessageHandler.global).startup();
	}

更多内容将会写在wiki里

assembly打包(辅助)
-----------------
> [assembly](http://maven.apache.org/plugins/maven-assembly-plugin/assembly.html)是maven的一个打包插件,它可以构建一个包含脚本、配置文件以及所有运行时所依赖的元素的完整发布包.

1.复制[assembly.xml](./src/main/assembly.xml)和[startup.sh](./src/main/startup.sh)到自己工程的src/main目录下.

2.在项目pom.xml中的/bulid/plugins节点新增如下配置

	<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-assembly-plugin</artifactId>
		<version>2.5.1</version>
		<configuration>
			<descriptors>
				<descriptor>src/main/assembly.xml</descriptor>
			</descriptors>
			<finalName>weixin4j-server</finalName>
		</configuration>
		<executions>
			<execution>
				<id>make-assembly</id>
				<phase>package</phase>
				<goals>
					<goal>single</goal>
				</goals>
			</execution>
		</executions>
	</plugin>
`descriptor`表示[assembly](./src/main/assembly.xml)文件的位置.

`finalName`表示打包(zip)后的文件名,需配合[startup.sh](./src/main/startup.sh)中`APP_HOME`的值使用.

3.[startup.sh](./src/main/startup.sh)中`JAVA_HOME`为java运行环境(jre|jdk)的安装根目录,如果与脚本中的值不一致,可使用软连接命令:`ln -s /usr/local/java 实际的目录`.

4.[startup.sh](./src/main/startup.sh)中`APP_HOME`为服务的启动目录,相当于运行服务时的classpath目录.

5.修改[startup.sh](./src/main/startup.sh)中`APP_MAINCLASS`为上述编写的带main方法的服务启动类的全限定名.

6.执行`mvn package`命令后在target目录下得到一个zip的压缩包,在7或者8中选择一种方式启动服务.

7.[上传zip包到服务器],解压包到启动目录(`APP_HOME`)的`上一级目录`后运行startup.sh脚本.
    
    sh startup.sh start
    
8.ant远程部署

 > 复制[`deploy.xml`](./deploy.xml)到自己工程的根目录下.
 
 > Ant远程部署[`deploy.xml`](./deploy.xml)的正确执行需要[`jsch`](http://www.jcraft.com/jsch/)包的支持,下载jar包将其引入执行Ant命令时的`classpath`中.
 
 > 正确填写`zip.name`、`host`、`pwd`、`main.dir`、`sub.dir`五个属性值.
 
 > 右键 Run as -> Ant Build
 
相关参考
-------

![消息服务时序图](http://7mj4zs.com1.z0.glb.clouddn.com/weixin4j.png)

[spring-webmvc:DispatcherServlet](https://github.com/spring-projects/spring-framework/blob/master/spring-webmvc/src/main/java/org/springframework/web/servlet/DispatcherServlet.java)

[spring-webmvc:HandlerAdapter](https://github.com/spring-projects/spring-framework/blob/master/spring-webmvc/src/main/java/org/springframework/web/servlet/HandlerAdapter.java)

[spring-webmvc:HandlerInterceptor](https://github.com/spring-projects/spring-framework/blob/master/spring-webmvc/src/main/java/org/springframework/web/servlet/HandlerInterceptor.java)

[更新LOG](./CHANGE.md)
----------------------