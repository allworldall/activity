package com.linekong.share.service.utils.xmlrpc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import com.linekong.share.service.utils.SysCodeConstant;
import com.linekong.share.service.utils.cache.CommonCache;
import com.linekong.share.service.utils.log.LoggerUtil;

public class InvokeXmlRpcUtil {
	//调用ePassportSDKMid初始化对象
	private static XmlRpcClient xmlRpcClient = null;


	private static class InvokeXmlRpcUtilHolder{
		private static final InvokeXmlRpcUtil instance = new InvokeXmlRpcUtil();
	}

	private InvokeXmlRpcUtil(){

	}
	public static final InvokeXmlRpcUtil getInstance(int gameId){
		if(xmlRpcClient == null)
			try {
			   xmlRpcClient = new XmlRpcClient((String) CommonCache.getSendItemAddress(SysCodeConstant.INVOKE_XML_RPC_ADDRESS + "_" + gameId));
		    } catch (MalformedURLException e) {
			   LoggerUtil.error(InvokeXmlRpcUtil.class, e.getMessage() + "INIT XMLRPC CONNECTION ERROR:");
			}
		return InvokeXmlRpcUtilHolder.instance;
	}
	/**
	 * 调用远程XMLRPC方法返回int值
	 * @param String methodName  调用方法名称
	 * @param Vector param		   传递参数值
	 * @return Integer
	 */
	public int invokeInt(String methodName,Vector<?> param){
		int result = SysCodeConstant.INVOKE_XML_RPC_ERROR;
		try {
			Object obj = xmlRpcClient.execute(methodName, param);
			if (obj instanceof Exception) {
				LoggerUtil.error(InvokeXmlRpcUtil.class,((Exception) obj).getMessage());
			}else{
				result = (Integer) obj;
			}
		} catch (XmlRpcException e) {
			LoggerUtil.error(InvokeXmlRpcUtil.class,e.getMessage());
		} catch (IOException e) {
			LoggerUtil.error(InvokeXmlRpcUtil.class,e.getMessage());
		}
		return result;
	}
	/**
	 * 调用远程XMLRPC方法返回String值
	 * @param String methodName   调用方法名称
	 * @param Vector param		     传递参数值
	 * @return String
	 */
	public String invokeString(String methodName,Vector<?> param){
		String result = SysCodeConstant.INVOKE_XML_RPC_ERROR+"";
		try {
			Object obj = xmlRpcClient.execute(methodName, param);
			if(obj instanceof Exception){
				LoggerUtil.error(InvokeXmlRpcUtil.class,((Exception) obj).getMessage());
			}else{
				result = (String)obj;
			}
		} catch (XmlRpcException e) {
			LoggerUtil.error(InvokeXmlRpcUtil.class,e.getMessage());
		} catch (IOException e) {
			LoggerUtil.error(InvokeXmlRpcUtil.class,e.getMessage());
		}
		return result;
		
	} 
	/**
	 * 调用远程XMLRPC方法返回Hashtable值
	 * @param String methodName  调用方法名称
	 * @param Vector param		  传递参数值
	 * @return Hashtable<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public Hashtable<String, Object> invokeHashTable(String methodName,Vector<?> param){
		Hashtable<String, Object> hashTable = new Hashtable<String,Object>();
		String result = SysCodeConstant.INVOKE_XML_RPC_ERROR+"";
		hashTable.put("result_code", result);
		try {
			Object obj = xmlRpcClient.execute(methodName, param);
			if(obj instanceof Exception){
				LoggerUtil.error(InvokeXmlRpcUtil.class,((Exception) obj).getMessage());
			}else{
				hashTable = (Hashtable<String,Object>)obj;
			}
		} catch (XmlRpcException e) {
			LoggerUtil.error(InvokeXmlRpcUtil.class,e.getMessage());
		} catch (IOException e) {
			LoggerUtil.error(InvokeXmlRpcUtil.class,e.getMessage());
		}
		return hashTable;
	}
}
