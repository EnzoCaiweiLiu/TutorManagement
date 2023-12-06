import ajax from './ajax'
export const LoginIn = (user_name, password) => ajax('/api/user/login', {user_name, password}, 'POST')
export const getAllUser= () => ajax('/api/user/allUser', {})
export const updateUser= (id) => ajax('/api/user/updateUser', {id});
