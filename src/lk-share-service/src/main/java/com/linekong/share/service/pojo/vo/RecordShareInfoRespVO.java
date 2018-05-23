package com.linekong.share.service.pojo.vo;

public class RecordShareInfoRespVO {
    private int result;             //状态码
    private String uniqulyId;       //唯一标识

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getUniqulyId() {
        return uniqulyId;
    }

    public void setUniqulyId(String uniqulyId) {
        this.uniqulyId = uniqulyId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("result=").append(result);
        sb.append(",uniqulyId=").append(uniqulyId);
        return sb.toString();
    }

    /**
     * 封装数据到返回对象中
     * @param success
     * @param uniqulyId
     */
    public void createResp(int result, String uniqulyId) {
        this.result = result;
        this.uniqulyId = uniqulyId;
    }
}
