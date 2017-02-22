package com.dsky.baas.configservice.logservice;

public interface IWarningReporterService {
	public void captureApiResultPacker(Object retVal);
	public boolean reportWarn(LogReport logReportData);
	public void reportWarnString(String msg,int code);
	public void reportWarnString(String msg);

}
