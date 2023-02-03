// store.js
import Vuex from 'vuex'

export default new Vuex.Store({
  state: {
    isLogin: false,
    roadmapDetail : []
  },
  mutations: {
    setLoginStatus(state, status) {
      state.isLogin = status
    },
    setRoadmapDetailStatus(state,status){
      state.roadmapDetail = status
    }
  }
})
