// store.js
import Vuex from "vuex";

export default new Vuex.Store({
  state: {
    isLogin: false,
    subjects: [],
    jwtToken: null,
  },
  mutations: {
    setLoginStatus(state, status) {
      state.isLogin = status;
    },
    setSubjectsStatus(state, subjects) {
      state.subjects = subjects;
    },
    setJwtToken(state, token) {
      state.jwtToken = token;
    },
    ifTokenVal(state) {
      if (state.jwtToken == null) {
        state.isLogin = false;
        return false;
      }
      state.isLogin = true;
      return true;
    },
    logout(state, status) {
      state.jwtToken = null;
      state.isLogin = status;
    },
  },
  actions: {
    ifTokenVal: ({ commit }) => {
      commit("ifTokenVal");
    },
    logout: ({ commit }) => {
      commit("logout");
    },
  },
});
