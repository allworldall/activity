package com.linekong.share.service.utils.exception;

public class RedisException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RedisException(){
        super();
    }
    public RedisException(String msg){
        super(msg);
    }

}
