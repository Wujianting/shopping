//给action注册处理函数
export function setUserIn({commit},user){
  return  commit('setUserinfo',user)
}
export function isShow({commit},isShow){
  return  commit('isShows',isShow)
}
