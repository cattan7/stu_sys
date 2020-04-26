//package com.stusys.cattan.teacher.component;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Component
//public class TokenFeignClientInterceptor implements RequestInterceptor {
//
//    /**
//     * token放在请求头.
//     *
//     * @param requestTemplate 请求参数
//     */
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        if (requestAttributes != null) {
//            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//            String token = request.getHeader(TokenContext.KEY_OAUTH2_TOKEN);
//            requestTemplate.header(TokenContext.KEY_OAUTH2_TOKEN,
//                    new String[] {token});
//        }
//    }
//}
