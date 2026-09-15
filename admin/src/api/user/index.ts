import { Alova } from '@/utils/http/alova/index';

/**
 * 管理员登录
 * 后端 /login/doLogin 入参: { username, password }
 */
export function login(params: {
  username: string;
  password: string;
}) {
  return Alova.Post<any>('/login/doLogin', params);
}

/**
 * 修改当前登录人密码。
 * 前端表单字段 oldPassword/newPassword，转换为后端 oldPwd/newPwd。
 */
export function changePassword(params: {
  oldPassword: string;
  newPassword: string;
  confirmPassword?: string;
}) {
  return Alova.Post<any>('/admin/changePassword', {
    oldPwd: params.oldPassword,
    newPwd: params.newPassword,
  });
}
