package com.isyscore.robot.integration;

import com.isyscore.os.dev.api.http.DefaultIsyscoreClient;
import com.isyscore.os.dev.api.http.IsyscoreClient;
import com.isyscore.os.dev.api.permission.model.builder.QueryUserAclRequestBuilder;
import com.isyscore.os.dev.api.permission.model.result.QueryUserAclResult;
import com.isyscore.os.dev.api.permission.service.PermissionService;
import com.isyscore.os.dev.api.permission.service.impl.PermissionServiceImpl;
import com.isyscore.os.dev.config.IsyscoreConstants;
import com.isyscore.os.dev.util.IsyscoreHashMap;
import com.isyscore.os.sso.session.RequestUserHolder;
import com.isyscore.os.sso.session.UserForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @author shizi
 * @since 2020/4/10 11:04 AM
 */
@SpringBootConfiguration
public class AuthTest  {

    private static final String SID_STR = "X-Isyscore-Permission-Sid";

    @Test
    public void test1() {

        String serverHost = "http://10.30.30.25:9010/isc-api-gateway";
        //        String serverHost = "http://10.20.5.3:9010/isc-api-gateway";
        //        String serverHost = "http://10.30.30.34:9010/isc-api-gateway";
        //        String serverHost = "http://192.168.10.82:9010/isc-api-gateway";
        String appId = "e56b59fc-6a4d-11ea-ae33-0221860e9b7e";
        String format = "JSON";
        String charset = "UTF-8";
        String signType = "RSA";
        String encryptType = "RSA";

        IsyscoreConstants.APP_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhsJI9m/0SisLM+AIVQH4QqOrbpYakpZq\n" + "xjpM8H1hRkiVDFh7ddaQA5NlM0pzQcmt/+Ty1W/hd6wIQ5BMKDSHRU7Q3eD8xT/UZRD0Ltigi2PV\n" + "/MkmdacbT9uvSCULuTJoDlhu4FhX8Sm7XAY2UndzWrDVSx0ffCkWggy/ZcBegvXwv0vfOgt486zk\n" + "hSXy4VePQ3BmR/HLIpZsLItsfIqR33TnsCQnEbi5GLZvy9Hm15e8/T05BTRwI2fmnGBbylq5YVRx\n" + "0uVqzZVnDFEfZPNzKEpxKg/QfjiJPwTMM7HMLjyavAlY9AwaBc3GbWzNHR1zYZk622AVIeZ3gjW1\n" + "JIV5xwIDAQAB";
        IsyscoreConstants.APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCGwkj2b/RKKwsz4AhVAfhCo6tu\n" +
            "lhqSlmrGOkzwfWFGSJUMWHt11pADk2UzSnNBya3/5PLVb+F3rAhDkEwoNIdFTtDd4PzFP9RlEPQu\n" +
            "2KCLY9X8ySZ1pxtP269IJQu5MmgOWG7gWFfxKbtcBjZSd3NasNVLHR98KRaCDL9lwF6C9fC/S986\n" +
            "C3jzrOSFJfLhV49DcGZH8csilmwsi2x8ipHfdOewJCcRuLkYtm/L0ebXl7z9PTkFNHAjZ+acYFvK\n" +
            "WrlhVHHS5WrNlWcMUR9k83MoSnEqD9B+OIk/BMwzscwuPJq8CVj0DBoFzcZtbM0dHXNhmTrbYBUh\n" +
            "5neCNbUkhXnHAgMBAAECggEAP58oTVjrz7xkrJgftuqYJ/YXsL0jTIPBY1cRDhVTwJfx7oMzvp2P\n" +
            "HnEPPVjv82ZhRMf1sbkBjLtmp2dP3Ud3ecxNJgPbstCA2TpY3mkd2tFGrPLWuoMrjNdd+MSstUHM\n" +
            "CMHNjxOGaXBXaYnxSZvllTKZcL4OKFNJshqMnmBk/pSmRbPUQ2j7Rrm7Bdvys9ftb5pudQ3tphw5\n" +
            "LXJ0T+i461mioYg953XYMvlxkcS/xfTizvkU/D4SVdi8ZrCZF0UQh6DHyOdT2vTxcfQD4muvdTEa\n" +
            "BGpXWhLrHKs0OwNzHpt+/HukqVWtkcuGJhGCA7eQ3rxH3Nnee7bW7ccPmzOZIQKBgQDe8hYXEawM\n" +
            "ykyPNQ8AsGJxRNpZnMJJc3xrU+OGvLhaSxW/VrAeK9VgmeXRSZOeSoc8UI/LNhYSExWly16ImthQ\n" +
            "8mbi8ClQjM+O10Hoqs3FGhhaFJv2P6XesdV+tgHaJ4/1rqbt2GuieQ2z1QATdNk+z02j/gTo1bZS\n" +
            "kC//qf1jtwKBgQCavREpidpOyKOLIyvdqmdPXCOjg0cdPVZ1KYsuWVdpDFAERjN+07zGp4ZoKAXk\n" +
            "lLyy+v+OHZhihAerxtjOIaIIOD5oCvHPUrIRG2xg1DaXs1xzSojLo0EzYyBaTigprN2Hn1zEJIB2\n" +
            "9V0Tsv6odETcxosLZFZa25hp8aIuF5M6cQKBgD7FQlr0atCfFLctZS/4eX8St7eLX1h2340IbYM+\n" +
            "F1m00kaxDZ0xEj7EleQ7JwjmSvU8aX/5lnU4Ulv/ynoDvuvrk/RJUhiPzNCW4sOzc4QPONQEAXVv\n" +
            "ri/pOgcXD6ZwhSPTLMTCrmtsUeNUEVVpDNGWZHHLz24O3eqZFiqRnfo5AoGAMznkNJwuaWaCvc0m\n" +
            "+HsxOFSId9k98bAcryzRpsZs6Znp44BaC+KM3DcZh+G4EbQrxBTUBXhGulZ7M6Lgo1z/BzmXb1ow\n" +
            "MzJtQLyTZHrcl9wI/yHtp3ykWR631y2InkNcE0nurABphvgzmBHpvFrBcVVJMrxE2k9YZbpYtmmX\n" +
            "lAECgYAcqx5hrnL/1KgXZsExoQg/ajpf2tG7VORn6maq044Rlsqykc0NQmw7nnxJe2wIRvfbNXHK\n" +
            "3TqZ+bxPnKXlV3q2nlxJwXNG6mEn3xMfXej6l+41mFWt1/8quYw1gkd7ypNZ6A4LK+odHGsE0pXv\n" +
            "F4TuaSj+gO+8n/s11lW6awcYSw==";

        IsyscoreClient client = new DefaultIsyscoreClient(serverHost, appId, format, charset, signType, encryptType);
        PermissionService permissionService = new PermissionServiceImpl(client);


        UserForm currentUser = RequestUserHolder.getCurrentUser();
        IsyscoreHashMap coreMap = new IsyscoreHashMap();
        coreMap.put(SID_STR, "95cd1e2f-702f-437b-ad1a-a10b6c70caee");

        QueryUserAclRequestBuilder builder = new QueryUserAclRequestBuilder()
            //设置应用 code: 指查询指定应用下的用户权限
            .setAppCode("appCode")
            //设置请求头
            .setHeaders(coreMap);

        QueryUserAclResult result = permissionService.queryUserAcl(builder);
        // 判断结果是否成功
        System.out.println("success: " + result.isSuccess());
        // 获取 httpCode:http 请求结果的 code 码
        System.out.println("httpCode: " + result.getResponse().getHttpCode());
        // 获取 httpMessage:http 请求结果的消息
        System.out.println("httpMessage: " + result.getResponse().getHttpMessage());
        // 获取 code:接口返回的错误码
        System.out.println("code: " + result.getResponse().getCode());
        // 获取 message:接口返回的错误消息
        System.out.println("message: " + result.getResponse().getMessage());
        // 获取 detail:接口返回的错误附带详情
        System.out.println("detail: " + result.getResponse().getDetail());
        // 获取结果数据
        System.out.println("data: " + result.getResponse().getBody());
        // 解析结果数据(Json 转 Object)
        System.out.println("parseData: " + result.parseData());
    }
}
