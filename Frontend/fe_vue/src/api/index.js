import axios from 'axios';

const axiosService = axios.create({
 baseURL: 'https://backend.devroad.site/',
 headers: 'Content-Type: application/json'
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
function getCourceData(param,config){
  return axiosService.get('api/course'+"/"+param,config);
}
function getAllSubjectData(){
  return axiosService.get('api/subject');
}

export { signinUser,signupUser,userData,getRoadmap,getSubjectDetail,getCourceData
,getAllSubjectData };