import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

export default new Vuex.Store({
  state: {
    isLogin: false,
    jwt: "",
    curSubjectId: "",
    isAdmin: false,
    accountId: "",
    isRoadmap: false,
    passingScore: 80,
    username: "",
    curSubExamId: "",
  },
  mutations: {
    clearState(state){
      state.clearState();
    },
    setIsRoadmap(state, status) {
      state.isRoadmap = status;
    },
    setCurSubjectExamId(state, status) {
      state.curSubExamId = status;
    },
    setAccountId(state, status) {
      state.accountId = status;
    },
    setIsAdmin(state, status) {
      state.isAdmin = status;
    },
    setCurSubjectId(state, status) {
      state.curSubjectId = status;
    },
    setLoginStatus(state, status) {
      state.isLogin = status;
    },
    setJwtToken(state, token) {
      state.jwt = token;
    },
    setUsername(state, username) {
      state.username = username;
    },
    ifTokenVal(state) {
      if (state.jwt === "") {
        state.isLogin = false;
        return false;
      }
      state.isLogin = true;
      return true;
    },
    logout(state) {
      state.jwt = "";
      state.isLogin = false;
      state.curSubjectId = "";
      state.isAdmin = false;
      state.accountId = "";
      state.isRoadmap = false;
      state.passingScore = 80;
      state.username = "";
      state.curSubExamId = "";
    },
  },
  actions: {
    ifTokenVal: ({ commit }) => {
      commit("ifTokenVal");
    },
    adminLogout: ({ commit }) => {
      commit("adminLogout");
    },
  },
  plugins: [createPersistedState()],
});
