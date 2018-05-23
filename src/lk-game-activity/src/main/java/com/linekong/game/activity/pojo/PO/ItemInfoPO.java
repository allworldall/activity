package com.linekong.game.activity.pojo.PO;

public class ItemInfoPO {
    private String activityId;
    private String itemCode;
    private int itemNum;

    public ItemInfoPO() {
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("activityId=").append(activityId);
        sb.append(",itemCode=").append(itemCode);
        sb.append(",itemNum=").append(itemNum);
        return sb.toString();
    }
}
