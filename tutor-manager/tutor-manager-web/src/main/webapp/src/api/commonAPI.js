import request from '@/api/request'

export default function commonAPI(api, postData = {}, method = 'get', config = {}) {
  if (method === 'get') {
    return request({
      url: api,
      params: postData,
      method: "get",
      ...config
    })
  } else {
    return request({
      url: api,
      data: postData,
      method: method,
      ...config
    })
  }
}