package com.department.departmentservice.exception;

public class DepartmentNotFoundException extends RuntimeException{
    private String ResourceName;
    private String fieldName;
    private String fieldValue;

    public DepartmentNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
        ResourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
