import axios from 'axios';

const axiosService = axios.create({
 baseURL: 'https://backend.devroad.site/',
 headers: 'Content-Type: application/json'
});

function signinUser(userData) {
  return axiosService.post('api/user/signin', userData);
}

export { signinUser };