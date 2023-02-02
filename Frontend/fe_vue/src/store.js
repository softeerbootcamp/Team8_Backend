// store.js
import Vuex from 'vuex'

export default new Vuex.Store({
  state: {
    isLogin: false
  },
  mutations: {
    setLoginStatus(state, status) {
      state.isLogin = status
    }
  }
})
