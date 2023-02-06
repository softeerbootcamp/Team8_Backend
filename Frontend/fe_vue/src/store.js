// store.js
import Vuex from "vuex";

export default new Vuex.Store({
  state: {
    isLogin: false,
    roadmapDetail: [],
    jwtToken: null,
  },
  mutations: {
    setLoginStatus(state, status) {
      state.isLogin = status;
    },
    setRoadmapDetailStatus(state, roadmapDetail) {
      state.roadmapDetail = roadmapDetail;
    },
    setJwtToken(state, token) {
      
      state.jwtToken = token;
    },
    ifTokenVal(state) {
      if (state.jwtToken == null) {
        return false;
      }
      return true;
    },
  },
  actions:{
    ifTokenVal:({commit}) => {
      commit('ifTokenVal');
  }
    
  }
});
