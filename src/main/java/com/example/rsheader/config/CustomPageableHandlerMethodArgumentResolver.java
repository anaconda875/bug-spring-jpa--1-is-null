package com.example.rsheader.config;

import com.example.rsheader.RsHeaderApplication;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CustomPageableHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return RsHeaderApplication.CustomPageable.class.equals(parameter.getParameterType());
    }

    @Override
    public RsHeaderApplication.CustomPageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        String keyword = webRequest.getParameter(getParameterNameToUse("keyword", methodParameter));

        return new RsHeaderApplication.CustomPageable(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort(), keyword);
    }
}
