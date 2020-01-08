package com.example.qiniuyun.util;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 吴成卓
 * @version V1.0
 * @Project: qiniuyun
 * @Package com.example.qiniuyun.util
 * @Description:
 * @date 2020/1/8 星期三 10:35
 */
@Configuration
@ConfigurationProperties(prefix = "qiniuyun")
@Data
public class QNYConfig {
    //密钥
    private String accessKey;
    private String secretKey;
    //前缀
    private String path;
    //空间名字
    private String bucket;


    @Bean
    public Auth createAuth(){
        return Auth.create(accessKey,secretKey);
    }

    @Bean
    public com.qiniu.storage.Configuration configuration(){
        return new com.qiniu.storage.Configuration(Region.huanan());
    }

    @Bean
    public UploadManager uploadManager(){
        return  new UploadManager(configuration());
    }

    @Bean
    public BucketManager bucketManager(){
        return new BucketManager(createAuth(),configuration());
    }
}
