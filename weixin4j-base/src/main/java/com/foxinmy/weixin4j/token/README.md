包含token的实现

FileTokenHolder.java 基于文件保存的token实现

RedisTokenHolder.java 基于redis保存的token实现(推荐)

当然自己也可实现token的存取,继承`AbstractTokenHolder`实现`getToken`方法