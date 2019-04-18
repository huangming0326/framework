package com.newegg.framework.common.data.access.configuration;

public enum  ParameterDirection {
    // Summary:
    //     The parameter is an input parameter.
    Input,
    //
    // Summary:
    //     The parameter is an output parameter.
    Output,
    //
    // Summary:
    //     The parameter is capable of both input and output.
    InputOutput,
    //
    // Summary:
    //     The parameter represents a return value from an operation such as a stored
    //     procedure, built-in function, or user-defined function.
    ReturnValue,
}
