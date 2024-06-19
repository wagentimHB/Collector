package de.wagentim.collector.utils;

public interface ICommands
{
	public static final String CMD_GET_ENV_VARIABLE = "wmic ENVIRONMENT get Username, Name, VariableValue";
	public static final String POWERSHELL_SET_VARIABLE_SYSTEM = "[System.Environment]::SetEnvironmentVariable($key, $value, [System.EnvironmentVariableTarget]::Machine)";
	public static final String POWERSHELL_SET_VARIABLE_USER = "[Environment]::SetEnvironmentVariable($key, $value, [System.EnvironmentVariableTarget]::User)";
	public static final String POWERSHELL_GET_NET_ADAPTER = "Get-NetAdapter";
}
