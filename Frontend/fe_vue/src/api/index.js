import axios from 'axios';
import store from '@/store'

const axiosService = axios.create({
 baseURL: 'https://backend.devroad.site/',
 headers: {
  "Content-Type": 'application/json',
  "jwt" : store.state.jwt
}
});

function signinUser(userData) {
  return axiosService.post('api/user/signin', userData);
}
function signupUser(userData){
  return axiosService.post('api/user/signup', userData);
}
function userData(config){
  return axiosService.get('api/user',config);
}
function getRoadmap(config){
  return axiosService.get('api/roadmap',config);
}
function getSubjectDetail(param,config){
  return axiosService.get('api/subject'+"/"+param,config);
}
function getCourseData(param,config){
  return axiosService.get('api/course'+"/"+param,config);
}
function getAllSubjectData(){
  return axiosService.get('api/subject');
}
function postRoadmapToUserByEmail(requestBody){
  return axiosService.post('api/roadmap',requestBody);
}
function getNoRoadmapUserData(config){
    return axiosService.get('api/user/noRoadmap',config);
}
function getExamDetailData(config,param){
  return axiosService.get('api/exam/'+param,config);
}
function postAssignMentData(config, param){
  return axiosService.post('api/exam/assignment/',param,config);

}
export { signinUser,signupUser,userData,getRoadmap,getSubjectDetail,getCourseData
,getAllSubjectData,postRoadmapToUserByEmail,getNoRoadmapUserData,getExamDetailData,
postAssignMentData
 };