package sparkx.sparkshop.system.service;

import sparkx.sparkshop.system.validate.LoginValidate;
import sparkx.sparkshop.system.vo.LoginReturnVo;

public interface ILoginService {

    /**
     * 执行登录
     */
    LoginReturnVo doLogin(LoginValidate validate);
}
