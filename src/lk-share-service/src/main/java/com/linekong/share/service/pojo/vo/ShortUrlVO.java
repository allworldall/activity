package com.linekong.share.service.pojo.vo;

import com.linekong.share.service.utils.DataFormatUtils.StringUtils;
import com.linekong.share.service.utils.SysCodeConstant;
import sun.misc.BASE64Encoder;

/**
 * 本类作为用户获取短连接的响应对象
 */
public class ShortUrlVO {
    private int result;               //状态码
    private String shortUrl;          //客户端需要的短连接
    private String originalUrl;       //原始连接

    public ShortUrlVO() {
    }

    public ShortUrlVO(String originalUrl) {
        this.originalUrl = originalUrl;
        this.result = SysCodeConstant.SUCCESS;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        /*if(!StringUtils.isEmpty(shortUrl)) {
            this.shortUrl = new BASE64Encoder().encode(shortUrl.getBytes());
        }else {
            this.shortUrl = shortUrl;
        }*/
        this.shortUrl = shortUrl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("result=").append(result);
        sb.append(",originalUrl=").append(originalUrl);
        sb.append(",shortUrl=").append(shortUrl);
        return sb.toString();
    }

}
