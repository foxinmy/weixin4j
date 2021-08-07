## 欢迎来到 weixin4j-wxa
微信小程序 API 支持

### 如何使用

```java
var appId = "YOUR APP ID";
var appSecret = "YOUR APP SECRET";
var wxAccount = new WeixinAccount(appId, appSecret);
var wxa = new WeixinAppFacade(wxAccount);
var jsCode = request.getParameter("jsCode");
var session = wxa.getLoginApi().jscode2session(jsCode);
```

*更多 API 见 [WeixinAppFacade JavaDoc](apidocs/index.html?com/foxinmy/weixin4j/wxa/WeixinAppFacade.html)。*

### 解密数据
客户端 API
[`wx.getUserInfo(Object object)`](https://developers.weixin.qq.com/miniprogram/dev/api/open-api/user-info/wx.getUserInfo.html)
[`wx.getUserProfile(Object object)`](https://developers.weixin.qq.com/miniprogram/dev/api/open-api/user-info/wx.getUserProfile.html)
等请求后得到的加密数据，可以通过下面的方法来解密。

客户端将获得的加密数据发往业务服务器，业务服务器收到加密数据后，使用登录 API 获得的 `sessionKey` 解密并进行后续操作：

```java
var encryptedData = request.getParameter("encryptedData");
var iv = request.getParameter("iv");
var sessionKey = session.getSessionKey();
var wxBizDataCrypt = new WXBizDataCrypt(appId, sessionKey);
var decryptedData = wxBizDataCrypt.decryptData(encryptedData, iv);
```
