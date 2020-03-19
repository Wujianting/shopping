package com.neuedu.common;

public enum PaymentEnum {
            PAYMENT_ONLINE(1,"在线支付"),
            PAYMENT_OFFLINE(2,"货到付款"),

    ;
    private int status;
    private String desc;
    PaymentEnum(int status, String desc){
        this.status=status;
        this.desc=desc;
    }

    //枚举的遍历
   public static PaymentEnum codeOf(Integer status){
        for(PaymentEnum orderStatusEnum:values()){
            if(orderStatusEnum.getStatus()==status){
                return orderStatusEnum;
            }
        }
        return null;
   }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
