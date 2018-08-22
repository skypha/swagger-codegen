package io.swagger.codegen.languages;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import io.swagger.codegen.CliOption;
import io.swagger.codegen.CodegenConfig;
import io.swagger.codegen.CodegenConstants;
import io.swagger.codegen.CodegenOperation;
import io.swagger.codegen.CodegenModel;
import io.swagger.codegen.CodegenProperty;
import io.swagger.codegen.CodegenType;
import io.swagger.codegen.DefaultCodegen;
import io.swagger.codegen.SupportingFile;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.Property;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class Swift4MoyaCodegen extends Swift4Codegen {
    
    protected String[] responseAs = new String[0];
    
    @Override
    public String getName() {
        return "swift4-moya";
    }
    
    public Swift4MoyaCodegen() {
        super();
        embeddedTemplateDir = templateDir = "swift4/MoyaSimple";
        typeMapping.put("file", "Data");
        
    }
    
    @Override
    public void processOpts() {
        super.processOpts();
        
        supportingFiles.clear();
        //supportingFiles.add(new SupportingFile("model.mustache", sourceFolder, "Models.swift"));
        //supportingFiles.add(new SupportingFile("api.mustache", sourceFolder));
    }
    
    @Override
    public String toApiName(String name) {
        if (name.length() == 0)
            return "APITargets";
        return initialCaps(name) + "Targets";
    }
    
    @Override
    public CodegenOperation fromOperation(String path, String httpMethod, Operation operation, Map<String, Model> definitions) {
        CodegenOperation op = super.fromOperation(path, httpMethod, operation, definitions, null);
        op.httpMethod = toMoyaTarget(op.httpMethod);
        
        String tmpPath = op.path;
        tmpPath = tmpPath.replaceAll("\\{", "\\(");
        tmpPath = tmpPath.replaceAll("\\}", ")");
        op.path = tmpPath;
        
        return op;
    }
    
    @Override
    public CodegenOperation fromOperation(String path,
                                          String httpMethod,
                                          Operation operation,
                                          Map<String, Model> definitions,
                                          Swagger swagger) {
        CodegenOperation op = super.fromOperation(path, httpMethod, operation, definitions, swagger);
        op.httpMethod = toMoyaTarget(op.httpMethod);
        
        String tmpPath = op.path;
        tmpPath = tmpPath.replaceAll("\\{", "\\\\(");
        tmpPath = tmpPath.replaceAll("}", ")");
        op.path = tmpPath;
        
        return op;
    }
    
    public String toMoyaTarget(String name) {
        String target = "";
        switch (name) {
            case "GET":
                target = ".get";
                break;
            case "POST":
                target = ".post";
                break;
            case "PUT":
                target = ".put";
                break;
            case "DELETE":
                target = ".delete";
                break;
            default:
                target = "." + name.toLowerCase();
        }
        return target;
    }
    
    
}
