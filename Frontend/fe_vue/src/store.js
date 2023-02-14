import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

export default new Vuex.Store({
  state: {
    isLogin: false,
    subjects: [],
    jwt: "",
    curSubjectId:"",
    isAdmin: false,
    accountId:"",
    isRoadmap:"-1",
    passingScore : 80,
    username :"",
  },
  mutations: {


    setAccountId(state,status){
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
    setSubjectsStatus(state, subjects) {
      state.subjects = subjects;
    },
    setJwtToken(state, token) {
      state.jwt = token;
      
    },
    setUsername(state,username){
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
    },
    adminLogout(state) {
      state.jwt = "";
      state.isLogin = false;
      state.isAdmin = false;
    },
  },
  actions: {
    ifTokenVal: ({ commit }) => {
      commit("ifTokenVal");
    },
    logout: ({ commit }) => {
      commit("logout");
    },
    adminLogout: ({ commit }) => {
      commit("adminLogout");
    },
  },
  plugins: [createPersistedState()],
});
